package repository;

import dto.enums.BookStatus;
import dto.enums.Format;
import dto.enums.Genre;
import model.Book;
import model.BookEntity;
import model.BookSummary;
import model.User;
import model.status.Status;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO
{
  @Override public Book create(Book newBook) throws SQLException
  {
    return null;
  }

  @Override public Book findById(int id) throws SQLException
  {
    return null;
  }

  @Override public List<Book> findAll() throws SQLException
  {
    return List.of();
  }

  @Override public List<Book> findByTitle(String title) throws SQLException
  {
    return List.of();
  }

  @Override public List<Book> findByIsbn(String isbn) throws SQLException
  {
    return List.of();
  }

  @Override public List<Book> findByAuthor(String author) throws SQLException
  {
    return List.of();
  }

  @Override public List<Book> findByGenre(Genre genre) throws SQLException
  {
    return List.of();
  }

  @Override public List<Book> findByFormat(Format format) throws SQLException
  {
    return List.of();
  }

  @Override public List<Book> findByOwner(User owner) throws SQLException
  {
    return List.of();
  }

  @Override public List<Book> findByStatus(Status status) throws SQLException
  {
    return List.of();
  }

  @Override public List<Book> findByBorrowedBy(User borrowedBy)
      throws SQLException
  {
    return List.of();
  }

  @Override public void save(Book b) throws SQLException
  {

  }

  @Override public void update(Book b) throws SQLException
  {

  }

  @Override public void delete(int id) throws SQLException
  {

  }
  //  private final Connection connection;
//
//  public BookDAOImpl(Connection connection){
//    this.connection = connection;
//  }
//  private static Connection getConnection() throws SQLException
//  {
//    return DriverManager.getConnection(
//        "jdbc:postgresql://localhost:5432/BookStore"+ "?currentSchema=bfl_library", "postgres", "password");
//  }
//  private Book mapResultSetToBook(ResultSet rs) throws SQLException {
//    int    id          = rs.getInt("book_id");
//    String title       = rs.getString("title");
//    String author      = rs.getString("author");
//    Genre  genre       = Genre.valueOf(rs.getString("genre"));
//    String isbn        = rs.getString("isbn");
//    Format format      = Format.valueOf(rs.getString("format"));
//    String description = rs.getString("description");
//    String image       = rs.getString("image");
//    int    ownerId     = rs.getInt("owner_id");
//    String rawStatus   = rs.getString("status");
//    int    year        = rs.getInt("year");
//
//    User owner = userDao.findById(ownerId);
//    Status status = parseStatus(rawStatus, owner);
//
//    return new Book(id, title, author, genre, isbn, format,
//        description, image, owner, status, year);
//  }
//
//  public void save(BookEntity book)
//  {
//    String sql = "INSERT INTO books (title, publisher, ownerId, year) VALUES (?, ?, ?, ?)";
//    try(PreparedStatement stmt = connection.prepareStatement(sql))
//    {
//      stmt.setString(1, book.getTitle());
//      stmt.setString(2, book.getPublisher());
//      stmt.setInt(3, book.getOwnerId());
//      stmt.setInt(4, book.getYear());
//      stmt.executeUpdate();
//      System.out.println("Book saved successfully");
//    }
//    catch (SQLException e)
//    {
//      System.out.println("Error saving book: " + e.getMessage());
//    }
//  }
//  public List<Book> findAll() throws SQLException
//  {
//    String sql = """
//            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
//                   b.description, b.image, b.owner_id, b.status, b.year
//              FROM books b
//            """;
//    List<Book> books = new ArrayList<>();
//    try (Connection c = DBConnection.getConnection();
//        PreparedStatement ps = c.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery()) {
//
//      while (rs.next()) {
//        books.add(mapResultSetToBook(rs));
//      }
//    }
//    return books;
//    }
//  }
//
//  @Override public List<BookSummary> findByTitle(String title)
//      throws SQLException
//  {
//    return List.of();
//  }
//
//  @Override public List<BookSummary> findByIsbn(String isbn) throws SQLException
//  {
//    return List.of();
//  }
//
//  @Override public List<BookSummary> findByAuthor(String author)
//      throws SQLException
//  {
//    return List.of();
//  }
//
//  @Override public List<BookSummary> findByGenre(Genre genre)
//      throws SQLException
//  {
//    return List.of();
//  }
//
//  @Override public List<BookSummary> findByFormat(Format format)
//      throws SQLException
//  {
//    return List.of();
//  }
//
//  @Override public List<BookSummary> findByOwner(User owner) throws SQLException
//  {
//    return List.of();
//  }
//
//  @Override public List<BookSummary> findByStatus(Status status)
//      throws SQLException
//  {
//    return List.of();
//  }
//
//  @Override public List<BookSummary> findByBorrowedBy(User borrowedBy)
//      throws SQLException
//  {
//    return List.of();
//  }
//
//  @Override public void save(Book b)
//  {
//
//  }
//
//  @Override public void update(Book b) throws SQLException
//  {
//
//  }
//
//  @Override public void delete(int id) throws SQLException
//  {
//
//  }
//
//  @Override public Book create(Book newBook) throws SQLException
//  {
//    return null;
//  }
//
//  public Book findById(int id)
//  {
//    String sql = "SELECT * FROM books WHERE bookId = ?";
//    try(PreparedStatement stmt = connection.prepareStatement(sql))
//    {
//      stmt.setInt(1, id);
//      try(ResultSet rs = stmt.executeQuery();)
//      {
//      if (rs.next()){
////        return new BookEntity(
////            rs.getInt("bookId"),
////            rs.getString("title"),
////            rs.getString("publisher"),
////            rs.getInt("ownerId"),
////            rs.getInt("year")
////        );
//      }
//    }
//    catch (SQLException e)
//    {
//      System.out.println("Error retrieving book: " + e.getMessage());
//    }
//    return null;
//  }
//    catch (SQLException e)
//    {
//      throw new RuntimeException(e);
//    }
//
//  }
}
