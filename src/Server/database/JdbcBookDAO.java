package Server.database;

import Shared.dto.enums.Format;
import Shared.dto.enums.Genre;
import Server.model.Book;
import Server.model.User;
import Server.model.status.Status;
import Server.model.status.Available;
import Server.model.status.Borrowed;
import Server.dbstart.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;

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
  public ArrayList<Book> findAll() throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
            """;
    ArrayList<Book> books = new ArrayList<>();
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
  public ArrayList<Book> findByTitle(String searchString) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.title LIKE ?
            """;
    ArrayList<Book> books = new ArrayList<>();
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
  public ArrayList<Book> findByAuthor(String searchString) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.author LIKE ?
            """;
    ArrayList<Book> books = new ArrayList<>();
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
  public ArrayList<Book> findByGenre(Genre genre) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.genre = ?
            """;
    ArrayList<Book> books = new ArrayList<>();
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
  public ArrayList<Book> findByFormat(Format format) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.format = ?
            """;
    ArrayList<Book> books = new ArrayList<>();
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
  public ArrayList<Book> findByOwner(User owner) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.owner_id = ?
            """;
    ArrayList<Book> books = new ArrayList<>();
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
  public ArrayList<Book> findByStatus(Status status) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.status = ?
            """;
    ArrayList<Book> books = new ArrayList<>();
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
  //TODO check this again
  @Override
  public ArrayList<Book> findByBorrowedBy(User borrowedBy) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.status LIKE ?
            """;
    ArrayList<Book> books = new ArrayList<>();
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
  public ArrayList<Book> findByIsbn(String searchString) throws SQLException {
    String sql = """
            SELECT b.book_id, b.title, b.author, b.genre, b.isbn, b.format,
                   b.description, b.image, b.owner_id, b.status, b.year
              FROM books b
             WHERE b.isbn LIKE ?
            """;
    ArrayList<Book> books = new ArrayList<>();
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
    String rawGenre    = rs.getString("genre");    // e.g. "Science Fiction"
    String isbn        = rs.getString("isbn");
    String rawFormat   = rs.getString("format");   // e.g. "HARDCOVER"
    String description = rs.getString("description");
    String image       = rs.getString("image");
    int    ownerId     = rs.getInt("owner_id");
    String rawStatus   = rs.getString("status");  // e.g. "Borrowed by ash"
    int    year        = rs.getInt("year");

    // 1) Map the genreName back to the enum constant
    Genre genre = Arrays.stream(Genre.values())
        .filter(g -> g.getGenreName().equalsIgnoreCase(rawGenre))
        .findFirst()
        .orElseThrow(() ->
            new SQLException("Unknown genre in DB: " + rawGenre)
        );

    // 2) Parse the format via your helper
    Format format = Format.fromString(rawFormat);

    // 3) Lookup owner
    User owner = userDao.findById(ownerId);

    // 4) Parse status string
    Status status = parseStatus(rawStatus);

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
        status
    );
  }

  private Status parseStatus(String rawStatus) throws SQLException {
    if (rawStatus == null || rawStatus.isBlank() || rawStatus.equalsIgnoreCase("AVAILABLE")) {
      return new Available();
    }

    String prefix = "Borrowed by ";
    if (rawStatus.startsWith(prefix)) {
      String borrowerName = rawStatus.substring(prefix.length());
      User borrower = userDao.findByUserName(borrowerName);
      if (borrower == null) {
        throw new SQLException("No such borrower username in status: " + borrowerName);
      }
      return new Borrowed(borrower);
    }

    // fallback
    return new Available();
  }

}
