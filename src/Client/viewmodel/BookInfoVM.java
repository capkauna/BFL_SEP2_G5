// Client/viewmodel/BookInfoVM.java
package Client.viewmodel;

import Shared.network.Request;
import Shared.network.Response;
import Shared.dto.enums.Action;
import Shared.dto.BookSummaryDTO;
import Client.network.ClientSocketHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

public class BookInfoVM {
  private String currentUser;
  private final ClientSocketHandler socket = new ClientSocketHandler();

  private final StringProperty title       = new SimpleStringProperty();
  private final StringProperty author      = new SimpleStringProperty();
  private final StringProperty isbn        = new SimpleStringProperty();
  private final StringProperty genre       = new SimpleStringProperty();
  private final StringProperty format      = new SimpleStringProperty();
  private final StringProperty status = new SimpleStringProperty();
  private final StringProperty description = new SimpleStringProperty();
  private final StringProperty imagePath   = new SimpleStringProperty();

  // expose for binding in the controller
  public StringProperty titleProperty()       { return title; }
  public StringProperty authorProperty()      { return author; }
  public StringProperty isbnProperty()        { return isbn; }
  public StringProperty genreProperty()       { return genre; }
  public StringProperty formatProperty()      { return format; }
  public StringProperty statusProperty()      { return status; }
  public StringProperty descriptionProperty(){ return description; }
  public StringProperty imagePathProperty()   { return imagePath; }

  public BookInfoVM(String currentUser)
  {
    this.currentUser = currentUser;
    //this would be more relevant if we had time to separate owner to other differences
  }
  /**
   * Fetches the BookSummary from the server and populates all properties. 
   */
  public void loadBookInfo(int bookId)
      throws IOException, ClassNotFoundException
  {
    socket.connect("localhost", 1234);
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

}
