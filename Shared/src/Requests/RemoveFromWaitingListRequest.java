package Requests;

import java.io.Serializable;

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
