package Client.viewmodel;

import Server.model.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class NotificationsVM
{
  public String username;
  private final ObservableList<Notification> notifications = FXCollections.observableArrayList();

  public NotificationsVM(String currentusername) {
    this.username = currentusername;
  }
  public ObservableList<Notification> getNotifications() {
    return notifications;
  }

  public void loadNotifications(List<Notification> list) {
    notifications.setAll(list);
  }
}
