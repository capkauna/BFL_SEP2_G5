import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    ViewModelFactory vmf = new ViewModelFactory();
    ViewHandler vh = new ViewHandler(vmf);
    vh.start(primaryStage);
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
