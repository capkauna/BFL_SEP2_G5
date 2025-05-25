package Client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import Client.viewmodel.SearchVM;

public class SearchViewController
{
  @FXML private TextField searchField;
  @FXML private Button searchButton;
  @FXML private ChoiceBox<String> genreChoice;
  @FXML private ChoiceBox<String> typeChoice;
  @FXML private ChoiceBox<String> ownerChoice;
  @FXML private ChoiceBox<String> borrowedChoice;
  @FXML private ListView<String> searchResults;

  private ViewHandler viewHandler;
  private SearchVM viewModel;

  public void init(ViewHandler vh, SearchVM vm) {
    this.viewHandler = vh;
    this.viewModel = vm;

    genreChoice.setItems(viewModel.getGenres());
    typeChoice.setItems(viewModel.getFormats());
    ownerChoice.setItems(viewModel.getOwnerUsernames());
    borrowedChoice.setItems(viewModel.getBorrowerUsernames());

    searchResults.setItems(viewModel.getSearchResults());
  }

  @FXML private void onSearchClicked() {
    String query = searchField.getText();
    viewModel.performSearch(query);
  }

  @FXML private void onBackClicked() {
    viewHandler.openView("HomeView.fxml");
  }
}

//  @FXML
//  private TextField searchField, genreField, formatField, ownerField, borrowerField, statusField;
//  @FXML
//  private ListView<String> resultsList;
//  private SearchVM viewModel;
//
//  public void init(SearchVM vm) {
//    this.viewModel = vm;
//  }
//
//  @FXML
//  private void onSearchClick() {
//    var results =viewModel.search(
//        searchField.getText(),
//        genreField.getText(),
//        formatField.getText(),
//        ownerField.getText(),
//        borrowerField.getText(),
//        statusField.getText()
//    );
//    resultsList.getItems().setAll(results);
//  }
//}
