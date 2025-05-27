// Client/viewmodel/BookInfoVM.java
package Client.viewmodel;

import Client.state.SessionState;
import Server.database.BookDAO;
import Server.database.JdbcBookDAO;
import Server.model.Book;
import Server.model.Lend;
import Server.model.User;
import Server.service.BookInfoService;
import Shared.dto.FullUserDTO;
import Shared.dto.WaitingListEntryDTO;
import Shared.network.Request;
import Shared.network.Response;
import Shared.dto.enums.Action;
import Shared.dto.BookSummaryDTO;
import Client.network.ClientSocketHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookInfoVM {
  private final ClientSocketHandler socketHandler;
  private String currentUser;
  private final ClientSocketHandler socket = new ClientSocketHandler();
  private SessionState sessionState = SessionState.getInstance();

  private final StringProperty title       = new SimpleStringProperty();
  private final StringProperty author      = new SimpleStringProperty();
  private final StringProperty isbn        = new SimpleStringProperty();
  private final StringProperty genre       = new SimpleStringProperty();
  private final StringProperty format      = new SimpleStringProperty();
  private final StringProperty status = new SimpleStringProperty();
  private final StringProperty description = new SimpleStringProperty();
  private final StringProperty imagePath   = new SimpleStringProperty();
  private final StringProperty owner = new SimpleStringProperty();

  // expose for binding in the controller
  public StringProperty titleProperty()       { return title; }
  public StringProperty authorProperty()      { return author; }
  public StringProperty isbnProperty()        { return isbn; }
  public StringProperty genreProperty()       { return genre; }
  public StringProperty formatProperty()      { return format; }
  public StringProperty statusProperty()      { return status; }
  public StringProperty descriptionProperty(){ return description; }
  public StringProperty imagePathProperty()   { return imagePath; }
  public StringProperty ownerProperty()       { return owner; }

  public BookInfoVM(ClientSocketHandler socketHandler, String currentUser)
  {
    this.currentUser = currentUser;
    this.socketHandler = socketHandler;
    //this would be more relevant if we had time to separate owner to other differences
  }
  /**
   * Fetches the BookSummary from the server and populates all properties. 
   */
  public void loadBookInfo(int bookId)
      throws IOException, ClassNotFoundException
  {
    socket.connect();
    socket.sendRequest(new Request(Action.GET_BOOK_INFO, bookId));
    Response resp = socket.readResponse();
    socket.close();

    if (!resp.isSuccess()) {
      throw new RuntimeException("Failed to load book: " + resp.getErrorMessage());
    }

    BookSummaryDTO b = (BookSummaryDTO) resp.getData();
    title.set(b.getTitle());
    author.set(b.getAuthor());
    isbn.set(b.getIsbn());
    genre.set(b.getGenre().name());
    format.set(b.getFormat().name());
    status.set(b.getStatus());
    description.set(b.getDescription());
    imagePath.set(b.getAvatar());
    owner.set(b.getOwnerName());
  }

  public String getTitle()
  {
    return title.get();
  }
  public String getAuthor()
  {
    return author.get();
  }
  public String getIsbn()
  {
    return isbn.get();
  }
  public String getGenre()
  {
    return genre.get();
  }
  public String getFormat()
  {
    return format.get();
  }
  public String getStatus()
  {
    return status.get();
  }
  public String getDescription()
  {
    return description.get();
  }
  public String getImagePath()
  {
    return imagePath.get();
  }
  public String getOwner()
  {
    return owner.get();
  }
  //      just to show it working, this shouldn't access the database directly
  //also bypasses safety checks, so it shouldn't be used
  public void lendBook(int bookId) {
    System.out.println("->Sending LEND_BOOK request to server");
    try {
      FullUserDTO loggedUser = sessionState.getLoggedUser();

      BookInfoService books = new BookInfoService();
      Book b = books.getBookInfo(bookId);
      User u = new User(loggedUser.getUserId(), loggedUser.getUserName());
      Lend lendattempt = Lend.lendBook(b, u);
      if (lendattempt != null)
      {
        Request req = new Request(Action.LEND_BOOK, lendattempt);
        socketHandler.sendRequest(req);
        Response resp = socketHandler.readResponse();
        System.out.println(" <- Got response: " + resp.isSuccess());

        if (resp.isSuccess()) {
          System.out.println("Lended successfully");
        } else {
          System.err.println("Failed to lend book: " + resp.getErrorMessage());
        }
      } else {
        System.err.println("Lending attempt failed, book might not be available.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error loading books");
    }
  }

  public void addwaitingList(int bookId)
  {
    try {
    FullUserDTO loggedUser = sessionState.getLoggedUser();
          WaitingListEntryDTO entry = new WaitingListEntryDTO(bookId,loggedUser.getUserName(),
              LocalDateTime.now());

          Request req = new Request(Action.ADD_TO_WAITING_LIST, entry);
          socketHandler.sendRequest(req);
          Response resp = socketHandler.readResponse();
          System.out.println(" <- Got response: " + resp.isSuccess());


      if (resp.isSuccess()) {
        System.out.println("Landed successfully");
      } else {
        System.err.println("Failed to load books: " + resp.getErrorMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error loading books");
    }

  }
}
