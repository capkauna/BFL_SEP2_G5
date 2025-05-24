package Server.repository;

import Shared.dto.enums.Format;
import Shared.dto.enums.Genre;
import Server.model.Book;
import Server.model.User;
import Server.model.status.Status;
import Server.model.status.Available;
import Server.model.status.Borrowed;
import Server.util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookDAO implements BookDAO {
  private static JdbcBookDAO instance;
  private final UserDAO userDao;

  private JdbcBookDAO() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
    this.userDao = JdbcUserDAO.getInstance();
  }

  public static JdbcBookDAO getInstance() throws SQLException {
    if (instance == null) {
      instance = new JdbcBookDAO();
    }
    return instance;
  }

  @Override
  public synchronized Book create(Book book) throws SQLException {
    String sql = """
            INSERT INTO books
               (title, author, genre, isbn, format, description, image, owner_id, status, year)
            VALUES (?,     ?,      ?,     ?,    ?,      ?,           ?,     ?,     ?,     ?)
            RETURNING book_id
            """;

    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, book.getTitle());
      ps.setString(2, book.getAuthor());
      ps.setString(3, book.getGenre().name());
      ps.setString(4, book.getIsbn());
      ps.setString(5, book.getFormat().name());
      ps.setString(6, book.getDescription());
      ps.setString(7, book.getImage());
      ps.setInt(8, book.getOwner().getUserId());
      ps.setString(9, book.getStatus().toString());
      ps.setInt(10, book.getYear());

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          book.setBookId(rs.getInt("book_id"));
        }
      }
      return book;
    }
  }

  @Override
  public Book findById(int id) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.book_id = ?
            """;
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return mapResultSetToBook(rs);
        }
      }
    }
    return null;
  }

  @Override
  public List<Book> findAll() throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
            """;
    List<Book> books = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        books.add(mapResultSetToBook(rs));
      }
    }
    return books;
  }

  @Override
  public List<Book> findByTitle(String searchString) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.title LIKE ?
            """;
    List<Book> books = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, "%" + searchString + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          books.add(mapResultSetToBook(rs));
        }
      }
    }
    return books;
  }

  @Override
  public List<Book> findByAuthor(String searchString) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.author LIKE ?
            """;
    List<Book> books = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, "%" + searchString + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          books.add(mapResultSetToBook(rs));
        }
      }
    }
    return books;
  }
  @Override
  public List<Book> findByGenre(Genre genre) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.genre = ?
            """;
    List<Book> books = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, genre.name());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          books.add(mapResultSetToBook(rs));
        }
      }
    }
    return books;
  }
  @Override
  public List<Book> findByFormat(Format format) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.format = ?
            """;
    List<Book> books = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, format.name());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          books.add(mapResultSetToBook(rs));
        }
      }
    }
    return books;
  }



    @Override
  public List<Book> findByOwner(User owner) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.owner_id = ?
            """;
    List<Book> books = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setInt(1, owner.getUserId());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          books.add(mapResultSetToBook(rs));
        }
      }
    }
    return books;
  }
  @Override
  public List<Book> findByStatus(Status status) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.status = ?
            """;
    List<Book> books = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, status.toString());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          books.add(mapResultSetToBook(rs));
        }
      }
    }
    return books;
  }
  @Override
  public List<Book> findByBorrowedBy(User borrowedBy) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.status LIKE ?
            """;
    List<Book> books = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, "Borrowed by " + borrowedBy.getUserName());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          books.add(mapResultSetToBook(rs));
        }
      }
    }
    return books;
  }
  @Override
  public List<Book> findByIsbn(String searchString) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.isbn LIKE ?
            """;
    List<Book> books = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, "%" + searchString + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          books.add(mapResultSetToBook(rs));
        }
      }
    }
    return books;
  }

  @Override
  public void save(Book b) throws SQLException {
    update(b);
  }

  @Override
  public void update(Book b) throws SQLException {
    String sql = """
            UPDATE books
               SET title       = ?,
                   author      = ?,
                   genre       = ?,
                   isbn        = ?,
                   format      = ?,
                   description = ?,
                   image       = ?,
                   owner_id    = ?,
                   status      = ?,
                   year        = ?
             WHERE book_id    = ?
            """;
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, b.getTitle());
      ps.setString(2, b.getAuthor());
      ps.setString(3, b.getGenre().name());
      ps.setString(4, b.getIsbn());
      ps.setString(5, b.getFormat().name());
      ps.setString(6, b.getDescription());
      ps.setString(7, b.getImage());
      ps.setInt(8, b.getOwner().getUserId());
      ps.setString(9, b.getStatus().toString());
      ps.setInt(10, b.getYear());
      ps.setInt(11, b.getBookId());

      ps.executeUpdate();
    }
  }

  @Override
  public void delete(int id) throws SQLException {
    String sql = "DELETE FROM books WHERE book_id = ?";
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, id);
      ps.executeUpdate();
    }
  }

  private Book mapResultSetToBook(ResultSet rs) throws SQLException {
    int    id          = rs.getInt("book_id");
    String title       = rs.getString("title");
    String author      = rs.getString("author");
    Genre  genre       = Genre.valueOf(rs.getString("genre"));
    String isbn        = rs.getString("isbn");
    Format format      = Format.valueOf(rs.getString("format"));
    String description = rs.getString("description");
    String image       = rs.getString("image");
    int    ownerId     = rs.getInt("owner_id");
    String rawStatus   = rs.getString("status");
    int    year        = rs.getInt("year");

    User owner = userDao.findById(ownerId);
    Status status = parseStatus(rawStatus, owner);

    return new Book(
        id,
        title,
        author,
        year,
        genre,
        isbn,
        format,
        description,
        image,
        owner,
        status);
  }



//TODO check what is this (this is bad, needs to be fixed)
  private Status parseStatus(String raw, User owner) {
    if (raw.startsWith("Borrowed by ")) {
      // borrower name part ignored here; real impl would lookup borrower
      return new Borrowed(owner);
    }
    return new Available();
  }
}
