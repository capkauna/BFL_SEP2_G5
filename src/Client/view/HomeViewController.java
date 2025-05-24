package Client.view;

import javafx.fxml.FXML;
import Client.viewmodel.HomeVM;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HomeViewController
{
  @FXML private TextArea notificationLog;

  private ViewHandler viewHandler;
  private HomeVM viewModel;

  public void init(ViewHandler viewHandler, HomeVM vm) {
    this.viewHandler = viewHandler;
    this.viewModel = vm;

    notificationLog.setText("Notifications appears");
  }

  @FXML private void onMyLibrary(ActionEvent actionEvent) {
    viewModel.openView("MyLibraryView.fxml");
  }

  @FXML private void onMyAccount(ActionEvent actionEvent) {
    viewModel.openView("UserProfileView.fxml");
  }

  @FXML private void onFindBook(ActionEvent actionEvent){
    viewModel.openView("SearchView.fxml");
  }

  @FXML private void onFindUser(ActionEvent actionEvent){
    viewModel.openView("UserListView.fxml");
  }
}
