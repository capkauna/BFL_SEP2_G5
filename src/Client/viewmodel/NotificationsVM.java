package Client.viewmodel;

import Server.model.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class NotificationsVM
{
  private final ObservableList<Notification> notifications = FXCollections.observableArrayList();

  public ObservableList<Notification> getNotifications() {
    return notifications;
  }

  public void loadNotifications(List<Notification> list) {
    notifications.setAll(list);
  }
}
