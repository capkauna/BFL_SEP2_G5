package Client.view;

import Client.viewmodel.UserListVM;
import Client.viewmodel.UserSummaryVM;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserListViewController {

  @FXML private TableView<UserSummaryVM> userTable;
  @FXML private TableColumn<UserSummaryVM, String> nameColumn;
  @FXML private TableColumn<UserSummaryVM, Integer> libraryColumn;
  @FXML private TableColumn<UserSummaryVM, Integer> readColumn;

  private ViewHandler viewHandler;
  private UserListVM viewModel;


  public void init(ViewHandler viewHandler, UserListVM viewModel) {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
    libraryColumn.setCellValueFactory(new PropertyValueFactory<>("booksInLibrary"));
    readColumn.setCellValueFactory(new PropertyValueFactory<>("booksRead"));

    userTable.setItems(viewModel.getUsers());
  }

  @FXML
  private void onBack() {
    viewHandler.openView("Client/view/HomeView.fxml");
  }
}

