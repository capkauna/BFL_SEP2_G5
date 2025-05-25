package Client.view;

import Client.viewmodel.BookInfoVM;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BookListViewController
{
  //many books list


  @FXML private ImageView bookImage;
  @FXML private Label titleLabel;
  @FXML private Label authorLabel;
  @FXML private Label genreLabel;
  @FXML private Label isbnLabel;
  @FXML private Label ownerLabel;
  @FXML private Label borrowedLabel;
  @FXML private Label languageLabel;
  @FXML private Label yearLabel;

  @FXML private CheckBox unavailableCheckBox;
  @FXML private CheckBox readCheckBox;
  @FXML private TextArea readerNotes;
  @FXML private GridPane List;

  private ViewHandler viewHandler;
  private BookInfoVM viewModel;

  public void init(ViewHandler viewHandler, BookInfoVM viewModel) {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;

    // Привязка — позже можно улучшить с JavaFX Binding
    titleLabel.setText(viewModel.getTitle());
    authorLabel.setText(viewModel.getAuthor());
    genreLabel.setText(viewModel.getGenre());
    isbnLabel.setText(viewModel.getIsbn());
    ownerLabel.setText(viewModel.getOwner());
    borrowedLabel.setText(viewModel.getBorrowedBy());
    languageLabel.setText(viewModel.getLanguage());
    yearLabel.setText(viewModel.getYear());
    readerNotes.setText(viewModel.getReaderNotes());
  }

  @FXML private void onBackClicked() {
    viewHandler.openView("MyLibraryView.fxml");
  }
}
