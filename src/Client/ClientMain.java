package Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Client.view.*;
import Client.viewmodel.LogInVM;

public class ClientMain extends Application
{
  @Override
  public void start(Stage primaryStage) throws Exception {
    // load FXML
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
    Parent root = loader.load();
    // get controller and inject VM
    LoginController controller = loader.getController();
    controller.init(new LogInVM());

    primaryStage.setTitle("User Login");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
