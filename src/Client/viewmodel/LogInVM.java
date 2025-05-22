package Client.viewmodel;

import javafx.scene.control.Label;
import Server.model.*;
import Server.repository.*;

import java.sql.SQLException;
import java.util.Optional;

public class LogInVM
{
  private final UserDAO userDAO;

  public LogInVM() throws SQLException{
    this.userDAO =  JdbcUserDAO.getInstance();
  }


  public void login(String username, String password, Label errorLabel)
  {
    try
    {
      Optional<User> opt = userDAO.findByUserName(username);
      if (opt.isPresent() && verifyPassword(password, opt.get().getPasswordHash()))
      {
        //TODO: open main window

        System.out.println("Login successful");
        errorLabel.setVisible(false);

      }
    }
    catch (SQLException e)
    {
      //TODO revisit this
      e.printStackTrace();
      errorLabel.setText("Error accessing database");
      errorLabel.setVisible(true);
    }

  }
  private boolean verifyPassword(String raw, String hashed) {
    // could be replaced with real hashing (e.g. BCrypt)
    return raw.equals(hashed);
  }
}
