package Client.viewmodel;

import Server.repository.JdbcBookDAO;
import Server.model.Book;
import Server.repository.BookDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class SearchVM
{
  private final BookDAO dao;

  public SearchVM(JdbcBookDAO dao) {
    this.dao = dao;
  }

  public List<String> search(String title, String genre, String format, String owner, String borrower, String status) {
    try {
      return dao.findAll().stream()
          .filter(book -> title == null || book.getTitle().toLowerCase().contains(title.toLowerCase()))
          .map(Book::getTitle)
          .collect(Collectors.toList());
    } catch (SQLException e) {
      return List.of("Error searching: " + e.getMessage());
    }
  }
}
