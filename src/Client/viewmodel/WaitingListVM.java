package Client.viewmodel;

import Client.network.ClientSocketHandler;
import Shared.dto.UserDTO;
import Shared.dto.WaitingListEntryDTO;
import Shared.network.Request;
import Shared.network.Response;
import Shared.dto.enums.Action;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class WaitingListVM {

  private final ObservableList<WaitingListEntryDTO> entries = FXCollections.observableArrayList();
  public ObservableList<WaitingListEntryDTO> getEntries() { return entries; }

  public void removeMeFromWaitingList(WaitingListEntryDTO entry) {
    // trimite request la server pentru remove
    Request req = new Request(Action.REMOVE_FROM_WAITING_LIST, entry);
    new Thread(() -> {
      try {
        ClientSocketHandler.getInstance().sendRequest(req);
        Platform.runLater(() -> entries.remove(entry));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }

  public void lendBookTo(WaitingListEntryDTO entry) {
    Request req = new Request(Action.LEND_BOOK_TO_USER, entry);
    new Thread(() -> {
      try {
        ClientSocketHandler.getInstance().sendRequest(req);
        Platform.runLater(() -> entries.remove(entry));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }
