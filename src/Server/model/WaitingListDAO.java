package Server.model;

import java.time.LocalDateTime;

public class WaitingListDAO
{
  private int userId;
  private int bookId;
  private int entryId;
  private LocalDateTime addedAt;

  public WaitingListDAO(int userId, int bookId, int entryId, LocalDateTime addedAt)
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
