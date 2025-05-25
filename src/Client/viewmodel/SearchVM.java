package Client.viewmodel;


import Client.network.ClientSocketHandler;
import Server.model.Book;
import Shared.dto.enums.Action;
import Shared.network.Request;
import Shared.network.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

import java.util.logging.SocketHandler;

import static java.util.Arrays.stream;



public class SearchVM {
  private final ObservableList<Book> books = FXCollections.observableArrayList();
  private final ClientSocketHandler socketHandler;

  private Book selectedBook;

  public SearchVM(ClientSocketHandler socketHandler) {
    this.socketHandler = socketHandler;
  }

  public void loadBooks() {
    try {
      Request req = new Request(Action.GET_ALL_BOOKS, null);
      socketHandler.sendRequest(req);
      Response resp = socketHandler.readResponse();
      if (resp.isSuccess()) {
        List<Book> list = (List<Book>) resp.getData();
        books.setAll(list);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ObservableList<Book> getBooks() {
    return books;
  }

  public void setSelectedBook(Book book) {
    this.selectedBook = book;
  }

  public Book getSelectedBook() {
    return selectedBook;
  }
}



/*public class SearchVM
{

  private final ObservableList<String> searchResults;
  private final ObservableList<String> genres;
  private final ObservableList<String> formats;
  private final ObservableList<String> owners;
  private final ObservableList<String> borrowers;

  private final ClientSocketHandler socketHandler;



  public SearchVM(SocketHandler socketHandler) {

    this.socketHandler = (ClientSocketHandler) socketHandler;

    //TODO: give a search service attrivute (or clientsockethandler) instead of a DAO
    searchResults = FXCollections.observableArrayList();
    genres = FXCollections.observableArrayList("All", "Fiction", "Manga", "History");
    formats = FXCollections.observableArrayList("All", "Paperback", "Hardcover", "EBook");
    owners = FXCollections.observableArrayList("All", "Mara", "Karina", "Jasmin");
    borrowers = FXCollections.observableArrayList("All", "Mara", "Karina", "Jasmin");
  }

  public ObservableList<String> getSearchResults() {
    return searchResults;
  }

  public ObservableList<String> getGenres() {
    return genres;
  }

  public ObservableList<String> getFormats() {
    return formats;
  }

  public ObservableList<String> getOwnerUsernames() {
    return owners;
  }

  public ObservableList<String> getBorrowerUsernames() {
    return borrowers;
  }

  public void performSearch(String query) {
    searchResults.clear();
    // Временно статично:
    searchResults.add("Found: " + query);
    searchResults.add("Book XYZ by Author");
  }

  //get all books

  public void viewBookDetails(String selected)
  {

  }
}*/


//  private final BookDAO dao;
//
//  public SearchVM(JdbcBookDAO dao) {
//    this.dao = dao;
//  }
//
//  public List<String> search(String title, String genre, String format, String owner, String borrower, String status) {
//    try {
//      return dao.findAll().stream()
//          .filter(book -> title == null || book.getTitle().toLowerCase().contains(title.toLowerCase()))
//          .map(Book::getTitle)
//          .collect(Collectors.toList());
//    } catch (SQLException e) {
//      return List.of("Error searching: " + e.getMessage());
//    }
//  }
//}
