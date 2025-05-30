package Server.model;

import java.time.LocalDateTime;

public class HistoryLog {
  private int bookId;
  private String note;
  private LocalDateTime addedAt;

  public HistoryLog(int bookId, String note, LocalDateTime addedAt) {
    this.bookId = bookId;
    this.note = note;
    this.addedAt = addedAt;
  }

  public int getBookId() {
    return bookId;
  }

  public String getNote() {
    return note;
  }

  public LocalDateTime getAddedAt() {
    return addedAt;
  }
}
