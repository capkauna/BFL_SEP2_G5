package Client;
import Client.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Client.view.*;
import Client.viewmodel.LogInVM;

import java.sql.SQLException;

public class ClientMain extends Application
{

  public void start(Stage primaryStage) throws SQLException
  {
    ViewModelFactory vmFactory = new ViewModelFactory();
    ViewHandler viewHandler = new ViewHandler(vmFactory);
    viewHandler.start(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}


//  @Override
//  public void start(Stage primaryStage) throws Exception {
//    // load FXML
//    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/view/LoginView.fxml"));
//    Parent root = loader.load();
//    // get controller and inject VM
//    LoginController controller = loader.getController();
//    controller.init(new LogInVM());
//
//    primaryStage.setTitle("User Login");
//    primaryStage.setScene(new Scene(root));
//    primaryStage.show();
//  }
//
//  public static void main(String[] args) {
//    launch(args);
//  }
//  }


