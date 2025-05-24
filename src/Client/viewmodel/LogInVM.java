package Client.viewmodel;

import Client.network.AuthServiceClient;
import Client.network.SocketAuthServiceClient;
import javafx.scene.control.Label;


public class LogInVM {
  private final AuthServiceClient authClient;

  public LogInVM() {
    // pick your implementation here:
    this.authClient = new SocketAuthServiceClient();
  }

  /**
   * Attempts login via the AuthService over sockets.
   * Shows/hides the errorLabel based on outcome.
   */
  public void login(String username, String password, Label errorLabel) {
    try {
      boolean ok = authClient.login(username, password);
      if (ok) {
        System.out.println("Login successful for user: " + username);
        errorLabel.setVisible(false);
        // TODO: launch main window
      } else {
        System.out.println("Invalid credentials for user: " + username);
        errorLabel.setText("Invalid username or password.");
        errorLabel.setVisible(true);
      }
    } catch (Exception e) {
      e.printStackTrace();
      errorLabel.setText("Error connecting to auth server.");
      errorLabel.setVisible(true);
    }
  }
}

//for direct testing if login works
/*

import Server.model.*;
import Server.repository.*;

import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Label;

public class LogInVM
{
  private final UserDAO userDAO;

  public LogInVM() throws SQLException{
    this.userDAO =  JdbcUserDAO.getInstance();
  }


  public void login(String username, String password, Label errorLabel) {
    try {
      Optional<User> opt = userDAO.findByUserName(username);
      if (opt.isPresent()) {
        User user = opt.get();
        // use the model’s built-in check, not raw.equals(hashed)
        if (user.validatePassword(password)) {
          System.out.println("Login successful for user: " + username);
          errorLabel.setVisible(false);
          // TODO: launch main window
        } else {
          System.out.println("Invalid password for user: " + username);
          errorLabel.setText("Invalid username or password.");
          errorLabel.setVisible(true);
        }
      } else {
        System.out.println("No such user: " + username);
        errorLabel.setText("Invalid username or password.");
        errorLabel.setVisible(true);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      errorLabel.setText("Error accessing database.");
      errorLabel.setVisible(true);
    }
  }

  // no changes to validatePassword in VM; delegate to User.validatePassword(...)
  private boolean verifyPassword(String raw, String hashed) {
    return raw.equals(hashed);
  }

}

 */
