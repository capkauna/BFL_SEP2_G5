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
  //constructor without entryId or addedAt for adding to waiting list
  public WaitingListEntry(User user, Book book) {
    this.user = user;
    this.book = book;
    this.addedAt = LocalDateTime.now();
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
  public void setEntryId(int entryId) {
    this.entryId = entryId;
  }

  public static WaitingListEntry addToWaitingList(Book b, User u)
  {
    return new WaitingListEntry(u, b);

    // Logic to add the entry to the waiting list
    // This could involve adding it to a database or an in-memory list
  }
}