package Server.database;

import Server.model.Book;
import Server.model.User;
import Server.model.actionmanagers.MarkAsRead;
import Server.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcMarkAsReadDAO implements MarkAsReadDAO {
  private static JdbcMarkAsReadDAO instance;
  private final UserDAO userDAO = JdbcUserDAO.getInstance();
  private final BookDAO bookDAO = JdbcBookDAO.getInstance();

  private JdbcMarkAsReadDAO() throws SQLException
  {}

  public static synchronized JdbcMarkAsReadDAO getInstance() throws SQLException {
    if (instance == null) {
      instance = new JdbcMarkAsReadDAO();
    }
    return instance;
  }

  @Override
  public MarkAsRead create(MarkAsRead entry) throws SQLException {
    String sql = """
        INSERT INTO read (user_id, book_id, is_read, comment)
        VALUES (?, ?, ?, ?)
        RETURNING record_id
        """;

    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, entry.getUser().getUserId());
      ps.setInt(2, entry.getBook().getBookId());
      ps.setBoolean(3, entry.isRead());
      ps.setString(4, entry.getNotes());

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          entry.setId(rs.getInt("record_id"));
        }
      }
    }

    return entry;
  }

  @Override
  public MarkAsRead findById(int id) throws SQLException {
    String sql = """
        SELECT * FROM read WHERE record_id = ?
        """;

    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapToMarkAsRead(rs);
      }
    }
    return null;
  }

  @Override
  public MarkAsRead findByUserAndBook(int userId, int bookId) throws SQLException {
    String sql = """
        SELECT * FROM read WHERE user_id = ? AND book_id = ?
        """;
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
      ps.setInt(2, bookId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapToMarkAsRead(rs);
      }
    }
    return null;
  }

  @Override
  public List<MarkAsRead> findByUser(int userId) throws SQLException {
    List<MarkAsRead> list = new ArrayList<>();
    String sql = """
        SELECT * FROM read WHERE user_id = ?
        """;
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(mapToMarkAsRead(rs));
        }
      }
    }
    return list;
  }

  @Override
  public List<MarkAsRead> findAll() throws SQLException {
    List<MarkAsRead> list = new ArrayList<>();
    String sql = """
        SELECT * FROM read
        """;

    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        list.add(mapToMarkAsRead(rs));
      }
    }
    return list;
  }

  @Override
  public void update(MarkAsRead entry) throws SQLException {
    String sql = """
        UPDATE read SET is_read = ?, comment = ?
        WHERE record_id = ?
        """;


    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setBoolean(1, entry.isRead());
      ps.setString(2, entry.getNotes());
      ps.setInt(3, entry.getId());
      ps.executeUpdate();
    }
  }

  @Override
  public void save(MarkAsRead entry) throws SQLException {
    MarkAsRead existing = findByUserAndBook(entry.getUser().getUserId(), entry.getBook().getBookId());
    if (existing == null) {
      create(entry);
    } else {
      entry.setId(existing.getId());
      update(entry);
    }
  }

  @Override
  public void delete(int id) throws SQLException {
    String sql = """
        DELETE FROM read WHERE record_id = ?
        """;

    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, id);
      ps.executeUpdate();
    }
  }

  private MarkAsRead mapToMarkAsRead(ResultSet rs) throws SQLException {
    int id = rs.getInt("record_id");
    int userId = rs.getInt("user_id");
    int bookId = rs.getInt("book_id");
    boolean isRead = rs.getBoolean("is_read");
    String comment = rs.getString("comment");

    User user = userDAO.findById(userId);
    Book book = bookDAO.findById(bookId);

    MarkAsRead entry = new MarkAsRead(user, book, isRead, comment);
    entry.setId(id);
    return entry;
  }
}
