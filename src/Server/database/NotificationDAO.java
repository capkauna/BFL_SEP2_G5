package Server.database;

import Server.model.Notification;

import java.sql.SQLException;
import java.util.ArrayList;

public interface NotificationDAO
{
  ArrayList<Notification>  addNotification(int userId, String message, int bookId) throws SQLException;
  ArrayList<Notification> getNotifications(int userId) throws SQLException;
  ArrayList<Notification> markAsRead(int notificationId) throws SQLException;
  ArrayList<Notification> deleteNotification(int notificationId) throws SQLException;
}

