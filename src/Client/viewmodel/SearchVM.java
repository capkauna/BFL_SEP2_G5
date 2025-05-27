package Client.viewmodel;


import Client.network.ClientSocketHandler;
import Shared.dto.BookSummaryDTO;
import Shared.dto.enums.Action;
import Shared.network.Request;
import Shared.network.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

import static java.util.Arrays.stream;



public class SearchVM {
  private final ObservableList<BookSummaryDTO> books = FXCollections.observableArrayList();
  private final ClientSocketHandler socketHandler;

  private BookSummaryDTO selectedBook;

  public SearchVM(ClientSocketHandler socketHandler) {
    this.socketHandler = socketHandler;
  }

  public void loadBooks() {
    System.out.println("->Sending GET_ALL_BOOKS request to server");
    try {
      Request req = new Request(Action.GET_ALL_BOOKS, null);
      socketHandler.sendRequest(req);
      Response resp = socketHandler.readResponse();
      System.out.println(" <- Got response: " + resp.isSuccess());
      
      if (resp.isSuccess()) {
        ArrayList<BookSummaryDTO> bookSummaryList = (ArrayList<BookSummaryDTO>) resp.getData();
        System.out.println("Loaded books: " + bookSummaryList.size());
        books.setAll(bookSummaryList);
      } else {
        System.err.println("Failed to load books: " + resp.getErrorMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error loading books");
    }
  }

  public ObservableList<BookSummaryDTO> getBooks() {
    return books;
  }

  public void setSelectedBook(BookSummaryDTO book) {
    this.selectedBook = book;
  }

  public BookSummaryDTO getSelectedBook() {
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
//  public ArrayList<String> search(String title, String genre, String format, String owner, String borrower, String status) {
//    try {
//      return dao.findAll().stream()
//          .filter(book -> title == null || book.getTitle().toLowerCase().contains(title.toLowerCase()))
//          .map(Book::getTitle)
//          .collect(Collectors.toList());
//    } catch (SQLException e) {
//      return ArrayList.of("Error searching: " + e.getMessage());
//    }
//  }
//}
