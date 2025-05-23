package Server.repository;

import Server.model.WaitingListEntry;

import java.util.List;

public interface WaitingListDAO
{
  // CRUD operations create
  boolean addEntry(int userId, int copyId);
  // Read
  boolean removeEntry(int userId, int copyId);
  boolean removeEntryById(int entryId);
  boolean exists(int userId, int copyId);
  // Read
  List<WaitingListEntry> getByBookId(int bookId);
  List<WaitingListEntry> findAll();// for admin/debug
}
