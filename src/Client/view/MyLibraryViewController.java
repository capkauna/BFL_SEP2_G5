package Client.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import Client.viewmodel.MyLibraryVM;

public class MyLibraryViewController
{
  @FXML private ListView<String> libraryList;
  @FXML private Button addBookButton;
  @FXML private Button viewBookButton;
  @FXML private Button removeBookButton;
  //private ActionEvent actionEvent;

  private ViewHandler viewHandler;
  private MyLibraryVM viewModel;


  public void init(ViewHandler viewHandler, MyLibraryVM vm) {
    this.viewHandler = viewHandler;
    this.viewModel = vm;

    libraryList.setItems(viewModel.getMyBooks());
  }

  public void onAddBookClicked(ActionEvent actionEvent)
  {
    viewHandler.openView("BookInfoView.fxml");
  }

  public void onViewBookClicked(ActionEvent actionEvent)
  {
    viewHandler.openView("BookInfoView.fxml");
  }

  public void onRemoveBookClicked(ActionEvent actionEvent)
  {
    String selectedBook = libraryList.getSelectionModel().getSelectedItem();
    if (selectedBook != null) {
      viewModel.removeSelectedBook(selectedBook);
    }
  }


  @FXML
  public void onBackClicked(ActionEvent actionEvent) {
    viewHandler.openView("HomeView.fxml");
  }
}
