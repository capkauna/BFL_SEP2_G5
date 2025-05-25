package Client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Client.viewmodel.*;

import java.io.IOException;

public class ViewHandler {
  private Stage primaryStage;
  private final ViewModelFactory viewModelFactory;

  public ViewHandler(ViewModelFactory viewModelFactory) {
    this.viewModelFactory = viewModelFactory;
  }

  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    openView("Client/view/HomeView.fxml");
  }

  public void openView(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile));
      Parent root = loader.load();

      Object controller = loader.getController();
      if (controller instanceof HomeViewController homeController) {
        homeController.init(this, viewModelFactory.getHomeVM());
      } else if (controller instanceof SearchViewController searchController) {
        searchController.init(this, viewModelFactory.getSearchVM());
      } else if (controller instanceof MyLibraryViewController libController) {
        libController.init(this, viewModelFactory.getMyLibraryVM());
      }

      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

