package Server.model;

import java.time.LocalDateTime;

 // Represents a raw database record from the waiting_list table.
 //Contains only basic identifiers (userId, bookId, entryId, and addedAt),
 // and is primarily used for internal mapping, logging, or administrative queries.
 // This class avoids expensive object resolution (e.g., User, Book) for performance.

public class WaitingListRecord
{
  private int userId;
  private int bookId;
  private int entryId;
  private LocalDateTime addedAt;

  public WaitingListRecord(int userId, int bookId, int entryId, LocalDateTime addedAt)
  {
    this.userId = userId;
    this.bookId = bookId;
    this.entryId = entryId;
    this.addedAt = addedAt;
  }
  public int getUserId()
  {
    return userId;
  }
  public int getBookId()
  {
    return bookId;
  }
  public int getEntryId()
  {
    return entryId;
  }
  public LocalDateTime getAddedAt()
  {
    return addedAt;
  }
}
