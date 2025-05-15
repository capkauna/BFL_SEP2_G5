package repository;

import dto.enums.Format;
import dto.enums.Genre;
import model.*;
import model.status.Status;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookDAO
{

  //Create
  Book create (String title, String author, Genre genre, String isbn, Format format, String description, String imagePath, User owner) throws
      SQLException;

  //Read
  BookSummary findById(int id) throws SQLException;
  List<BookSummary> findAll() throws SQLException;
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
}
