package Server.database;

import Server.model.actionmanagers.MarkAsRead;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MarkAsReadDAO {
  // Create
  MarkAsRead create(MarkAsRead entry) throws SQLException;

  // Read
  MarkAsRead findById(int id) throws SQLException;
  MarkAsRead findByUserAndBook(int userId, int bookId) throws SQLException;
  ArrayList<MarkAsRead> findByUser(int userId) throws SQLException;
  ArrayList<MarkAsRead> findAll() throws SQLException;

  // Update
  void update(MarkAsRead entry) throws SQLException;
  void save(MarkAsRead entry) throws SQLException; // upsert-style logic

  // Delete
  void delete(int id) throws SQLException;
}
