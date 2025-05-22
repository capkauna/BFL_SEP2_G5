package view;

import javafx.fxml.FXML;
import viewmodel.HomeVM;

public class HomeViewController
{
  private HomeVM viewModel;

  public void init(HomeVM vm) {
    this.viewModel = vm;
  }

  @FXML private void onMyLibrary() {
    viewModel.openLibraryView();
  }

  @FXML private void onMyAccount() {
    viewModel.openUserProfileView();
  }

  @FXML private void onFindBook(){
    viewModel.openSearchView();
  }

  @FXML private void onFindUser(){
    viewModel.openUserListView();
  }
}
