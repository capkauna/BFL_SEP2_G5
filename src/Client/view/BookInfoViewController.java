package Client.view;

import Client.viewmodel.BookInfoVM;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.text.html.ImageView;

public class BookInfoViewController
{

  @FXML private Label titleLabel;
  @FXML private Label authorLabel;
  @FXML private Label yearLabel;
  @FXML private Label genreLabel;
  @FXML private Label isbnLabel;
  @FXML private Label languageLabel;
  @FXML private Label ownerLabel;
  @FXML private Label borrowedLabel;

  @FXML private Button waitinglist;
  @FXML private Button history;
  @FXML private Button lendbutton;
  @FXML private Button getpdfbutton;
  @FXML private Button addnote;
  @FXML private Button back;
  @FXML private Button edit;//добавить в fxml
  @FXML private Button returnButton;
  @FXML private  ImageView bookImage;
  @FXML private TextArea booknotes;

  @FXML private CheckBox unavailableCheckBox;
  @FXML private CheckBox readCheckBox;

  private ViewHandler viewHandler;
  private BookInfoVM viewModel;

//TODO: add the book object and the label setting methods (getters)
  public void setBook() {
    titleLabel.setText("Book Title: Example Book Title");
    authorLabel.setText("Author: Example Author");
    yearLabel.setText("Year: 2023");
    genreLabel.setText("Genre: Fiction");
    isbnLabel.setText("ISBN: 123-4567890123");
    languageLabel.setText("Language: English");
    ownerLabel.setText("Owner: John Doe");
    borrowedLabel.setText("Borrowed By: Jane Smith");
    // Set the image for the book (assuming you have an image file)
    waitinglist.setText("Waiting List");
    history.setText("History");
    lendbutton.setText("Lend Book");
    getpdfbutton.setText("Get PDF");
    addnote.setText("Add Note");
    back.setText("Back");
    edit.setText("Edit Book");
    returnButton.setText("Return Book");

    booknotes.setText("This is an example note for the book.");

    unavailableCheckBox.setText("Unavailable");
    readCheckBox.setText("Read");
  }
  /*// Bind labels to VM properties
        titleLabel.textProperty().bind(viewModel.titleProperty());
        authorLabel.textProperty().bind(viewModel.authorProperty());
        genreLabel.textProperty().bind(viewModel.genreProperty());
        isbnLabel.textProperty().bind(viewModel.isbnProperty());
        ownerLabel.textProperty().bind(viewModel.ownerProperty());
        borrowedLabel.textProperty().bind(viewModel.borrowedProperty());
        languageLabel.textProperty().bind(viewModel.languageProperty());
        yearLabel.textProperty().bind(viewModel.yearProperty());
        readerNotes.textProperty().bind(viewModel.notesProperty());

  // Load book image
        if (viewModel.getImagePath() != null && !viewModel.getImagePath().isEmpty()) {
  bookImage.setImage(new Image(viewModel.getImagePath()));
}

        readCheckBox.setSelected(viewModel.isRead());
        unavailableCheckBox.setSelected(viewModel.isUnavailable());
}*/



  public void onLendButtonClicked()
  {
    // Logic to lend the book
    System.out.println("Lending the book: " + titleLabel.getText());
    // You can add more logic here, such as updating the database or notifying the user
  }

  public void onGetPdfButtonClicked()
  {
    // Logic to get the PDF of the book
    System.out.println("Getting PDF for the book: " + titleLabel.getText());
    // You can add more logic here, such as opening a file dialog or downloading the PDF
  }
  public void onAddNoteButtonClicked()
  {
    // Logic to add a note to the book
    System.out.println("Adding note for the book: " + titleLabel.getText());
    // You can add more logic here, such as opening a dialog to enter the note
  }
  public void onBackButtonClicked()
  {
    // Logic to go back to the previous screen
    System.out.println("Going back to the previous screen.");
    // You can add more logic here, such as closing the current window or navigating to another view
  }
  public void onEditButtonClicked()
  {
    // Logic to edit the book details
    System.out.println("Editing the book: " + titleLabel.getText());
    // You can add more logic here, such as opening a dialog to edit the book details
  }

  /*@FXML private void onHistoryClicked() {
    viewModel.showHistory();
  }*/

  public void init(ViewHandler viewHandler, BookInfoVM bookInfoVM)
  {
  }

  @FXML
  private void onBack() {
    viewHandler.openView("Client/view/HomeView.fxml");
  }
}
