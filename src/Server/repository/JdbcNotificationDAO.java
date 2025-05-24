package Server.repository;

import Server.model.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Server.util.DBConnection.getConnection;

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
  public void addNotification(Notification notification) {
    String sql = "INSERT INTO notification_log (user_id, message, book_id) VALUES (?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, notification.getUserId());
      stmt.setString(2, notification.getMessage());
      stmt.setInt(3, notification.getBookId());
      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace(); // Poți înlocui cu logging dacă ai un sistem de log
    }
  }

  @Override
  public List<Notification> getNotificationsForUser(int userId) {
    List<Notification> list = new ArrayList<>();
    String sql = "SELECT * FROM notification_log WHERE user_id = ? ORDER BY notification_date DESC";

    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Notification notification = new Notification(
            rs.getInt("notification_id"),
            rs.getInt("user_id"),
            rs.getString("message"),
            rs.getTimestamp("notification_date").toLocalDateTime(),
            rs.getInt("book_id")
        );
        list.add(notification);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return list;
  }
}
