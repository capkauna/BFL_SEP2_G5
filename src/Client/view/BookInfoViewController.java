package Client.view;

import Client.viewmodel.BookInfoVM;
import Server.database.BookDAO;
import Shared.dto.BookSummaryDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import java.io.IOException;

public class BookInfoViewController
{

  @FXML private Label titleLabel, statusLabel,authorLabel,yearLabel,
      genreLabel,format, isbnLabel,languageLabel, ownerLabel,borrowedLabel;

  @FXML private Button waitinglist, history,lendbutton,getpdfbutton,addnote,
      back,edit,returnButton,onLendButtonClicked,onAddNoteButtonClicked,addtowait;
  @FXML private  ImageView bookImage;
  @FXML private TextArea booknotes, descriptionarea;

  @FXML private CheckBox unavailableCheckBox,readCheckBox;

  private ViewHandler viewHandler;
  private BookInfoVM viewModel;
  private int bookId;
  private String currentUser;

  @FXML
  private void onLendButtonClicked(ActionEvent actionEvent )
  {
    viewHandler.openWaitingListView(bookId, true);
    viewModel.lendBook(this.bookId);
  }

  @FXML
  private void onWaitingListView (ActionEvent actionEvent){
    System.out.println("Opening Waiting List View for book ID: " + bookId);
    viewHandler.openWaitingListView(bookId, false);
  }
  @FXML private void onAddtoWaitingListClicked(ActionEvent e)
{
  viewModel.addwaitingList(this.bookId);
}

  @FXML
  private void onGetPdfButtonClicked(ActionEvent actionEvent)
  {
    // Logic to get the PDF of the book
    System.out.println("Getting PDF for the book: " + titleLabel.getText());
    // You can add more logic here, such as opening a file dialog or downloading the PDF
  }

  @FXML
  private void onAddNoteButtonClicked(ActionEvent actionEvent)
  {
    // Logic to add a note to the book
    System.out.println("Adding note for the book: " + titleLabel.getText());
    // You can add more logic here, such as opening a dialog to enter the note
  }

  public void onEditButtonClicked()
  {
    // Logic to edit the book details
    System.out.println("Not implemented - Editing the book: " + titleLabel.getText());
    //viewHandler.openEditBookView(bookId);
    // You can add more logic here, such as opening a dialog to edit the book details
  }

  @FXML private void onHistoryClicked() {
    System.out.println("Not implemented - Opening History View for book ID: " + bookId);
    //viewHandler.openView("Client/view/HistoryView.fxml");
  }
  public void init(ViewHandler vh, BookInfoVM vm, int bookId)
      throws IOException, ClassNotFoundException
  {
    this.viewHandler = vh;
    this.viewModel          = vm;
    this.bookId = bookId;
    this.currentUser = vm.getCurrentUser();
    // bind UI to VM properties
    titleLabel.textProperty().bind(vm.titleProperty());
    authorLabel.textProperty().bind(vm.authorProperty());
    isbnLabel.textProperty().bind(vm.isbnProperty());
    genreLabel.textProperty().bind(vm.genreProperty());
    format.textProperty().bind(vm.formatProperty());
    descriptionarea.textProperty().bind(vm.descriptionProperty());
    statusLabel.textProperty().bind(vm.statusProperty());
    ownerLabel.textProperty().bind(vm.ownerProperty());


    // Listen for owner property being populated
    vm.ownerProperty().addListener((obs, oldOwner, newOwner) -> {
          if (newOwner != null && !newOwner.isBlank())
          {
            System.out.println("Book owner loaded: " + newOwner);
            System.out.println("Current user: " + currentUser);

            boolean isOwner = newOwner.equals(currentUser);

            edit.setVisible(isOwner);
            lendbutton.setDisable(!isOwner);
            lendbutton.setOpacity(isOwner ? 1.0 : 0.5);
          }
        });


    // load the image once the path is set
    vm.imagePathProperty().addListener((obs, oldP, newP) -> {
      if (newP != null && !newP.isBlank()) {
       // bookImage.setImage(new Image(newP));
        //handle later
      }

    });
    // finally, fetch from the server
      try
      {
        vm.loadBookInfo(bookId);
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }

    }

  @FXML private void onBackClicked() {
    viewHandler.openView("Client/view/SearchView.fxml");
  }

  @FXML
  private void onBack() {
    viewHandler.openView("Client/view/HomeView.fxml");
  }
}
