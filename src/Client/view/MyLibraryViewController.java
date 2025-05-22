package view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import viewmodel.MyLibraryVM;

public class MyLibraryViewController
{
  @FXML private ListView<String> libraryList;
  private MyLibraryVM viewModel;

  public void init(MyLibraryVM vm) {
    this.viewModel = vm;
    updateList();
  }

  private void updateList() {
    libraryList.getItems().setAll(viewModel.getMyBooks());
  }

  @FXML private void onBookSelected() {
    String selected = libraryList.getSelectionModel().getSelectedItem();
    if (selected != null) {
      viewModel.selectedBookByTitle(selected);
    }
  }

  @FXML private void onEditClicked() {
    viewModel.editSelectedBook();
  }

  @FXML private void onDeleteClicked() {
    viewModel.deleteSelectedBook();
    updateList(); //update after deleting
  }

  @FXML private void onBackClicked() {
    viewModel.goHome();
  }
}
