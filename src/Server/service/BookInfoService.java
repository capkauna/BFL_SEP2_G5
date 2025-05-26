package Server.service;

import Server.database.JdbcBookDAO;
import Server.model.Book;
import Server.model.User;
import Shared.dto.BookSummaryDTO;
import Shared.dto.enums.Genre;

import java.sql.SQLException;
import java.util.ArrayList;

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
  public ArrayList<Book> getAllBooks() throws SQLException
  {
    return books.findAll();
  }
  public ArrayList<BookSummaryDTO> getAllBookSummaries() throws SQLException
  {
    ArrayList<Book>booksToTurn= books.findAll();
    ArrayList<BookSummaryDTO>turnedBooks = new ArrayList<>();
    for (Book b : booksToTurn) {
      BookSummaryDTO newBook = convertToSummary(b);
      turnedBooks.add(newBook);
    }
    return turnedBooks;
  }
  public ArrayList<Book> getBooksByTitle(String title) throws SQLException
  {
    return books.findByTitle(title);
  }
  public ArrayList<Book> getBooksByAuthor(String author) throws SQLException
  {
    return books.findByAuthor(author);
  }
  public ArrayList<Book> getBooksByGenre(Genre genre) throws SQLException
  {
    return books.findByGenre(genre);
  }
  public ArrayList<Book> getBorrowedBooksBy(User u) throws SQLException
  {
    return books.findByBorrowedBy(u);
  }
  public ArrayList<Book> getBooksByOwner(User u) throws SQLException
  {
    return books.findByOwner(u);
  }
  private BookSummaryDTO convertToSummary(Book book) {
    return new BookSummaryDTO(
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
  private ArrayList<BookSummaryDTO> convertListToSummary(ArrayList<Book> books) {
    ArrayList<BookSummaryDTO> summaries = new java.util.ArrayList<>();
    for (Book book : books) {
      summaries.add(convertToSummary(book));
    }
    return summaries;
  }


}
