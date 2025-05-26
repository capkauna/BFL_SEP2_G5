package Server.database;

import Server.model.Lend;

import java.sql.SQLException;
import java.util.List;

public interface LendDAO {


Lend create(Lend lend) throws SQLException;
  Lend createFull(Lend lend) throws SQLException;


  Lend findById(int id) throws SQLException;
  List<Lend> findAll() throws SQLException;
  List<Lend> findByBookId(int bookId) throws SQLException;
  List<Lend> findByLenderId(int lenderId) throws SQLException;
  List<Lend> findByBorrowerId(int borrowerId) throws SQLException;
  List<Lend> findActiveLends() throws SQLException;
  List<Lend> findReturnedLends() throws SQLException;

  void markAsReturned(int lendId) throws SQLException;
  void update(Lend lend) throws SQLException;


  void delete(int lendId) throws SQLException;
}
