package Client.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Client.viewmodel.LogInVM;


public class LoginController
{
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private Button loginButton;
  @FXML private Button cancelButton;
  @FXML private Label errorLabel;

  private ViewHandler viewHandler;
  private LogInVM viewModel;

  public void init(ViewHandler vh, LogInVM vm){
    this.viewHandler = vh;
    this.viewModel = vm;
    errorLabel.setVisible(false); // Initially hide the error label
  }

  @FXML private void onLoginClicked(ActionEvent event) {
    String user = usernameField.getText();
    String pass = passwordField.getText();
    try {
      if (viewModel.login(user, pass)) {
        // successful → hand off to ViewHandler to open the home view
        viewHandler.openView("/Client/view/HomeView.fxml");
      } else {
        errorLabel.setText("Invalid username or password.");
        errorLabel.setVisible(true);
      }
    } catch (Exception e) {
      errorLabel.setText("Error connecting to auth server.");
      errorLabel.setVisible(true);
      e.printStackTrace();
    }
  }

@FXML
public void onCancelClicked(ActionEvent actionEvent)
{
  // Close the application or go back to the previous screen
  System.out.println("Cancel button clicked. Closing application.");
  System.exit(0); // This will terminate the Java application
  // You can use Platform.exit() to close the application
  // Platform.exit();
}
}
//  @FXML

//  public void onLoginClicked(ActionEvent actionEvent) throws Exception
//  {
//    String username = usernameField.getText();
//    String password = passwordField.getText();
//
//    if (username.isEmpty() || password.isEmpty())
//    {
//      // Show error message
//      errorLabel.setText("Please enter both username and password.");
//      System.out.println("Please enter both username and password.");
//      errorLabel.setVisible(true);
//      return;
//    }


//    // Perform login logic here:
//    viewModel.login(username, password);
//
//    System.out.println("Logging in with username: " + username + " and password: " + password);
//  }


//}
