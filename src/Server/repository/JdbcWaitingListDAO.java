package Server.repository;

import Server.model.Book;
import Server.model.User;
import Server.model.WaitingListEntry;
import Server.model.WaitingListRecord;
import Server.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//* Provides full CRUD access to the waiting_list table in the database.
// * Responsible for creating, reading, and deleting entries related to book reservations.
// * Uses PreparedStatement for safe SQL execution and manages connection lifecycles.
// * This class is a singleton, ensuring only one instance exists for database access.

public class JdbcWaitingListDAO implements WaitingListDAO
{
  private static JdbcWaitingListDAO instance;

  private JdbcWaitingListDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static JdbcWaitingListDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new JdbcWaitingListDAO();
    }
    return instance;
  }

  @Override
  public List<WaitingListEntry> addEntry(int userId, int bookId) throws SQLException{
    if (exists(userId, bookId).isEmpty()) {
      String sql = "INSERT INTO waiting_list (user_id, book_id, added_at) VALUES (?, ?, NOW())";
      try (Connection conn = DBConnection.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        stmt.setInt(2, bookId);
        stmt.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return getByBookId(bookId);
  }

  @Override
  public List<WaitingListEntry> removeEntry(int userId, int bookId) {
    String sql = "DELETE FROM waiting_list WHERE user_id = ? AND book_id = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setInt(2, bookId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return getByBookId(bookId);
  }

  //It ensures that only the selected user is removed,
  // * without affecting other users waiting for the same book.
  //* This method is typically used when a book is successfully lent to a user from the waiting list
  @Override
  public List<WaitingListEntry> removeEntryById(int entryId) {
    String sql = "DELETE FROM waiting_list WHERE entry_id = ?";
    int bookId = -1;
    try (Connection conn = DBConnection.getConnection()) {
      PreparedStatement findStmt = conn.prepareStatement("SELECT book_id FROM waiting_list WHERE entry_id = ?");
      findStmt.setInt(1, entryId);
      ResultSet rs = findStmt.executeQuery();
      if (rs.next()) {
        bookId = rs.getInt("book_id");
      }
      PreparedStatement deleteStmt = conn.prepareStatement(sql);
      deleteStmt.setInt(1, entryId);
      deleteStmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bookId != -1 ? getByBookId(bookId) : new ArrayList<>();
  }

  @Override
  public List<WaitingListEntry> exists(int userId, int bookId) {
    List<WaitingListEntry> result = new ArrayList<>();
    String sql = "SELECT w.entry_id, w.user_id, u.username, w.book_id, w.added_at, b.title " +
        "FROM waiting_list w " +
        "JOIN users u ON w.user_id = u.user_id " +
        "JOIN books b ON w.book_id = b.book_id " +
        "WHERE w.user_id = ? AND w.book_id = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setInt(2, bookId);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        result.add(mapResultSetToEntry(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  //Provides the list of those waiting for a specific book and the "lend" function.
  public List<WaitingListEntry> getByBookId(int bookId) {
    List<WaitingListEntry> list = new ArrayList<>();
    String sql = "SELECT w.entry_id, w.user_id, u.username, w.book_id, w.added_at, b.title " +
        "FROM waiting_list w " +
        "JOIN users u ON w.user_id = u.user_id " +
        "JOIN books b ON w.book_id = b.book_id " +
        "WHERE w.book_id = ? ORDER BY w.added_at ASC";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, bookId);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        list.add(mapResultSetToEntry(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  @Override
  public List<WaitingListRecord> findAll() throws SQLException {
    String sqlQuery = "SELECT entry_id, book_id, user_id, added_at FROM waiting_list";
    List<WaitingListRecord> waitingList = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        int entryId = rs.getInt("entry_id");
        int bookId = rs.getInt("book_id");
        int userId = rs.getInt("user_id");
        LocalDateTime addedAt = rs.getTimestamp("added_at").toLocalDateTime();
        waitingList.add(new WaitingListRecord(userId, bookId, entryId, addedAt));
      }
    }
    return waitingList;
  }


  private WaitingListEntry mapResultSetToEntry(ResultSet rs) throws SQLException {
    int entryId = rs.getInt("entry_id");
    int userId = rs.getInt("user_id");
    String username = rs.getString("username");
    int bookId = rs.getInt("book_id");
    String title = rs.getString("title");
    LocalDateTime addedAt = rs.getTimestamp("added_at").toLocalDateTime();

    User user = new User();
    user.setUserId(userId);
    user.setUsername(username);

    Book book = new Book();
    book.setBookId(bookId);
    book.setTitle(title);

    return new WaitingListEntry(entryId, user, book, addedAt);
  }
}

