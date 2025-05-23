package Server.repository;

import Server.model.Lend;
import Server.repository.LendDAO;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcLendDAO implements LendDAO
{
  private final Connection connection;

  public JdbcLendDAO(Connection connection) {
    this.connection = connection;
  }

  @Override
  public Lend create(int userId, int bookId, int borrowerId) throws SQLException {
    String sql = "INSERT INTO lend (user_id, book_id, borrower_id, start_date) VALUES (?, ?, ?, CURRENT_DATE)";
    try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setInt(1, userId);
      stmt.setInt(2, bookId);
      stmt.setInt(3, borrowerId);
      stmt.executeUpdate();

      ResultSet keys = stmt.getGeneratedKeys();
      if (keys.next()) {
        int id = keys.getInt(1);
        return new Lend(id, userId, bookId, borrowerId, LocalDate.now());
      }
      throw new SQLException("Creating lend failed, no ID obtained.");
    }
  }

  @Override
  public Lend findById(int id) throws SQLException {
    String sql = "SELECT * FROM lend WHERE lend_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) return mapRowToLend(rs);
      return null;
    }
  }

  @Override
  public List<Lend> findAll() throws SQLException {
    String sql = "SELECT * FROM lend";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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

  @Override public List<Lend> findActiveLends() throws SQLException
  {
    return List.of();
  }

  @Override public List<Lend> findReturnedLends() throws SQLException
  {
    return List.of();
  }

  @Override public void markAsReturned(int lendId) throws SQLException
  {

  }

  @Override
  public void update(Lend lend) throws SQLException {
    String sql = "UPDATE lend SET user_id = ?, book_id = ?, borrower_id = ?, start_date = ?, end_date = ? WHERE lend_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, lend.getUserId());
      stmt.setInt(2, lend.getBookId());
      stmt.setInt(3, lend.getBorrowerId());
      stmt.setDate(4, Date.valueOf(lend.getStartDate()));
      if (lend.getEndDate() != null) {
        stmt.setDate(5, Date.valueOf(lend.getEndDate()));
      } else {
        stmt.setNull(5, Types.DATE);
      }
      stmt.setInt(6, lend.getId());
      stmt.executeUpdate();
    }
  }

  @Override
  public void delete(int id) throws SQLException {
    String sql = "DELETE FROM lend WHERE lend_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    }
  }

  // ---------------- Utility ----------------

  private List<Lend> findByField(String fieldName, int value) throws SQLException {
    String sql = "SELECT * FROM lend WHERE " + fieldName + " = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, value);
      ResultSet rs = stmt.executeQuery();
      List<Lend> lends = new ArrayList<>();
      while (rs.next()) lends.add(mapRowToLend(rs));
      return lends;
    }
  }

  private Lend mapRowToLend(ResultSet rs) throws SQLException {
    return new Lend(
        rs.getInt("lend_id"),
        rs.getInt("user_id"),
        rs.getInt("book_id"),
        rs.getInt("borrower_id"),
        rs.getDate("start_date").toLocalDate()
    );
  }
}
