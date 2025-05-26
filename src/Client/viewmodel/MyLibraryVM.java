package Client.viewmodel;


import Client.network.ClientSocketHandler;
import Client.state.SessionState;
import Shared.dto.BookSummaryDTO;
import Shared.dto.FullUserDTO;
import Shared.dto.enums.Action;
import Shared.network.Request;
import Shared.network.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyLibraryVM
{

  //private final AuthServiceClient authClient;
  private final ClientSocketHandler socketHandler;
  private final String currentUserName;
  private final ObservableList<String> books;
  private SessionState sessionState = SessionState.getInstance();

  public MyLibraryVM(ClientSocketHandler socketHandler,String username) {
    //this.authClient = authClient;
    this.socketHandler = socketHandler;
    this.currentUserName = username;
    this.books = FXCollections.observableArrayList();
  }

  public ObservableList<String> getMyBooks() {
    System.out.println("->Sending GET_ALL_BOOKS request to server");
    try {
      FullUserDTO loggedUser = sessionState.getLoggedUser();
      Request req = new Request(Action.GET_MY_BOOKS, loggedUser.getUserId());
      socketHandler.sendRequest(req);
      Response resp = socketHandler.readResponse();
      System.out.println(" <- Got response: " + resp.isSuccess());

      if (resp.isSuccess()) {
        ArrayList<BookSummaryDTO> bookSummaryList = (ArrayList<BookSummaryDTO>) resp.getData();
        System.out.println("Loaded books: " + bookSummaryList.size());
        List<String> bookTitles = bookSummaryList.stream()
            .map(bookSummaryDTO -> bookSummaryDTO.getTitle())
            .collect(Collectors.toList());
        books.setAll(bookTitles);
      } else {
        System.err.println("Failed to load books: " + resp.getErrorMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error loading books");
    }

    return books;
  }

  public void removeSelectedBook(String book) {
    books.remove(book);
  }

}

