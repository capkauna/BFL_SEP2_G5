package Server.repository;

import Server.model.WaitingListDAO;
import Server.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WaitingListRepository
{
  private static WaitingListRepository instance;

  private WaitingListRepository() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static WaitingListRepository getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new WaitingListRepository();
    }
    return instance;
  }

  //  @Override
  public List<WaitingListDAO> findAll() throws SQLException
  {
    String sqlQuery = """
        SELECT b.entry_id, b.book_id, b.user_id, b.added_at
          FROM waiting_list b
        """;
    List<WaitingListDAO> waitingList = new ArrayList<>();
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery())
    {
      while (rs.next())
      {
        waitingList.add(mapResultSetToBook(rs));
      }
    }
    return waitingList;
  }

  private WaitingListDAO mapResultSetToBook(ResultSet rs) throws SQLException
  {
    int entryId = rs.getInt("entry_id");
    int bookId = rs.getInt("book_id");
    int userId = rs.getInt("user_id");
    Timestamp addedAt = rs.getTimestamp("added_at");

    return new WaitingListDAO(entryId, bookId, userId,
        addedAt.toLocalDateTime());
  }
}
