package Client.viewmodel;

import Client.network.ClientSocketHandler;
import Shared.dto.FullUserDTO;
import Shared.dto.WaitingListEntryDTO;
import Shared.dto.enums.Action;
import Shared.network.Request;
import Shared.network.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class WaitingListVM
{
  private final ClientSocketHandler socket = new ClientSocketHandler();
  private String username;
  private int bookId;
  private ObservableList<WaitingListEntryDTO> waitingUsers = FXCollections.observableArrayList();


  public WaitingListVM(String username)
  {
    this.username = username;
  }


  public void setBookId(int bookId)
  {
    this.bookId = bookId;
  }
  public ObservableList<WaitingListEntryDTO> getWaitingUsers() {
    return waitingUsers;
  }


  public void loadListFromServer()
  {
    try {
      socket.connect();
      socket.sendRequest(new Request(Action.GET_WAITING_LIST, bookId));
      Response resp = socket.readResponse();
      socket.close();

      if (!resp.isSuccess()) {
        System.out.println("Error fetching waiting list: " + resp.getErrorMessage());
        throw new RuntimeException(resp.getErrorMessage());
      }
      // we sent back an ArrayList<FullUserDTO>
      ArrayList<WaitingListEntryDTO> result = (ArrayList<WaitingListEntryDTO>) resp.getData();
      waitingUsers.setAll(result);
      System.out.println("Waiting list loaded: " + result.size() + " entries");
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void addToWaitingList() {
    try {
      socket.connect();
      socket.sendRequest(new Request(Action.ADD_TO_WAITING_LIST,
          new WaitingListEntryDTO(bookId, username, LocalDateTime.now())));
      Response resp = socket.readResponse();
      socket.close();

      if (!resp.isSuccess()) {
        throw new RuntimeException("Add failed: " + resp.getErrorMessage());
      }

      System.out.println("Successfully added to waiting list. Reloading list...");
      loadListFromServer();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

}

