package Server.service;

import Server.database.*;
import Server.model.*;
import Server.model.status.*;
import Shared.dto.*;
import Shared.dto.enums.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookInfoService
{
  private final BookDAO books;
  private final JdbcBookDAO lends;
  public BookInfoService() throws SQLException
  {
    this.books = JdbcBookDAO.getInstance();
    //added to handle lending operations
    this.lends = JdbcBookDAO.getInstance();
  }

  public static Book getBookInfo(int bookId) throws SQLException
  {
    return JdbcBookDAO.getInstance().findById(bookId);
  }
  public ArrayList<Book> getAllBooks() throws SQLException
  {
    return books.findAll();
  }
  public ArrayList<BookSummaryDTO> getMyBookSummaries(int userId) throws SQLException
  {
    ArrayList<Book>booksToTurn= books.findMyBooks(userId);
    ArrayList<BookSummaryDTO>myBooks = new ArrayList<>();
    for (Book b : booksToTurn) {
      BookSummaryDTO newBook = convertToSummary(b);
      myBooks.add(newBook);
    }
    return myBooks;
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
  public void lendBook(int bookId, int userId) throws SQLException
  {
    Book book = books.findById(bookId);
    if (book == null) {
      throw new SQLException("Book not found");
    }
    if (book.getStatus() instanceof Borrowed || book.getStatus() instanceof Unavailable) {
      throw new SQLException("Book is not available for lending");
    }
    UserInfoService userService = new UserInfoService();
    FullUserDTO borrow = userService.getUserInfo(userId);
    User borrower = new User(borrow.getUserName(), borrow.getEmail(),
        borrow.getFullName(), borrow.getPhoneNumber(), borrow.getAddress(), borrow.getAvatar());

    book.lendTo(borrower);
    books.update(book);
  }


}
