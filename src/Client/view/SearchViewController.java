package Client.view;

import Client.viewmodel.SearchVM;
import Server.model.Book;
import Shared.dto.BookSummaryDTO;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchViewController {

  @FXML private TextField searchField;
  @FXML private Button searchButton;
  @FXML private Button backButton;
  @FXML private Button viewbook;

  @FXML private ChoiceBox<String> genreChoice;
  @FXML private ChoiceBox<String> typeChoice;
  @FXML private ChoiceBox<String> ownerChoice;
  @FXML private ChoiceBox<String> borrowedChoice;

  @FXML private TableView<BookSummaryDTO> bookTable;
  @FXML private TableColumn<Book, String> titlecolumn;
  @FXML private TableColumn<Book, String> authorcolumn;
  @FXML private TableColumn<Book, String> ownercolumn;
  @FXML private TableColumn<Book, String> formatcolumn;
  @FXML private TableColumn<Book, String> statuscolumn;

  private ViewHandler viewHandler;
  private SearchVM viewModel;

  public void init(ViewHandler viewHandler, SearchVM viewModel) {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;

    // Setup columns
    titlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    authorcolumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    ownercolumn.setCellValueFactory(new PropertyValueFactory<>("ownerUsername"));
    formatcolumn.setCellValueFactory(new PropertyValueFactory<>("format"));
    statuscolumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    // Table data
    bookTable.setItems(viewModel.getBooks());
    viewModel.loadBooks(); // this triggers the GET_ALL_BOOKS request

    // Disable "View Book" button if nothing is selected
    viewbook.setDisable(true);
    bookTable.getSelectionModel().selectedItemProperty().addListener(
        (ObservableValue<? extends BookSummaryDTO> obs, BookSummaryDTO oldVal, BookSummaryDTO newVal) -> {
          viewbook.setDisable(newVal == null);
        }
    );

    // Optional: Setup ChoiceBoxes if data exists in the ViewModel
    // genreChoice.setItems(viewModel.getGenres());
    // typeChoice.setItems(viewModel.getFormats());
    // ownerChoice.setItems(viewModel.getOwnerUsernames());
    // borrowedChoice.setItems(viewModel.getBorrowerUsernames());
  }

  @FXML
  private void onSearchClicked() {
    String query = searchField.getText();
    //viewModel.performSearch(query); // You can implement this later
  }

  @FXML
  private void onBackClicked() {
    viewHandler.openView("Client/view/HomeView.fxml");
  }

  @FXML
  private void onViewBookClicked() {
    BookSummaryDTO selected = bookTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      viewModel.setSelectedBook(selected);
      viewHandler.openBookInfoView(selected.getBookId());
      //viewHandler.openView("Client/view/BookInfoView.fxml");
    }
  }
}
