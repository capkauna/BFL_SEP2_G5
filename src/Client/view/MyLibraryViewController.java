package Client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import Client.viewmodel.MyLibraryVM;

import java.awt.event.ActionEvent;

public class MyLibraryViewController
{
  @FXML private ListView<String> libraryList;
  @FXML private Button addBookButton;
  @FXML private Button viewBookButton;
  @FXML private Button removeBookButton;

  private ViewHandler viewHandler;
  private MyLibraryVM viewModel;

  public void init(ViewHandler viewHandler, MyLibraryVM vm) {
    this.viewHandler = viewHandler;
    this.viewModel = vm;

    bookListView.setItems(viewModel.getMyBooks());
  }


  @FXML private void onAddBookClicked(ActionEvent actionEvent) {
    viewHandler.openView("BookInfoView.fxml");
  }

  @FXML private void onViewBookClicked(ActionEvent actionEvent) {
    viewHandler.openView("BookInfoView.fxml");
  }

  @FXML private void onRemoveBookClicked(ActionEvent actionEvent) {
    viewModel.removeSelectedBook(bookListView.getSelectionModel().getSelectedItem());
  }


}
