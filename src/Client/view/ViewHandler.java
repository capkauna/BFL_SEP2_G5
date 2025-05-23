package Client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage; //this was sus, something about postgres hmm
import Client.viewmodel.ViewModelFactory;
import Client.view.HomeViewController;
import Client.viewmodel.HomeVM;
import view.MyLibraryViewController;
import Client.view.SearchViewController;


import java.io.IOException;

public class ViewHandler
{
  //viewmodel connects to controller, just note to myself cause i am lost

  private Stage primaryStage;
  private final ViewModelFactory viewModelFactory;

  public ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewModelFactory = viewModelFactory;
  }

  public void start(Stage stage)
  {
    this.primaryStage = stage;
    openView("HomeView.fxml");
  }

  public void openView(String fxmlFile)
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      Parent root = loader.load();

      Object controller = loader.getController();

      if (controller instanceof HomeViewController homeController)
      {
        homeController.init(viewModelFactory.getHomeVM());
      }
      else if (controller instanceof SearchViewController searchViewController)
      {
        searchViewController.init(viewModelFactory.getSearchVM());
      }
      else if (controller instanceof MyLibraryViewController libController)
      {
        libController.init(viewModelFactory.getMyLibraryVM());
      }
      else if (controller instanceof SearchViewController searchViewController)
      {
        searchViewController.init(viewModelFactory.getSearchVM());
      }
      else if (controller instanceof MyLibraryViewController libController)
      {
        libController.init(viewModelFactory.getMyLibraryVM());
        // to be continued for next pages
      }

      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}

