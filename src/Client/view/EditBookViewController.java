package Client.view;

import Client.viewmodel.EditBookVM;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditBookViewController
{
  @FXML private TextField titleField;
  @FXML private TextField authorField;
  @FXML private TextField isbnField;
  @FXML private TextField genreField;
  @FXML private TextField formatField;
  @FXML private TextField descriptionArea;
  @FXML private TextField imagePathField;
  @FXML private TextField errorLabel;

  private EditBookVM viewModel;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, EditBookVM viewModel) {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;

    // Bind properties to the view model
    titleField.textProperty().bindBidirectional(viewModel.titleProperty());
    authorField.textProperty().bindBidirectional(viewModel.authorProperty());
    isbnField.textProperty().bindBidirectional(viewModel.isbnProperty());
    genreField.textProperty().bindBidirectional(viewModel.genreProperty());
    formatField.textProperty().bindBidirectional(viewModel.formatProperty());
    descriptionArea.textProperty().bindBidirectional(viewModel.descriptionProperty());
    imagePathField.textProperty().bindBidirectional(viewModel.imagePathProperty());
    errorLabel.textProperty().bind(viewModel.errorMessageProperty());
  }

  @FXML private void onSaveClicked() {
    if (viewModel.validateAndSave()) {
      //goes to MyLibarryView after saving
      viewHandler.openView("Client/view/MyLibraryView.fxml");
    } else {
      errorLabel.setText("Please fill in all fields.");
      errorLabel.setVisible(true);
    }
    }

    @FXML private void onCancelClicked() {
      viewHandler.openView("Client/view/MyLibraryView.fxml");
    }
}
