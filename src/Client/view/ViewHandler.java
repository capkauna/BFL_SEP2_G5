package Client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Client.viewmodel.*;

import java.io.IOException;

public class ViewHandler
{
  private Stage primaryStage;
  private final ViewModelFactory viewModelFactory;

  public ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewModelFactory = viewModelFactory;
  }

  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    openView("Client/view/LoginView.fxml");
  }

  public void openView(String fxmlFile)
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(
          getClass().getClassLoader().getResource(fxmlFile));
      Parent root = loader.load();

      Object controller = loader.getController();
      switch (controller)
      {
        case LoginController loginController ->
            loginController.init(this, viewModelFactory.getLogInVM());
        case HomeViewController homeController ->
            homeController.init(this, viewModelFactory.getHomeVM());
        case SearchViewController searchController ->
            searchController.init(this, viewModelFactory.getSearchVM());
        case MyLibraryViewController libController ->
            libController.init(this, viewModelFactory.getMyLibraryVM());
        case UserProfileViewController userPage ->
            userPage.init(this, viewModelFactory.getUserProfileVM());
        case UserListViewController userList ->
            userList.init(this, viewModelFactory.getUserListVM());
        case EditBookViewController userPage ->
            userPage.init(this, viewModelFactory.getEditBookVM());
        case BookInfoViewController bookInfoController ->
            bookInfoController.init(this, viewModelFactory.getBookInfoVM());



        // …add more cases for UserPageViewController, UserListViewController, etc.
        default ->
        {
          // No-op or log unexpected controller
        }
      }

      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }
}

