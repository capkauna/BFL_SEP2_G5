package Server.repository;

import Server.model.actionmanagers.MarkAsRead;

import java.sql.SQLException;
import java.util.List;

public interface MarkAsReadDAO {
  // Create
  MarkAsRead create(MarkAsRead entry) throws SQLException;

  // Read
  MarkAsRead findById(int id) throws SQLException;
  MarkAsRead findByUserAndBook(int userId, int bookId) throws SQLException;
  List<MarkAsRead> findByUser(int userId) throws SQLException;
  List<MarkAsRead> findAll() throws SQLException;

  // Update
  void update(MarkAsRead entry) throws SQLException;
  void save(MarkAsRead entry) throws SQLException; // upsert-style logic

  // Delete
  void delete(int id) throws SQLException;
}
