package Shared.Requests;

import java.io.Serializable;

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
