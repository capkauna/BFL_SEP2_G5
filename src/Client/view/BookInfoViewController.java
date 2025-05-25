package Client.view;

import Client.viewmodel.BookInfoVM;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BookInfoViewController
{
  //details about one bookдав
  @FXML private Label titlelabel;
  @FXML private Label authorlabel;
  @FXML private Label yearlabel;
  @FXML private Label genrelabel;

  @FXML private Button lendbutton;
  @FXML private Button getpdfbutton;
  @FXML private Button addnote;
  @FXML private Button back;
  @FXML private Button edit;

  @FXML private TextArea booknotes;

//TODO: add the book object and the label setting methods (getters)
  public void setBook() {
    titlelabel.setText("Book Title: Example Book Title");
    authorlabel.setText("Author: Example Author");
    yearlabel.setText("Year: 2023");
    genrelabel.setText("Genre: Fiction");


    booknotes.setText("This is an example note for the book.");



  }

  public void onLendButtonClicked()
  {
    // Logic to lend the book
    System.out.println("Lending the book: " + titlelabel.getText());
    // You can add more logic here, such as updating the database or notifying the user
  }

  public void onGetPdfButtonClicked()
  {
    // Logic to get the PDF of the book
    System.out.println("Getting PDF for the book: " + titlelabel.getText());
    // You can add more logic here, such as opening a file dialog or downloading the PDF
  }
  public void onAddNoteButtonClicked()
  {
    // Logic to add a note to the book
    System.out.println("Adding note for the book: " + titlelabel.getText());
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
    System.out.println("Editing the book: " + titlelabel.getText());
    // You can add more logic here, such as opening a dialog to edit the book details
  }

  public void init(ViewHandler viewHandler, BookInfoVM bookInfoVM)
  {
  }
}
