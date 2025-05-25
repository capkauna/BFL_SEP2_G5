package Requests;

import java.io.Serializable;


//  Request object sent from client to server to add a user to the waiting list for a specific book.
// Contains only userId and bookId as input parameters for the operation.
// Used with object streams in client-server communication.

public class AddToWaitingListRequest implements Serializable {
  private final int bookId;
  private final int userId;

  public AddToWaitingListRequest(int bookId, int userId) {
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
