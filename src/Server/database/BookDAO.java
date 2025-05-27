  package Server.database;

  import Shared.dto.enums.Format;
  import Shared.dto.enums.Genre;
  import Server.model.*;
  import Server.model.status.Status;

  import java.sql.SQLException;
  import java.util.ArrayList;

  public interface BookDAO
  {

    //Create
    Book create (Book book) throws
        SQLException;

    //Read
    Book findById(int id) throws SQLException;
    ArrayList<Book> findAll() throws SQLException;
    ArrayList<Book> findMyBooks(int userId) throws SQLException;
    ArrayList<Book> findByTitle(String title) throws SQLException;
    ArrayList<Book> findByIsbn(String isbn)throws SQLException;
    ArrayList<Book> findByAuthor(String author)throws SQLException;
    ArrayList<Book> findByGenre(Genre genre)throws SQLException;
    ArrayList<Book>findByFormat(Format format)throws SQLException;
    ArrayList<Book> findByOwner(User owner)throws SQLException;
    ArrayList<Book>findByStatus(Status status)throws SQLException;
    ArrayList<Book> findByBorrowedBy(User borrowedBy)throws SQLException;


    //Update
    void save(Book b) throws SQLException;
    void update(Book b) throws SQLException;

    //Delete
    void delete(int id) throws SQLException;
  }
