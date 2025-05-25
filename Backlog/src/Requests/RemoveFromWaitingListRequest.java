package Requests;

import java.io.Serializable;

// Request object sent from client to server to remove a user from the waiting list for a specific book.
// Contains userId and bookId to identify which entry to remove.
// Typically used when a user cancels their interest in a book.

public class RemoveFromWaitingListRequest implements Serializable {
  private final int bookId;
  private final int userId;

  public RemoveFromWaitingListRequest(int bookId, int userId) {
    this.bookId = bookId;
    this.userId = userId;
  }

  public int getBookId() {
    return bookId;
  }

  public int getUserId() {
    return userId;
  }
}
