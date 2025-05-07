package View;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;

import java.awt.*;

public class LoginController
{
  private TextField usernameField;
  private PasswordField passwordField;
  private Button loginButton;
  private Button cancelButton;

  public void onLoginClicked(ActionEvent actionEvent)
  {
    String username = usernameField.getText();
    String password = passwordField.getText();

    if (username.isEmpty() || password.isEmpty())
    {
      // Show error message
      System.out.println("Please enter both username and password.");
      return;
    }
    //TODO: Validate username and password

    // Perform login logic here
    System.out.println("Logging in with username: " + username + " and password: " + password);
  }

  public void onCancelClicked(ActionEvent actionEvent)
  {
    // Close the application or go back to the previous screen
    System.out.println("Cancel button clicked. Closing application.");
    // You can use Platform.exit() to close the application
    // Platform.exit();
  }
}
