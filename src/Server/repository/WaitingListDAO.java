package Server.repository;

import Server.model.WaitingListEntry;
import Server.model.WaitingListRecord;

import java.sql.SQLException;
import java.util.List;

public interface WaitingListDAO
{
  // CRUD operations create
  List<WaitingListEntry> addEntry(int userId, int bookId) throws SQLException;
  // Read
  List<WaitingListEntry> removeEntry(int userId, int bookId)throws SQLException;
  List<WaitingListEntry> removeEntryById(int entryId)throws SQLException;
  List<WaitingListEntry> exists(int userId, int bookId)throws SQLException;
  // Read
  List<WaitingListEntry> getByBookId(int bookId)throws SQLException;
  List<WaitingListRecord> findAll()throws SQLException;// for debug
}
