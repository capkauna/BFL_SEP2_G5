package Client.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserListVM {
  private final ObservableList<UserSummaryVM> users = FXCollections.observableArrayList();

  public UserListVM() {
    // Dummy data
    users.add(new UserSummaryVM("Alice", 5, 12));
    users.add(new UserSummaryVM("Bob", 2, 8));
  }

  public ObservableList<UserSummaryVM> getUsers() {
    return users;
  }
}
