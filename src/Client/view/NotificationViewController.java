package Client.view;

import Client.viewmodel.NotificationsVM;
import Server.model.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class NotificationViewController
{
  @FXML private ListView<String> notificationList;
  private NotificationsVM viewModel;

  public void init(NotificationsVM vm) {
    this.viewModel = vm;
    viewModel.getNotifications().forEach(n ->
        notificationList.getItems().add(formatNotification(n))
    );
  }

  private String formatNotification(Notification n) {
    return "[" + n.getNotificationDate() + "] " + n.getMessage();
  }
}
