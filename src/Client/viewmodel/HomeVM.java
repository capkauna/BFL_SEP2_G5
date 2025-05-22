package viewmodel;

import view.ViewHandler;

public class HomeVM
{
  private final ViewHandler viewHandler;

  public HomeVM(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;
  }

  public void openLibraryView() {
    viewHandler.openView("MyLibraryView.fxml");
  }
  public void openUserProfileView() {
    viewHandler.openView("UserProfileView.fxml");
  }

  public void openSearchView() {
    viewHandler.openView("SearchView.fxml");
  }

  public void openUserListView() {
    viewHandler.openView("UserListView.fxml");
  }
}
