package repository;

import dto.enums.BookStatus;
import dto.enums.Format;
import dto.enums.Genre;
import model.*;
import model.status.*;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookDAO implements BookDAO
{
  private static JdbcBookDAO instance; // Singleton instance, might opt out, not sure yet

  private JdbcBookDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }
  public static JdbcBookDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new JdbcBookDAO();
    }
    return instance;
  }
//  replaced with DBConnection
//  private static Connection getConnection() throws SQLException
//  {
//    return DriverManager.getConnection(
//        "jdbc:postgresql://localhost:5432/BookStore"+ "?currentSchema=bfl_library", "postgres", "password");
//  }
  @Override
  public synchronized Book create(
      String title,
      String author,
      Genre genre,
      String isbn,
      Format format,
      String description,
      String imagePath,
      User owner
  ) throws SQLException
  {
    // 1) build the Book (assigns bookId and default Available state)
    Book book = new Book(title, author, genre, isbn, format, description, imagePath, owner);

    // 2) insert every field (including bookId & status)
    String sql = """
      INSERT INTO books
        (bookId, title, author, genre, isbn, format, description, imagePath, owner, status)
      VALUES (?,      ?,     ?,      ?,     ?,    ?,      ?,           ?,         ?,     ?)
      """;

    try ( Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql) ) {

      ps.setInt(1, book.getBookId());
      ps.setString(2, book.getTitle());
      ps.setString(3, book.getAuthor());
      ps.setString(4, book.getGenre().getGenreName());
      ps.setString(5, book.getIsbn());
      ps.setString(6, book.getFormat().name());
      ps.setString(7, book.getDescription());
      ps.setString(8, book.getImagePath());
      ps.setInt(9, book.getOwner().getUserId());
      // initial state is always Available
      ps.setString(10, dto.enums.BookStatus.AVAILABLE.name());

      ps.executeUpdate();
    }

    return book;
  }




  @Override public BookSummary findById(int id) throws SQLException
  {
    try(Connection c = DBConnection.getConnection())
    {
      PreparedStatement statement = c.prepareStatement(
          "SELECT b.title, b.author, u.username as owner, b.genre, b.format, b.status FROM books b join users u on b.owner=u.id  WHERE bookId = ?");
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        Genre genre = Genre.valueOf(resultSet.getString("genre"));
        Format format = Format.valueOf(resultSet.getString("format"));
        String ownerName = resultSet.getString("ownerName");
        BookStatus status = BookStatus.valueOf(resultSet.getString("status"));
        return new BookSummary(title, author, ownerName, format, genre, status);
      }
      else
      {
        return null;
      }
    }
  }

  @Override public List<BookSummary> findAll() throws SQLException
  {
   try(Connection c = DBConnection.getConnection())
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



  @Override public List<BookSummary> findByTitle(String searchString) throws SQLException
  {
    try(Connection c = DBConnection.getConnection())
    {
      PreparedStatement statement = c.prepareStatement(
          "SELECT b.title, b.author, u.username as owner, b.genre, b.format, b.status FROM books b join users u on b.owner=u.id  WHERE title LIKE ?");
      statement.setString(1, "%" + searchString + "%");
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

  @Override public List<BookSummary> findByIsbn(String isbn) throws SQLException
  {
    try(Connection c =DBConnection.getConnection())
    {
      PreparedStatement statement = c.prepareStatement(
          "SELECT b.title, b.author, u.username as owner, b.genre, b.format, b.status FROM books b join users u on b.owner=u.id  WHERE isbn = ?");
      statement.setString(1, isbn);
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

  @Override public List<BookSummary> findByAuthor(String author)throws SQLException
  {
    try(Connection c = DBConnection.getConnection())
    {
      PreparedStatement statement = c.prepareStatement(
          "SELECT b.title, b.author, u.username as owner, b.genre, b.format, b.status FROM books b join users u on b.owner=u.id  WHERE author LIKE ?");
      statement.setString(1, "%" + author + "%");
      ResultSet resultSet = statement.executeQuery();
      List<BookSummary> books = new ArrayList<>();
      while (resultSet.next())
      {
        String title = resultSet.getString("title");
        String authorName = resultSet.getString("author");
        Genre genre = Genre.valueOf(resultSet.getString("genre"));
        Format format = Format.valueOf(resultSet.getString("format"));
        String ownerName = resultSet.getString("ownerName");
        BookStatus status = BookStatus.valueOf(resultSet.getString("status"));
        books.add( new BookSummary(title, authorName, ownerName, format, genre, status));
      }
      return books;
    }
  }

  @Override public List<BookSummary> findByGenre(Genre genre)throws SQLException
  {
    try (Connection c = DBConnection.getConnection())
    {
      PreparedStatement statement = c.prepareStatement(
          "SELECT b.title, b.author, u.username as owner, b.genre, b.format, b.status FROM books b join users u on b.owner=u.id  WHERE genre = ?");
      statement.setString(1, genre.toString());
      ResultSet resultSet = statement.executeQuery();
      List<BookSummary> books = new ArrayList<>();
      while (resultSet.next())
      {
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        Format format = Format.valueOf(resultSet.getString("format"));
        String ownerName = resultSet.getString("ownerName");
        BookStatus status = BookStatus.valueOf(resultSet.getString("status"));
        books.add( new BookSummary(title, author, ownerName, format, genre, status));
      }
      return books;
    }
  }

  @Override public List<BookSummary> findByFormat(Format format)throws SQLException
  {
    try(Connection c = DBConnection.getConnection())
    {
      PreparedStatement statement = c.prepareStatement(
          "SELECT b.title, b.author, u.username as owner, b.genre, b.format, b.status FROM books b join users u on b.owner=u.id  WHERE format = ?");
      statement.setString(1, format.toString());
      ResultSet resultSet = statement.executeQuery();
      List<BookSummary> books = new ArrayList<>();
      while (resultSet.next())
      {
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        Genre genre = Genre.valueOf(resultSet.getString("genre"));
        String ownerName = resultSet.getString("ownerName");
        BookStatus status = BookStatus.valueOf(resultSet.getString("status"));
        books.add( new BookSummary(title, author, ownerName, format, genre, status));
      }
      return books;
    }
  }

  @Override public List<BookSummary> findByOwner(User owner)throws SQLException
  {
    try(Connection c =DBConnection.getConnection())
    {
      PreparedStatement statement = c.prepareStatement(
          "SELECT b.title, b.author, u.username as owner, b.genre, b.format, b.status FROM books b join users u on b.owner=u.id  WHERE owner = ?");
      statement.setInt(1, owner.getUserId());
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

  @Override public List<BookSummary> findByStatus(Status status)throws SQLException
  {
    try(Connection c = DBConnection.getConnection())
    {
      PreparedStatement statement = c.prepareStatement(
          "SELECT b.title, b.author, u.username as owner, b.genre, b.format, b.status FROM books b join users u on b.owner=u.id  WHERE status = ?");
      statement.setString(1, status.toString());
      ResultSet resultSet = statement.executeQuery();
      List<BookSummary> books = new ArrayList<>();
      while (resultSet.next())
      {
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        Genre genre = Genre.valueOf(resultSet.getString("genre"));
        Format format = Format.valueOf(resultSet.getString("format"));
        String ownerName = resultSet.getString("ownerName");
        BookStatus bookStatus = BookStatus.valueOf(resultSet.getString("status"));
        books.add( new BookSummary(title, author, ownerName, format, genre, bookStatus));
      }
      return books;
    }
  }

  @Override public List<BookSummary> findByBorrowedBy(User borrowedBy)throws SQLException
  {
    //TODO: implement this method
    System.out.println("findByBorrowedBy method not implemented");
    return null;
  }

  @Override public void save(Book b)
  {
    System.out.println("save method not implemented");
  }

  @Override
  public void update(Book b) throws SQLException {
    String sql = """
      UPDATE books
         SET title       = ?
           , author      = ?
           , genre       = ?
           , isbn        = ?
           , format      = ?
           , description = ?
           , imagePath   = ?
           , owner       = ?
           , status      = ?
       WHERE bookId      = ?
      """;

    try ( Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql) ) {

      ps.setString(1, b.getTitle());
      ps.setString(2, b.getAuthor());
      ps.setString(3, b.getGenre().name());
      ps.setString(4, b.getIsbn());
      ps.setString(5, b.getFormat().name());
      ps.setString(6, b.getDescription());
      ps.setString(7, b.getImagePath());
      ps.setInt   (8, b.getOwner().getUserId());
      // assume you map your Status object back to the enum name:
      ps.setString(9, b.getStatus().getStatus().name());
      ps.setInt   (10, b.getBookId());

      ps.executeUpdate();
    }
  }


  @Override public void delete(int id) throws SQLException
  {
    try(Connection c = DBConnection.getConnection())
    {
      PreparedStatement statement = c.prepareStatement(
          "DELETE FROM books WHERE bookId = ?");
      statement.setInt(1, id);
      statement.executeUpdate();
    }

  }
}
