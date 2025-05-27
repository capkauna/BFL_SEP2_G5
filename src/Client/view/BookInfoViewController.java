package Client.view;

import Client.viewmodel.BookInfoVM;
import Server.database.BookDAO;
import Shared.dto.BookSummaryDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.text.html.ImageView;
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

  @FXML
  private void onLendButtonClicked(ActionEvent actionEvent )
  {
    viewModel.lendBook(this.bookId);
  }

  @FXML
  private void onWaitingListView (ActionEvent actionEvent){
    viewHandler.openView("Client/view/WaitingListView.fxml");
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
  public void init(ViewHandler vh, BookInfoVM vm, int bookId)
      throws IOException, ClassNotFoundException
  {
    this.viewHandler = vh;
    this.viewModel          = vm;
this.bookId = bookId;
    // bind UI to VM properties
    titleLabel.textProperty().bind(vm.titleProperty());
    authorLabel.textProperty().bind(vm.authorProperty());
    isbnLabel.textProperty().bind(vm.isbnProperty());
    genreLabel.textProperty().bind(vm.genreProperty());
    format.textProperty().bind(vm.formatProperty());
    descriptionarea.textProperty().bind(vm.descriptionProperty());
    statusLabel.textProperty().bind(vm.statusProperty());
    ownerLabel.textProperty().bind(vm.ownerProperty());

    // load the image once the path is set
    vm.imagePathProperty().addListener((obs, oldP, newP) -> {
      if (newP != null && !newP.isBlank()) {
       // bookImage.setImage(new Image(newP));
        //handle later
      }
    });
    // finally, fetch from the server
    vm.loadBookInfo(bookId);
  }

  @FXML private void onBackClicked() {
    viewHandler.openView("Client/view/SearchView.fxml");
  }

  @FXML
  private void onBack() {
    viewHandler.openView("Client/view/HomeView.fxml");
  }
}
