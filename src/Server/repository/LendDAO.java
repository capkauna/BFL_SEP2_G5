package Server.repository;

import Server.model.Lend;

import java.sql.SQLException;
import java.util.List;

public interface LendDAO {

  // CREATE
  Lend create(int bookId, int lenderId, int borrowerId) throws SQLException;

  // READ
  Lend findById(int id) throws SQLException;
  List<Lend> findAll() throws SQLException;
  List<Lend> findByBookId(int bookId) throws SQLException;
  List<Lend> findByLenderId(int lenderId) throws SQLException;
  List<Lend> findByBorrowerId(int borrowerId) throws SQLException;
  List<Lend> findActiveLends() throws SQLException;
  List<Lend> findReturnedLends() throws SQLException;

  // UPDATE
  void markAsReturned(int lendId) throws SQLException;
  void update(Lend lend) throws SQLException;

  // DELETE
  void delete(int lendId) throws SQLException;
}
