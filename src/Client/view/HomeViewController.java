package Client.view;

import Client.viewmodel.HomeVM;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;


public class HomeViewController {
  @FXML private TextArea notificationLog;
@FXML private Button myLibraryButton;
@FXML private Button myAccountButton;
@FXML private Button findBookButton;
@FXML private Button findUserButton;

  private ViewHandler viewHandler;
  private HomeVM viewModel;


  public void init(ViewHandler viewHandler, HomeVM viewModel) {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;
    notificationLog.setText("Notifications appear here.");
  }

  @FXML private void onMyLibrary(ActionEvent actionEvent) {
    viewHandler.openView("/Client/view/MyLibraryView.fxml");
  }

  @FXML private void onMyAccount(ActionEvent actionEvent) {
    viewHandler.openView("/Client/view/UserPageView.fxml");
  }

  @FXML private void onFindBook(ActionEvent actionEvent){
    viewHandler.openView("/Client/view/SearchView.fxml");
  }

  @FXML private void onFindUser(ActionEvent actionEvent){
    viewHandler.openView("/Client/view/UserListView.fxml");
  }
}
