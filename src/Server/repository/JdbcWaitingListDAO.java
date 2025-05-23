package Server.repository;

import Server.model.Book;
import Server.model.User;
import Server.model.WaitingListEntry;
import Server.model.WaitingListRecord;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
  public boolean addEntry(int userId, int bookId) {
    if (exists(userId, bookId)) return false;
    String sql = "INSERT INTO waiting_list (user_id, copy_id, added_at) VALUES (?, ?, NOW())";
    try (Connection conn = util.DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setInt(2, bookId);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean removeEntry(int userId, int bookId) { //Remove me from the waiting list
    String sql = "DELETE FROM waiting_list WHERE user_id = ? AND copy_id = ?";
    try (Connection conn = util.DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setInt(2, bookId);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean removeEntryById(int entryId) { //Used for delete after the book was borrowed.
    String sql = "DELETE FROM waiting_list WHERE entry_id = ?";
    try (Connection conn = util.DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, entryId);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean exists(int userId, int bookId) {
    String sql = "SELECT 1 FROM waiting_list WHERE user_id = ? AND copy_id = ?";
    try (Connection conn = util.DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setInt(2, bookId);
      ResultSet rs = stmt.executeQuery();
      return rs.next();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
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

    try (Connection conn = util.DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, bookId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        int entryId = rs.getInt("entry_id");
        int userId = rs.getInt("user_id");
        String username = rs.getString("username");
        String title = rs.getString("title");
        LocalDateTime addedAt = rs.getTimestamp("added_at").toLocalDateTime();

        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);

        Book book = new Book();
        book.setBookId(bookId);
        book.setTitle(title);

        list.add(new WaitingListEntry(entryId, user, book, addedAt));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

    @Override
    public List<WaitingListEntry> findAll() throws SQLException
    {
      String sqlQuery = """
        SELECT b.entry_id, b.book_id, b.user_id, b.added_at
          FROM waiting_list b
        """;
      List<WaitingListEntry> waitingList = new ArrayList<>();
      try (Connection c = util.DBConnection.getConnection();
          PreparedStatement ps = c.prepareStatement(sqlQuery);
          ResultSet rs = ps.executeQuery())
      {
        while (rs.next())
        {
          waitingList.add(mapResultSetToBook(rs));
        }
      }
      return waitingList;
    }



    private WaitingListRecord mapResultSetToBook(ResultSet rs) throws SQLException
  {
    int entryId = rs.getInt("entry_id");
    int bookId = rs.getInt("book_id");
    int userId = rs.getInt("user_id");
    Timestamp addedAt = rs.getTimestamp("added_at");

    return new WaitingListRecord(entryId, bookId, userId,
        addedAt.toLocalDateTime());
  }
}
