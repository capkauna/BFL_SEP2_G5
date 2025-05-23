package Server.repository;

import java.sql.SQLException;
import java.util.List;

public interface HistoryLogDAO {
  void addLog(int bookId, String note) throws SQLException;

  List<HistoryLog> findByBookId(int bookId) throws SQLException;

  List<HistoryLog> findAll() throws SQLException;
}
