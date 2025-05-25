package Client;

import javafx.application.Application;
import javafx.stage.Stage;
import Client.view.ViewHandler;
import Client.viewmodel.ViewModelFactory;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    ViewModelFactory viewModelFactory = new ViewModelFactory();
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);
    viewModelFactory.getLogInVM().init(viewHandler);
  }

  public static void main(String[] args) {
    launch(args);
  }
}