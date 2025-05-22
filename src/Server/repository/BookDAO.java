package Server.repository;

import dto.enums.Format;
import dto.enums.Genre;
import Server.model.*;
import Server.model.status.Status;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO
{

  //Create
  Book create (Book newBook) throws
      SQLException;

  //Read
  Book findById(int id) throws SQLException;
  List<Book> findAll() throws SQLException;
  List<Book> findByTitle(String title) throws SQLException;
  List<Book> findByIsbn(String isbn)throws SQLException;
  List<Book> findByAuthor(String author)throws SQLException;
  List<Book> findByGenre(Genre genre)throws SQLException;
  List<Book>findByFormat(Format format)throws SQLException;
  List<Book> findByOwner(User owner)throws SQLException;
  List<Book>findByStatus(Status status)throws SQLException;
  List<Book> findByBorrowedBy(User borrowedBy)throws SQLException;


  //Update
  void save(Book b ) throws SQLException;
  void update(Book b) throws SQLException;

  //Delete
  void delete(int id) throws SQLException;
}
