package Client.viewmodel;

import Client.network.ClientSocketHandler;
import Shared.dto.FullUserDTO;
import Shared.dto.enums.Action;
import Shared.network.Request;
import Shared.network.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class UserListVM {
  private final ClientSocketHandler socketHandler;
  private final ObservableList<FullUserDTO> users = FXCollections.observableArrayList();
//TODO: figure this one out
  public UserListVM(ClientSocketHandler handler) {
    this.socketHandler = handler;
    // Dummy data
//    users.add(new UserSummaryVM("Guest","Alice", 5, 12));
//    users.add(new UserSummaryVM("Guest","Bob", 2, 8));
  }

  public ObservableList<FullUserDTO> getUsers()
  {
    System.out.println("->Sending GET_ALL_USERS request to server");
    try
    {
      Request request = new Request(Action.GET_ALL_USERS, null);
      socketHandler.sendRequest(request);
      Response resp = socketHandler.readResponse();
      System.out.println(" <- Got response: " + resp.isSuccess());

      if (resp.isSuccess())
      {
        ArrayList<FullUserDTO> userList = (ArrayList<FullUserDTO>) resp.getData();
        System.out.println("Loaded users: " + userList.size());
        users.setAll(userList);
      }
      else
      {
        System.err.println("Failed to load users: " + resp.getErrorMessage());
      }
    }

    catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error loading users");
    }
    return users;
  }
}
