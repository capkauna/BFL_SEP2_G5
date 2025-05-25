package Server.dummydata;

import Server.model.Notification;
import Server.database.JdbcNotificationDAO;
import Server.database.NotificationDAO;

import java.sql.SQLException;
import java.util.List;

public class DummyNotification
{
  public static void main(String[] args) {
    try {
      NotificationDAO notificationDAO = JdbcNotificationDAO.getInstance();
      int testUserId = 1;
      int bookId = 1;
      // Call your test method
      testReadStatus(notificationDAO, testUserId, bookId);

    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  // 🔽 ADD THIS BELOW main()
  public static void testReadStatus(NotificationDAO notificationDAO, int userId, int bookId) throws SQLException {
    System.out.println("\n=== TEST: UNREAD vs READ STATUS ===\n");

        // 1. Add notification with [UNREAD]
        String message = "[UNREAD] You have a new book available.";
        notificationDAO.addNotification(userId, message, bookId);
    System.out.println("Added new notification with status: [UNREAD]");

        // 2. Read and show all notifications
        List<Notification> all = notificationDAO.getNotifications(userId);
    System.out.println("\\nAll Notifications BEFORE marking as read:");
        all.forEach(n ->
            System.out.println("[\" + n.getNotificationId() + \"] \" + n.getMessage() + \" (\" + n.getNotificationDate() + \")"));

                // 3. Mark the first one as read
    if (!all.isEmpty()) {
      int targetId = all.get(0).getNotificationId();
      notificationDAO.markAsRead(targetId);
      System.out.println("\\nMarked notification ID \" + targetId + \" as [READ]");
    }

    // 4. Read and show all notifications again
    List<Notification> updated = notificationDAO.getNotifications(userId);
    System.out.println("\\nAll Notifications AFTER marking as read:");
        updated.forEach(n ->
            System.out.println("[\" + n.getNotificationId() + \"] \" + n.getMessage() + \" (\" + n.getNotificationDate() + \")"));
  }
  
//      // 1. CREATE - add a notification
//      System.out.println("--- ADD NOTIFICATION ---");
//      List<Notification> afterAdd = notificationDAO.addNotification(testUserId, "Reminder: Your borrowed book is due tomorrow.", 1);
//      afterAdd.forEach(n ->
//          System.out.println(n.getNotificationId() + ": " + n.getMessage() + " (" + n.getNotificationDate() + ")"));
//
//      // 2. READ - get notifications
//      System.out.println("\n--- GET NOTIFICATIONS ---");
//      List<Notification> allNotifications = notificationDAO.getNotifications(testUserId);
//      allNotifications.forEach(n ->
//          System.out.println(n.getNotificationId() + ": " + n.getMessage() + " (" + n.getNotificationDate() + ")"));
//
//      // 3. UPDATE - mark first as read (updates message prefix)
//      if (!allNotifications.isEmpty()) {
//        int notificationIdToMark = allNotifications.get(0).getNotificationId();
//        System.out.println("\n--- MARK AS READ (ID: " + notificationIdToMark + ") ---");
//        List<Notification> afterUpdate = notificationDAO.markAsRead(notificationIdToMark);
//        afterUpdate.forEach(n ->
//            System.out.println(n.getNotificationId() + ": " + n.getMessage() + " (" + n.getNotificationDate() + ")"));
//      }
//
//      // 4. DELETE - delete last notification
//      if (!allNotifications.isEmpty()) {
//        int notificationIdToDelete = allNotifications.get(allNotifications.size() - 1).getNotificationId();
//        System.out.println("\n--- DELETE NOTIFICATION (ID: " + notificationIdToDelete + ") ---");
//        List<Notification> afterDelete = notificationDAO.deleteNotification(notificationIdToDelete);
//        afterDelete.forEach(n ->
//            System.out.println(n.getNotificationId() + ": " + n.getMessage() + " (" + n.getNotificationDate() + ")"));
//      }
//
//    } catch (SQLException e) {
//      System.out.println("Error during notification test: " + e.getMessage());
//    }
//  }
}
