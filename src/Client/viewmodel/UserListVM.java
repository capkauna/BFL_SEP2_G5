package Client.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserListVM {
  private final ObservableList<UserSummaryVM> users = FXCollections.observableArrayList();
//TODO: figure this one out
  public UserListVM() {
    // Dummy data
    users.add(new UserSummaryVM("Guest","Alice", 5, 12));
    users.add(new UserSummaryVM("Guest","Bob", 2, 8));
  }

  public ObservableList<UserSummaryVM> getUsers() {
    return users;
  }
}
