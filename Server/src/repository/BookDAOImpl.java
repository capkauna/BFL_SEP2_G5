package repository;

import dto.enums.BookStatus;
import dto.enums.Format;
import dto.enums.Genre;
import model.Book;
import model.BookEntity;
import model.BookSummary;
import model.User;
import model.status.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO
{
  private final Connection connection;

  public BookDAOImpl(Connection connection){
    this.connection = connection;
  }
  private static Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/BookStore"+ "?currentSchema=bfl_library", "postgres", "password");
  }

  public void save(BookEntity book)
  {
    String sql = "INSERT INTO books (title, publisher, ownerId, year) VALUES (?, ?, ?, ?)";
    try(PreparedStatement stmt = connection.prepareStatement(sql))
    {
      stmt.setString(1, book.getTitle());
      stmt.setString(2, book.getPublisher());
      stmt.setInt(3, book.getOwnerId());
      stmt.setInt(4, book.getYear());
      stmt.executeUpdate();
      System.out.println("Book saved successfully");
    }
    catch (SQLException e)
    {
      System.out.println("Error saving book: " + e.getMessage());
    }
  }
  public List<BookSummary> findAll() throws SQLException
  {
    try(Connection c = getConnection())
    {
      PreparedStatement statement = c.prepareStatement(
          "SELECT b.title, b.author, u.username as owner, b.genre, b.format, b.status FROM books b join users u on b.owner=u.id");
      ResultSet resultSet = statement.executeQuery();
      List<BookSummary> books = new ArrayList<>();
      while (resultSet.next())
      {
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        Genre genre = Genre.valueOf(resultSet.getString("genre"));
        Format format = Format.valueOf(resultSet.getString("format"));
        String ownerName = resultSet.getString("ownerName");
        BookStatus status = BookStatus.valueOf(resultSet.getString("status"));
        books.add( new BookSummary(title, author, ownerName, format, genre, status));
      }
      return books;
    }
  }

  @Override public List<BookSummary> findByTitle(String title)
      throws SQLException
  {
    return List.of();
  }

  @Override public List<BookSummary> findByIsbn(String isbn) throws SQLException
  {
    return List.of();
  }

  @Override public List<BookSummary> findByAuthor(String author)
      throws SQLException
  {
    return List.of();
  }

  @Override public List<BookSummary> findByGenre(Genre genre)
      throws SQLException
  {
    return List.of();
  }

  @Override public List<BookSummary> findByFormat(Format format)
      throws SQLException
  {
    return List.of();
  }

  @Override public List<BookSummary> findByOwner(User owner) throws SQLException
  {
    return List.of();
  }

  @Override public List<BookSummary> findByStatus(Status status)
      throws SQLException
  {
    return List.of();
  }

  @Override public List<BookSummary> findByBorrowedBy(User borrowedBy)
      throws SQLException
  {
    return List.of();
  }

  @Override public void save(Book b)
  {

  }

  @Override public void update(Book b) throws SQLException
  {

  }

  @Override public void delete(int id) throws SQLException
  {

  }

  @Override public Book create(String title, String author, Genre genre,
      String isbn, Format format, String description, String imagePath,
      User owner) throws SQLException
  {
    return null;
  }

  public BookSummary findById(int id)
  {
    String sql = "SELECT * FROM books WHERE bookId = ?";
    try(PreparedStatement stmt = connection.prepareStatement(sql))
    {
      stmt.setInt(1, id);
      try(ResultSet rs = stmt.executeQuery();)
      {
      if (rs.next()){
//        return new BookEntity(
//            rs.getInt("bookId"),
//            rs.getString("title"),
//            rs.getString("publisher"),
//            rs.getInt("ownerId"),
//            rs.getInt("year")
//        );
      }
    }
    catch (SQLException e)
    {
      System.out.println("Error retrieving book: " + e.getMessage());
    }
    return null;
  }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }

  }
}
