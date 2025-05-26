package Server.database;

import Server.model.Book;
import Server.model.Lend;
import Server.model.User;
import Server.model.status.Available;
import Server.model.status.Borrowed;
import Server.model.status.Status;
import Server.dbstart.DBConnection;
import Shared.dto.enums.Format;
import Shared.dto.enums.Genre;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JdbcLendDAO implements LendDAO {
  private final Connection c;
  private static JdbcBookDAO instance;
  private final UserDAO userDao;

  public JdbcLendDAO(Connection connection) throws SQLException
  {
    this.c = connection;
    this.userDao = JdbcUserDAO.getInstance();

  }

  public static LendDAO getInstance() {
    try {
      return new JdbcLendDAO(DBConnection.getConnection());
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }


  //this method creates a lend and updates the book's status in a single transaction
  @Override

  public Lend create(Lend lend) throws SQLException {
    boolean oldAuto = c.getAutoCommit();
    c.setAutoCommit(false);

    try {
      // ——— A) Load domain objects ———
      Book book = loadBook(lend.getBookId());
      User borrower = loadUser(lend.getBorrowerId());

      // ——— B) Run the domain action ———
      book.lendTo(borrower);

      // ——— C) Persist the new Lend row ———
      String lendSql =
          "INSERT INTO lends (user_id, book_id, borrower_id, start_date) " +
              "VALUES (?, ?, ?, CURRENT_DATE) RETURNING lend_id";
      try (PreparedStatement ps = c.prepareStatement(lendSql)) {
        ps.setInt(1, lend.getOwnerId());
        ps.setInt(2, lend.getBookId());
        ps.setInt(3, lend.getBorrowerId());
        try (ResultSet rs = ps.executeQuery()) {
          if (rs.next()) lend.setLendId(rs.getInt("lend_id"));
        }
      }

      // ——— D) Persist the updated book status ———
      JdbcBookDAO.getInstance().update(book);

      c.commit();
      return lend;
    } catch (SQLException ex) {
      c.rollback();
      throw ex;
    } finally {
      c.setAutoCommit(oldAuto);
    }
  }





  //  @Override
  //  public Lend create(Lend lend) throws SQLException {
  //    String sql =
  //        "INSERT INTO lends (user_id, book_id, borrower_id, start_date) " +
  //            "VALUES (?, ?, ?, CURRENT_DATE) RETURNING lend_id";
  //    try (PreparedStatement stmt = c.prepareStatement(sql)) {
  //      stmt.setInt(1, lend.getOwnerId());
  //      stmt.setInt(2, lend.getBookId());
  //      stmt.setInt(3, lend.getBorrowerId());
  //
  //      ResultSet rs = stmt.executeQuery();
  //
  //    ResultSet keys = stmt.executeQuery();
  //      if (keys.next()) {
  //        lend.setLendId(keys.getInt(1));
  //      }
  //      return lend;
  //    }
  //  }


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
  public void markAsReturned(int lendId, int userId) throws SQLException {
    boolean oldAuto = c.getAutoCommit();
    c.setAutoCommit(false);

    try {
      // ——— A) Find the Lend record & bookId ———
      int bookId;
      LocalDate start;
      try (PreparedStatement ps = c.prepareStatement(
          "SELECT book_id, start_date FROM lends WHERE lend_id = ?"
      )) {
        ps.setInt(1, lendId);
        try (ResultSet rs = ps.executeQuery()) {
          if (!rs.next()) throw new SQLException("No lend " + lendId);
          bookId = rs.getInt("book_id");
          start  = rs.getDate("start_date").toLocalDate();
        }
      }

      // ——— B) Load domain book and call returnBook() ———
      Book book = loadBook(bookId);
      User accessUser = userDao.findById(userId);
      book.markAsReturned(accessUser);

      // ——— C) Update the lend end_date ———
      try (PreparedStatement ps = c.prepareStatement(
          "UPDATE lends SET end_date = ? WHERE lend_id = ?"
      )) {
        ps.setDate(1, Date.valueOf(LocalDate.now()));
        ps.setInt(2, lendId);
        ps.executeUpdate();
      }

      // ——— D) Persist the updated book status ———
      JdbcBookDAO.getInstance().update(book);

      c.commit();
    } catch (SQLException ex) {
      c.rollback();
      throw ex;
    } finally {
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



  // Helpers you can add to this DAO to avoid duplicating mapping logic:
  private Book mapRowToBook(ResultSet rs) throws SQLException {
    int    id          = rs.getInt("book_id");
    String title       = rs.getString("title");
    String author      = rs.getString("author");
    String rawGenre    = rs.getString("genre");    // e.g. "Science Fiction"
    String isbn        = rs.getString("isbn");
    String rawFormat   = rs.getString("format");   // e.g. "HARDCOVER"
    String description = rs.getString("description");
    String image       = rs.getString("image");
    int    ownerId     = rs.getInt("owner_id");
    String rawStatus   = rs.getString("status");  // e.g. "Borrowed by ash"
    int    year        = rs.getInt("year");

    // 1) Map the genreName back to the enum constant
    Genre genre = Arrays.stream(Genre.values())
        .filter(g -> g.getGenreName().equalsIgnoreCase(rawGenre))
        .findFirst()
        .orElseThrow(() ->
            new SQLException("Unknown genre in DB: " + rawGenre)
        );

    // 2) Parse the format via your helper
    Format format = Format.fromString(rawFormat);

    // 3) Lookup owner
    User owner = userDao.findById(ownerId);

    // 4) Parse status string
    Status status = parseStatus(rawStatus);

    return new Book(
        id,
        title,
        author,
        year,
        genre,
        isbn,
        format,
        description,
        image,
        owner,
        status
    );
  }

  private Status parseStatus(String rawStatus) throws SQLException {
    if (rawStatus == null || rawStatus.isBlank() || rawStatus.equalsIgnoreCase("AVAILABLE")) {
      return new Available();
    }

    String prefix = "Borrowed by ";
    if (rawStatus.startsWith(prefix)) {
      String borrowerName = rawStatus.substring(prefix.length());
      User borrower = userDao.findByUserName(borrowerName);
      if (borrower == null) {
        throw new SQLException("No such borrower username in status: " + borrowerName);
      }
      return new Borrowed(borrower);
    }

    // fallback
    return new Available();
  }

  private User mapRowToUser(ResultSet rs) throws SQLException {
    return User.fromDb(
        rs.getInt("user_id"),
        rs.getString("username"),
        rs.getString("full_name"),
        rs.getString("email"),
        rs.getString("hashed_pw"),
        rs.getString("phone_number"),
        rs.getString("address"),
        rs.getString("avatar")
    );
  }

  private Book loadBook(int bookId) throws SQLException {
    try (PreparedStatement ps = c.prepareStatement(
        "SELECT * FROM books WHERE book_id = ?"
    )) {
      ps.setInt(1, bookId);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) throw new SQLException("Book not found: " + bookId);
        return mapRowToBook(rs);
      }
    }
  }

  private User loadUser(int userId) throws SQLException {
    try (PreparedStatement ps = c.prepareStatement(
        "SELECT * FROM users WHERE user_id = ?"
    )) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) throw new SQLException("User not found: " + userId);
        return mapRowToUser(rs);
      }
    }
  }


}

