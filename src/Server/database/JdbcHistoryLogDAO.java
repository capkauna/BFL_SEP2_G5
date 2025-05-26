package Server.database;

import Server.model.HistoryLog;
import Server.dbstart.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcHistoryLogDAO implements HistoryLogDAO
{
  private static JdbcHistoryLogDAO instance;
  private final Connection connection;

  public JdbcHistoryLogDAO(Connection connection) {
    this.connection = connection;
  }

  public static HistoryLogDAO getInstance()
  {
    if (instance == null) {
      try {
        Connection connection = DBConnection.getConnection();
        instance = new JdbcHistoryLogDAO(connection);
      } catch (SQLException e) {
        throw new RuntimeException("Failed to create JdbcHistoryLogDAO instance", e);
      }
    }
    return instance;
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


  @Override
  public void updateLog(int bookId, String oldNote, String newNote) throws SQLException {
    String sql = "UPDATE history_log SET note = ? WHERE book_id = ? AND note = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, newNote);
      stmt.setInt(2, bookId);
      stmt.setString(3, oldNote);
      stmt.executeUpdate();
    }
  }

  @Override
  public void deleteLog(int bookId, String note) throws SQLException {
    String sql = "DELETE FROM history_log WHERE book_id = ? AND note = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, bookId);
      stmt.setString(2, note);
      stmt.executeUpdate();
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
