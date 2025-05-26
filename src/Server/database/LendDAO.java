package Server.database;

import Server.model.Lend;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LendDAO {


Lend create(Lend lend) throws SQLException;



  Lend findById(int id) throws SQLException;
  ArrayList<Lend> findAll() throws SQLException;
  ArrayList<Lend> findByBookId(int bookId) throws SQLException;
  ArrayList<Lend> findByLenderId(int lenderId) throws SQLException;
  ArrayList<Lend> findByBorrowerId(int borrowerId) throws SQLException;
  ArrayList<Lend> findActiveLends() throws SQLException;
  ArrayList<Lend> findReturnedLends() throws SQLException;

  void markAsReturned(int lendId, int activeUserId) throws SQLException;
  void update(Lend lend) throws SQLException;


  void delete(int lendId) throws SQLException;
}
