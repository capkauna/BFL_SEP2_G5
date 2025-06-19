package Client.view;

import Client.viewmodel.UserListVM;
import Client.viewmodel.UserSummaryVM;
import Shared.dto.FullUserDTO;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class UserListViewController {

  @FXML private TableView<FullUserDTO> userTable;
  @FXML private TableColumn<FullUserDTO, String> nameColumn;
  @FXML private TableColumn<FullUserDTO, Integer> libraryColumn;
  @FXML private TableColumn<FullUserDTO, Integer> readColumn;

  private ViewHandler viewHandler;
  private UserListVM viewModel;


  public void init(ViewHandler viewHandler, UserListVM viewModel)
      throws IOException, ClassNotFoundException
  {
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

