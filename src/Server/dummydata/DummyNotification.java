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
      int testUserId = 1;

      // 1. CREATE - add a notification
      System.out.println("--- ADD NOTIFICATION ---");
      List<Notification> afterAdd = notificationDAO.addNotification(testUserId, "Reminder: Your borrowed book is due tomorrow.", 1);
      afterAdd.forEach(n ->
          System.out.println(n.getNotificationId() + ": " + n.getMessage() + " (" + n.getNotificationDate() + ")"));

      // 2. READ - get notifications
      System.out.println("\n--- GET NOTIFICATIONS ---");
      List<Notification> allNotifications = notificationDAO.getNotifications(testUserId);
      allNotifications.forEach(n ->
          System.out.println(n.getNotificationId() + ": " + n.getMessage() + " (" + n.getNotificationDate() + ")"));

      // 3. UPDATE - mark first as read
      if (!allNotifications.isEmpty()) {
        int notificationIdToMark = allNotifications.get(0).getNotificationId();
        System.out.println("\n--- MARK AS READ (ID: " + notificationIdToMark + ") ---");
        List<Notification> afterUpdate = notificationDAO.markAsRead(notificationIdToMark);
        afterUpdate.forEach(n ->
            System.out.println(n.getNotificationId() + ": " + n.getMessage() + " (" + n.getNotificationDate() + ")"));
      }

      // 4. DELETE - delete last notification
      if (!allNotifications.isEmpty()) {
        int notificationIdToDelete = allNotifications.get(allNotifications.size() - 1).getNotificationId();
        System.out.println("\n--- DELETE NOTIFICATION (ID: " + notificationIdToDelete + ") ---");
        List<Notification> afterDelete = notificationDAO.deleteNotification(notificationIdToDelete);
        afterDelete.forEach(n ->
            System.out.println(n.getNotificationId() + ": " + n.getMessage() + " (" + n.getNotificationDate() + ")"));
      }

    } catch (SQLException e) {
      System.out.println("Error during notification test: " + e.getMessage());
    }
  }
}
