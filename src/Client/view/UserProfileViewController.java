package Client.view;

import Client.viewmodel.UserProfileVM;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserProfileViewController
{

  @FXML private ImageView userAvatar;

  @FXML private Label UserProfile;

  @FXML private TextField nameField;
  @FXML private TextField phoneField;
  @FXML private TextField emailField;
  @FXML private TextField addressField;
  @FXML private TextField moreInfoField;

  @FXML private Button readListButton;
  @FXML private Button myLibraryButton;
  @FXML private Button lentBooksButton;
  @FXML private Button borrowedBooksButton;
  @FXML private Button back;
  @FXML private Button settingsButton;
  @FXML private Button edit;

  private UserProfileVM viewModel;

  public void init(UserProfileVM viewModel) {
    this.viewModel = viewModel;

    nameField.textProperty().bind(viewModel.nameProperty());
    phoneField.textProperty().bind(viewModel.phoneProperty());
    emailField.textProperty().bind(viewModel.emailProperty());
    addressField.textProperty().bind(viewModel.addressProperty());
    //moreInfoField.textProperty().bind(viewModel.moreInfoProperty());

    if (viewModel.getAvatar() != null && !viewModel.getAvatar().isEmpty()) {
      userAvatar.setImage(new Image(viewModel.getAvatar()));
    }
  }

  @FXML
  public void onReadList() {
    System.out.println("Read list clicked");
  }

  @FXML
  public void onMyLibrary() {
    System.out.println("My Library clicked");
  }

  @FXML
  public void onLentBooks() {
    System.out.println("Lent Books clicked");
  }

  @FXML
  public void onBorrowedBooks() {
    System.out.println("Borrowed Books clicked");
  }

  @FXML
  public void onBack() {
    System.out.println("Back button clicked");
  }

  @FXML
  public void onEditUser() {
    System.out.println("Settings/Edit User button clicked");
  }

}

