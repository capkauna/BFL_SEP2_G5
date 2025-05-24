package Server.repository;

import Server.model.HistoryLog;

import java.sql.SQLException;
import java.util.List;

public interface HistoryLogDAO {
  void addLog(int bookId, String note) throws SQLException;
  List<HistoryLog> findByBookId(int bookId) throws SQLException;
  List<HistoryLog> findAll() throws SQLException;
  void updateLog(int bookId, String oldNote, String newNote) throws SQLException;
  void deleteLog(int bookId, String note) throws SQLException;
}
