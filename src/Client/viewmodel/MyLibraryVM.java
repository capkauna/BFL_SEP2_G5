package viewmodel;

import model.Book;
import repository.BookDAO;
import util.Session;
import view.ViewHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MyLibraryVM
{
  private final BookDAO dao;
  private final ViewHandler viewHandler;
  private Book selectedBook;

  public MyLibraryVM(BookDAO dao, ViewHandler viewHandler) {
    this.dao = dao;
    this.viewHandler = viewHandler;
  }

  public List<String> getMyBooks() {
    try {
      return dao.findByOwner(Session.getLoggedInUser()).stream()
          .map(Book::getTitle)
          .collect(Collectors.toList());
    } catch (SQLException e) {
      return List.of("Error: " + e.getMessage());
    }
  }

  public void selectedBookByTitle(String title) {
    try {
      selectedBook = dao.findByTitle(title).stream()
          .filter(book -> book.getOwner().getUserId() == Session.getLoggedInUser().getUserId())
          .findFirst().orElse(null);
    } catch (SQLException e) {
      selectedBook = null;
    }
  }

  public void editSelectedBook() {
    if (selectedBook != null) {
      viewHandler.openView("EditBookView.fxml");
    }
  }

  public void deleteSelectedBook() {
    if (selectedBook != null) {
      try {
        dao.delete(selectedBook.getBookId());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
  public void goHome() {
    viewHandler.openView("HomeView.fxml");
  }
}
