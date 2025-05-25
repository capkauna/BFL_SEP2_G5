package Shared.dto;


import java.io.Serializable;
import java.time.LocalDateTime;

//A simplified data transfer object (DTO) for transferring waiting list information
//from the server to the client. Contains only the user's username and the date they were added.
 // Used for UI display and network communication to avoid sending full domain models.


public class WaitingListEntryDTO implements Serializable {
  private final int bookId;
  private final String bookTitle;
  private final int userId;
  private final String username;
  private final LocalDateTime addedAt;

  public WaitingListEntryDTO(int bookId, String bookTitle, int userId, String username, LocalDateTime addedAt) {
    this.bookId = bookId;
    this.bookTitle = bookTitle;
    this.userId = userId;
    this.username = username;
    this.addedAt = addedAt;
  }

  public int getBookId() {
    return bookId;
  }

  public String getBookTitle() {
    return bookTitle;
  }

  public int getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public LocalDateTime getAddedAt() {
    return addedAt;
  }
}
