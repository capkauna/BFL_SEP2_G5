package Server.database;

import Server.model.Notification;
import Server.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcNotificationDAO implements NotificationDAO
{
  private static JdbcNotificationDAO instance;
  private static final String URL = "jdbc:postgresql://localhost:5432/bestfriendlibrary";
  private static final String USER = "postgres";
  private static final String PASSWORD = "Mitsi2018";

  private JdbcNotificationDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static JdbcNotificationDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new JdbcNotificationDAO();
    }
    return instance;
  }

  @Override
  public List<Notification> addNotification(int userId, String message, int bookId) throws SQLException {
    String sql = "INSERT INTO notification_log (user_id, message, notification_date, book_id) VALUES (?, ?, NOW(), ?)";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setString(2, message);
      stmt.setInt(3, bookId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return getNotifications(userId);
  }

  @Override
  public List<Notification> getNotifications(int userId) {
    List<Notification> notifications = new ArrayList<>();
    String sql = "SELECT * FROM notification_log WHERE user_id = ? ORDER BY notification_date DESC";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        notifications.add(mapResultSetToNotification(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return notifications;
  }

  @Override
  public List<Notification> markAsRead(int notificationId) {
    String sql = "UPDATE notification_log SET message = REPLACE(message, '[UNREAD]', '[READ]') WHERE notification_id = ?";
    int userId = -1;
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, notificationId);
      stmt.executeUpdate();

      // Retrieve userId from notification for updated list
      try (PreparedStatement ps = conn.prepareStatement("SELECT user_id FROM notification_log WHERE notification_id = ?")) {
        ps.setInt(1, notificationId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
          userId = rs.getInt("user_id");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return (userId != -1) ? getNotifications(userId) : new ArrayList<>();
  }

  @Override
  public List<Notification> deleteNotification(int notificationId) {
    int userId = -1;
    try (Connection conn = DBConnection.getConnection()) {
      // Get user ID first
      try (PreparedStatement ps = conn.prepareStatement("SELECT user_id FROM notification_log WHERE notification_id = ?")) {
        ps.setInt(1, notificationId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
          userId = rs.getInt("user_id");
        }
      }
      // Delete notification
      try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM notification_log WHERE notification_id = ?")) {
        stmt.setInt(1, notificationId);
        stmt.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return (userId != -1) ? getNotifications(userId) : new ArrayList<>();
  }

  private Notification mapResultSetToNotification(ResultSet rs) throws SQLException {
    int id = rs.getInt("notification_id");
    int userId = rs.getInt("user_id");
    String message = rs.getString("message");
    LocalDateTime notificationDate = rs.getTimestamp("notification_date").toLocalDateTime();
    int bookId = rs.getInt("book_id");

    return new Notification(id, userId, message, notificationDate, bookId);
  }
}