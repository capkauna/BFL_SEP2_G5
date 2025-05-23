package repository;

import Shared.dto.enums.Format;
import dto.enums.Genre;
import Server.model.*;
import Server.model.status.Status;

import java.sql.SQLException;
import java.util.List;

import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;

public interface BookDAO
{

  //Create
  Server.model.Book create (String title, String author, Genre genre, String isbn, Format format, String description, String imagePath, User owner) throws
      SQLException;

  //Read
  BookSummary findById(int id) throws SQLException;
  List<Book> findAll() throws SQLException;
  List<BookSummary> findByTitle(String title) throws SQLException;
  List<BookSummary> findByIsbn(String isbn)throws SQLException;
  List<BookSummary> findByAuthor(String author)throws SQLException;
  List<BookSummary> findByGenre(Genre genre)throws SQLException;
  List<BookSummary>findByFormat(Format format)throws SQLException;
  List<BookSummary> findByOwner(User owner)throws SQLException;
  List<BookSummary>findByStatus(Status status)throws SQLException;
  List<BookSummary> findByBorrowedBy(User borrowedBy)throws SQLException;


  //Update
  void save(Book b);
  void update(Book b) throws SQLException;

  //Delete
  void delete(int id) throws SQLException;
  Book create(Book book1);
}
