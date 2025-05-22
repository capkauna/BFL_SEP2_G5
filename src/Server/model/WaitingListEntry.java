package Server.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class WaitingListEntry implements Serializable {
  private int entryId;
  private User user;
  private LocalDateTime addedAt;
  private Book book;

public WaitingListEntry(int entryId, User user, Book book, LocalDateTime addedAt) {
    this.entryId = entryId;
    this.user = user;
    this.addedAt = addedAt;
    this.book = book;
  }

  public int getEntryId() {
    return entryId;
  }

  public User getUser() {
    return user;
  }

  public LocalDateTime getAddedAt() {
    return addedAt;
  }

  public Book getBook() {
    return book;
  }
}