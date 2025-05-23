package Server.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class WaitingList implements Serializable {
  private User user;
  private Book book;
  private LocalDateTime requestDate;

  public WaitingList(User user, Book b) {
    this.user = user;
    this.book=b;
    this.requestDate = LocalDateTime.now(); // set automatically to the current date and time
  }

  public User getUser() {
    return user;
  }

  public LocalDateTime getRequestDate() {
    return requestDate;
  }

  @Override
  public String toString() {
    return user.getUserName() + " (added on: " + requestDate + ")";
  }
}