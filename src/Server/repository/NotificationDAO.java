package Server.repository;

import Server.model.Notification;

import java.util.List;

public interface NotificationDAO
{
  void addNotification(Notification notification);
  List<Notification> getNotificationsForUser(int userId);
}

