package Client.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import Client.viewmodel.SearchVM;

public class SearchViewController
{
  @FXML
  private TextField searchField, genreField, formatField, ownerField, borrowerField, statusField;
  @FXML
  private ListView<String> resultsList;
  private SearchVM viewModel;

  public void init(SearchVM vm) {
    this.viewModel = vm;
  }

  @FXML
  private void onSearchClick() {
    var results =viewModel.search(
        searchField.getText(),
        genreField.getText(),
        formatField.getText(),
        ownerField.getText(),
        borrowerField.getText(),
        statusField.getText()
    );
    resultsList.getItems().setAll(results);
  }
}
