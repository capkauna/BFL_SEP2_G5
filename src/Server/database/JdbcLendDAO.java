package Server.database;

import Server.model.Lend;
import Server.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcLendDAO implements LendDAO {
  private final Connection c;

  public JdbcLendDAO(Connection connection) {
    this.c = connection;
  }

  public static LendDAO getInstance() {
    try {
      return new JdbcLendDAO(DBConnection.getConnection());
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }


  @Override
  public Lend create(Lend lend) throws SQLException {
    String sql =
        "INSERT INTO lends (user_id, book_id, borrower_id, start_date) " +
            "VALUES (?, ?, ?, CURRENT_DATE) RETURNING lend_id";
    try (PreparedStatement stmt = c.prepareStatement(sql)) {
      stmt.setInt(1, lend.getOwnerId());
      stmt.setInt(2, lend.getBookId());
      stmt.setInt(3, lend.getBorrowerId());

      ResultSet rs = stmt.executeQuery();

    ResultSet keys = stmt.executeQuery();
      if (keys.next()) {
        lend.setLendId(keys.getInt(1));
      }
      return lend;
    }
  }
  //this method creates a lend and updates the book's status in a single transaction
  @Override
  public Lend createFull(Lend lend) throws SQLException {
    // turn off auto‐commit so we can bundle both statements
    boolean oldAutoCommit = c.getAutoCommit();
    c.setAutoCommit(false);

    try {
      // 1) insert into lends
      String lendSql =
          "INSERT INTO lends (user_id, book_id, borrower_id, start_date) " +
              "VALUES (?, ?, ?, CURRENT_DATE) RETURNING lend_id";
      try (PreparedStatement lendStmt = c.prepareStatement(lendSql)) {
        lendStmt.setInt(1, lend.getOwnerId());
        lendStmt.setInt(2, lend.getBookId());
        lendStmt.setInt(3, lend.getBorrowerId());
        ResultSet rs = lendStmt.executeQuery();
        if (rs.next()) {
          lend.setLendId(rs.getInt("lend_id"));
        }
      }

      // 2) update the book’s status in the same transaction
      //    assume you store the raw status string "Borrowed by <username>"
      String newStatus = "Borrowed by " + lend.getBorrowerId();
      String bookSql =
          "UPDATE books SET status = ? WHERE book_id = ?";
      try (PreparedStatement bookStmt = c.prepareStatement(bookSql)) {
        bookStmt.setString(1, newStatus);
        bookStmt.setInt(2, lend.getBookId());
        bookStmt.executeUpdate();
      }

      c.commit();
      return lend;
    }
    catch (SQLException ex) {
      c.rollback();              // undo both if something went wrong
      throw ex;
    }
    finally {
      c.setAutoCommit(oldAutoCommit);
    }
  }



  @Override
  public Lend findById(int id) throws SQLException {
    String sql = "SELECT * FROM lends WHERE lend_id = ?";
    try (PreparedStatement stmt = c.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) return mapRowToLend(rs);
      return null;
    }
  }

  @Override
  public List<Lend> findAll() throws SQLException {
    String sql = "SELECT * FROM lends";
    try (PreparedStatement stmt = c.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      List<Lend> lends = new ArrayList<>();
      while (rs.next()) lends.add(mapRowToLend(rs));
      return lends;
    }
  }

  @Override
  public List<Lend> findByBookId(int bookId) throws SQLException {
    return findByField("book_id", bookId);
  }

  @Override
  public List<Lend> findByLenderId(int userId) throws SQLException {
    return findByField("user_id", userId);
  }

  @Override
  public List<Lend> findByBorrowerId(int borrowerId) throws SQLException {
    return findByField("borrower_id", borrowerId);
  }

  @Override
  public List<Lend> findActiveLends() throws SQLException {
    String sql = "SELECT * FROM lends WHERE end_date IS NULL";
    try (PreparedStatement stmt = c.prepareStatement(sql))
    {
      ResultSet rs = stmt.executeQuery();
      List<Lend> lends = new ArrayList<>();
      while (rs.next())
      {
        lends.add(mapRowToLend(rs));
      }
      return lends;
    }
  }

  @Override
  public List<Lend> findReturnedLends() throws SQLException {
    String sql = "SELECT * FROM lends WHERE end_date IS NOT NULL";
    try (PreparedStatement stmt = c.prepareStatement(sql))
    {
      ResultSet rs = stmt.executeQuery();
      List<Lend> lends = new ArrayList<>();
      while (rs.next())
      {
        lends.add(mapRowToLend(rs));
      }
      return lends;
    }
  }

  @Override
  public void markAsReturned(int lendId) throws SQLException {
    // 1) turn off auto‐commit so we can atomically update both tables
    boolean oldAuto = c.getAutoCommit();
    c.setAutoCommit(false);

    try {
      // 2) lookup which book this lend refers to
      int bookId;
      String findSql = "SELECT book_id FROM lends WHERE lend_id = ?";
      try (PreparedStatement ps = c.prepareStatement(findSql)) {
        ps.setInt(1, lendId);
        try (ResultSet rs = ps.executeQuery()) {
          if (!rs.next()) {
            throw new SQLException("No lend found with id=" + lendId);
          }
          bookId = rs.getInt("book_id");
        }
      }

      // 3) update the lend’s end_date
      String lendSql = "UPDATE lends SET end_date = ? WHERE lend_id = ?";
      try (PreparedStatement ps = c.prepareStatement(lendSql)) {
        ps.setDate(1, Date.valueOf(LocalDate.now()));
        ps.setInt(2, lendId);
        ps.executeUpdate();
      }

      // 4) mark the book back to available in the same transaction
      String bookSql = "UPDATE books SET status = ? WHERE book_id = ?";
      try (PreparedStatement ps = c.prepareStatement(bookSql)) {
        ps.setString(1, "AVAILABLE");
        ps.setInt(2, bookId);
        ps.executeUpdate();
      }

      // 5) if everything succeeded
      c.commit();
    } catch (SQLException ex) {
      // rollback both lend‐ and book‐updates if anything went wrong
      c.rollback();
      throw ex;
    } finally {
      // restore original auto‐commit mode
      c.setAutoCommit(oldAuto);
    }
  }


  //  @Override
  //this is a method that does not record the book's status change
//  public void markAsReturned(int lendId) throws SQLException {
//    String sql = "UPDATE lends SET end_date = ? WHERE lend_id = ?";
//
//    try (PreparedStatement stmt = c.prepareStatement(sql)) {
//      stmt.setDate(1, Date.valueOf(LocalDate.now()));
//      stmt.setInt(2, lendId);
//      stmt.executeUpdate();
//    }
//  }

  @Override
  public void update(Lend lend) throws SQLException {
    String sql = "UPDATE lends SET user_id = ?, book_id = ?, borrower_id = ?, start_date = ?, end_date = ? WHERE lend_id = ?";
    try (PreparedStatement stmt = c.prepareStatement(sql)) {
      stmt.setInt(1, lend.getOwnerId());
      stmt.setInt(2, lend.getBookId());
      stmt.setInt(3, lend.getBorrowerId());
      stmt.setDate(4, Date.valueOf(lend.getStartDate()));
      if (lend.getEndDate() == null) {
        stmt.setNull(5, Types.DATE);
      } else {
        stmt.setDate(5, Date.valueOf(lend.getEndDate()));
      }
      stmt.setInt(6, lend.getLendId());
      stmt.executeUpdate();
    }
  }

  @Override
  public void delete(int id) throws SQLException {
    String sql = "DELETE FROM lends WHERE lend_id = ?";
    try (PreparedStatement stmt = c.prepareStatement(sql)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    }
  }

  private List<Lend> findByField(String fieldName, int value) throws SQLException {
    String sql = "SELECT * FROM lends WHERE " + fieldName + " = ?";
    try (PreparedStatement stmt = c.prepareStatement(sql)) {
      stmt.setInt(1, value);
      ResultSet rs = stmt.executeQuery();
      List<Lend> lends = new ArrayList<>();
      while (rs.next()) lends.add(mapRowToLend(rs));
      return lends;
    }
  }

  private List<Lend> findByBooleanField(String fieldName, boolean value) throws SQLException {
    String sql = "SELECT * FROM lends WHERE " + fieldName + " = ?";
    try (PreparedStatement stmt = c.prepareStatement(sql)) {
      stmt.setBoolean(1, value);
      ResultSet rs = stmt.executeQuery();
      List<Lend> lends = new ArrayList<>();
      while (rs.next()) lends.add(mapRowToLend(rs));
      return lends;
    }
  }


  private Lend mapRowToLend(ResultSet rs) throws SQLException {

        int id =rs.getInt("lend_id");
        int ownerId = rs.getInt("user_id");
        int bookId = rs.getInt("book_id");
        int borrowerId = rs.getInt("borrower_id");
        LocalDate startDate = rs.getDate("start_date").toLocalDate();
        LocalDate endDate   = (rs.getDate("end_date") != null)
            ? rs.getDate("end_date").toLocalDate()
            : null;


    return new Lend(id, ownerId, bookId, borrowerId, startDate, endDate);
  }
}

