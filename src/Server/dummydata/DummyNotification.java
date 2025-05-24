package Server.dummydata;

import Server.model.Notification;
import Server.repository.JdbcNotificationDAO;
import Server.repository.NotificationDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DummyNotification
{
  public static void main(String[] args) {
    try {
      NotificationDAO notificationDAO = JdbcNotificationDAO.getInstance();


      Notification newNotif = new Notification(
          0,
          1,
          "This is a test notification for book lending.",
          LocalDateTime.now(),
          2
      );

      // save the notification in the database
      notificationDAO.addNotification(newNotif);
      System.out.println("Notification added!");

      // display all notifications for a user
      List<Notification> userNotifications = notificationDAO.getNotificationsForUser(1);
      for (Notification n : userNotifications) {
        System.out.println(n.getNotificationDate() + " | Book ID: " + n.getBookId() + " | " + n.getMessage());
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
