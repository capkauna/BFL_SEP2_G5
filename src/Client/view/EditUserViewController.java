package Client.view;

import Client.viewmodel.EditUserVM;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class EditUserViewController {

  @FXML private TextField usernameField;
  @FXML private TextField emailField;
  @FXML private TextField nameField;
  @FXML private TextField dobField;
  @FXML private TextField phoneField;
  @FXML private TextField addressField;
  @FXML private TextArea moreInfoField;
  @FXML private TextField newPasswordField;
  @FXML private TextField currentPasswordField;
  @FXML private Button back;
@FXML private Label EditUser;
@FXML private Button addImage;
@FXML private Button Submit;



  private EditUserVM viewModel;
  private ViewHandler viewHandler;

  public void init(EditUserVM viewModel, ViewHandler viewHandler) {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;

    usernameField.textProperty().bindBidirectional(viewModel.usernameProperty());
    emailField.textProperty().bindBidirectional(viewModel.emailProperty());
    nameField.textProperty().bindBidirectional(viewModel.nameProperty());
    dobField.textProperty().bindBidirectional(viewModel.dobProperty());
    phoneField.textProperty().bindBidirectional(viewModel.phoneProperty());
    addressField.textProperty().bindBidirectional(viewModel.addressProperty());
    moreInfoField.textProperty().bindBidirectional(viewModel.moreInfoProperty());
    newPasswordField.textProperty().bindBidirectional(viewModel.newPasswordProperty());
    currentPasswordField.textProperty().bindBidirectional(viewModel.currentPasswordProperty());
  }

  @FXML
  private void onAddImage() {
    viewModel.addImage();
  }

  @FXML
  private void onSubmit() {
    viewModel.submitChanges();
  }

  @FXML
  private void onBack() {
    viewHandler.openView("Client/view/UserProfileView.fxml");
  }
}

