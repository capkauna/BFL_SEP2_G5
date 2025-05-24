package Server.model;

import java.io.Serializable;
import java.time.LocalDateTime;

//Represents a detailed entry in the waiting list, containing full user and book objects.
 //This class is used in the application logic, including user interface updates and DTO generation.
  //It reflects a meaningful real-world entry with display-ready information.


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