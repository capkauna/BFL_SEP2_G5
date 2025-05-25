package Server.database;

import Server.model.Notification;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDAO
{
  List<Notification>  addNotification(int userId, String message, int bookId) throws SQLException;
  List<Notification> getNotifications(int userId) throws SQLException;
  List<Notification> markAsRead(int notificationId) throws SQLException;
  List<Notification> deleteNotification(int notificationId) throws SQLException;
}

