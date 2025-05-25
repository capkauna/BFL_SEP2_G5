package Client.viewmodel;

import Server.repository.JdbcBookDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static java.util.Arrays.stream;

public class SearchVM
{
  private final ObservableList<String> searchResults;
  private final ObservableList<String> genres;
  private final ObservableList<String> formats;
  private final ObservableList<String> owners;
  private final ObservableList<String> borrowers;

  public SearchVM(JdbcBookDAO instance) {
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
}


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
