package Server.service;

import Server.database.JdbcBookDAO;
import Server.model.Book;
import Server.model.User;
import Shared.dto.BookSummary;
import Shared.dto.enums.Genre;

import java.sql.SQLException;
import java.util.List;

public class BookInfoService
{
  private final JdbcBookDAO books;
  public BookInfoService() throws SQLException
  {
    this.books = JdbcBookDAO.getInstance();
  }

  public Book getBookInfo(int bookId) throws SQLException
  {
    return books.findById(bookId);
  }
  public List<Book> getAllBooks() throws SQLException
  {
    return books.findAll();
  }
  public List<BookSummary> getAllBookSummaries() throws SQLException
  {
    List<Book>booksToTurn= books.findAll();
    List<BookSummary>turnedBooks = new java.util.ArrayList<>();
    for (Book b : booksToTurn) {

      BookSummary newBook = convertToSummary(b);
      turnedBooks.add(newBook);
    }
    return turnedBooks;
  }
  public List<Book> getBooksByTitle(String title) throws SQLException
  {
    return books.findByTitle(title);
  }
  public List<Book> getBooksByAuthor(String author) throws SQLException
  {
    return books.findByAuthor(author);
  }
  public List<Book> getBooksByGenre(Genre genre) throws SQLException
  {
    return books.findByGenre(genre);
  }
  public List<Book> getBorrowedBooksBy(User u) throws SQLException
  {
    return books.findByBorrowedBy(u);
  }
  public List<Book> getBooksByOwner(User u) throws SQLException
  {
    return books.findByOwner(u);
  }
  private BookSummary convertToSummary(Book book) {
    return new BookSummary(
        book.getBookId(),
        book.getTitle(),
        book.getAuthor(),
        book.getIsbn(),
        book.getOwner().getUserName(),
        book.getFormat(),
        book.getGenre(),
        book.getStatus().toString(),
        book.getDescription(),
        book.getImage()
    );
  }
  private List<BookSummary> convertListToSummary(List<Book> books) {
    List<BookSummary> summaries = new java.util.ArrayList<>();
    for (Book book : books) {
      summaries.add(convertToSummary(book));
    }
    return summaries;
  }


}
