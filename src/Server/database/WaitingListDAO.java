package Server.database;

import Server.model.WaitingListEntry;
import Server.model.WaitingListRecord;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ArrayList;

public interface WaitingListDAO
{
  // CRUD operations create
  ArrayList<WaitingListEntry> addEntry(int userId, int bookId) throws SQLException;
  // Read
  ArrayList<WaitingListEntry> removeEntry(int userId, int bookId)throws SQLException;
  ArrayList<WaitingListEntry> removeEntryById(int entryId)throws SQLException;
  ArrayList<WaitingListEntry> exists(int userId, int bookId)throws SQLException;
  // Read
  ArrayList<WaitingListEntry> getByBookId(int bookId)throws SQLException;
  ArrayList<WaitingListRecord> findAll()throws SQLException;// for debug
}
