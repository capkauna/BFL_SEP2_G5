package Client.viewmodel;

import Client.network.ClientSocketHandler;
import Shared.dto.WaitingListEntryDTO;
import Shared.dto.enums.Action;
import Shared.network.Request;
import Shared.network.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WaitingListVM
{
  private final ClientSocketHandler socket = new ClientSocketHandler();
  private String username;
  private int bookId;
//TODO: make sure it gets the bookId from the book details page
  public WaitingListVM(String username)
  {
    this.username = username;
    this.bookId = bookId;
  }
  public void setBookId(int bookId)
  {
    this.bookId = bookId;
  }


  public WaitingListEntryDTO addToWaitingList(int bookId)
      throws IOException, ClassNotFoundException
  {
    socket.connect("localhost", 1234);
    socket.sendRequest(
        new Request(Action.ADD_TO_WAITING_LIST,
            new WaitingListEntryDTO(bookId, username, LocalDateTime.now()))
    );
    Response resp = socket.readResponse();
    socket.close();

    if (!resp.isSuccess()) {
      throw new RuntimeException(resp.getErrorMessage());
    }
    // we sent back a domain WaitingListEntry—cast it if you have that on the client
    return (WaitingListEntryDTO) resp.getData();
  }
}

