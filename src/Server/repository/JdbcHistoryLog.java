package Server.repository;

import Server.repository.HistoryLog;
import Server.repository.HistoryLogDAO;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class JdbcHistoryLogDAO implements HistoryLogDAO
{
  private final Connection connection;

  public JdbcHistoryLogDAO(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void addLog(int bookId, String note) throws SQLException {
    String sql = "INSERT INTO history_log (book_id, note) VALUES (?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, bookId);
      stmt.setString(2, note);
      stmt.executeUpdate();
    }
  }

  @Override
  public List<HistoryLog> findByBookId(int bookId) throws SQLException {
    String sql = "SELECT * FROM history_log WHERE book_id = ? ORDER BY added_at DESC";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, bookId);
      ResultSet rs = stmt.executeQuery();
      List<HistoryLog> logs = new ArrayList<>();
      while (rs.next()) {
        logs.add(mapRowToLog(rs));
      }
      return logs;
    }
  }

  @Override
  public List<HistoryLog> findAll() throws SQLException {
    String sql = "SELECT * FROM history_log ORDER BY added_at DESC";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      List<HistoryLog> logs = new ArrayList<>();
      while (rs.next()) {
        logs.add(mapRowToLog(rs));
      }
      return logs;
    }
  }

  private HistoryLog mapRowToLog(ResultSet rs) throws SQLException {
    return new HistoryLog(
        rs.getInt("book_id"),
        rs.getString("note"),
        rs.getTimestamp("added_at").toLocalDateTime()
    );
  }
}
