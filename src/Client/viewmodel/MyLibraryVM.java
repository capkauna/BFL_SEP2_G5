package Client.viewmodel;


import Client.network.ClientSocketHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MyLibraryVM
{

  //private final AuthServiceClient authClient;
  private final ClientSocketHandler socketHandler;
  private final String currentUserName;
  private final ObservableList<String> books;



  public MyLibraryVM(ClientSocketHandler socketHandler,String username) {
    //this.authClient = authClient;
    this.socketHandler = socketHandler;
    this.currentUserName = username;
    this.books = FXCollections.observableArrayList();

  }



  public ObservableList<String> getBooks(){
    return books;
  }

  public void removeSelectedBook(String book) {
    books.remove(book);
  }

  public ObservableList<String> getMyBooks()
  {
    return books;
  }
}
//  private final BookDAO dao;
//  private final ViewHandler viewHandler;
//  private Book selectedBook;
//
//  public MyLibraryVM(BookDAO dao, ViewHandler viewHandler) {
//    this.dao = dao;
//    this.viewHandler = viewHandler;
//  }
//
//  public ArrayList<String> getMyBooks() {
//    try {
//      return dao.findByOwner(Session.getLoggedInUser()).stream()
//          .map(Book::getTitle)
//          .collect(Collectors.toList());
//    } catch (SQLException e) {
//      return ArrayList.of("Error: " + e.getMessage());
//    }
//  }
//
//  public void selectedBookByTitle(String title) {
//    try {
//      selectedBook = dao.findByTitle(title).stream()
//          .filter(book -> book.getOwner().getUserId() == Session.getLoggedInUser().getUserId())
//          .findFirst().orElse(null);
//    } catch (SQLException e) {
//      selectedBook = null;
//    }
//  }
//
//  public void editSelectedBook() {
//    if (selectedBook != null) {
//      viewHandler.openView("EditBookView.fxml");
//    }
//  }
//
//  public void deleteSelectedBook() {
//    if (selectedBook != null) {
//      try {
//        dao.delete(selectedBook.getBookId());
//      } catch (SQLException e) {
//        e.printStackTrace();
//      }
//    }
//  }
//  public void goHome() {
//    viewHandler.openView("HomeView.fxml");
//  }
//}
