package Client;

import javafx.application.Application;
import javafx.stage.Stage;
import Client.view.ViewHandler;
import Client.viewmodel.ViewModelFactory;

import java.sql.SQLException;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws SQLException
  {
    ViewModelFactory vmf = new ViewModelFactory();
    ViewHandler vh = new ViewHandler(vmf);
    vh.start(primaryStage);
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
