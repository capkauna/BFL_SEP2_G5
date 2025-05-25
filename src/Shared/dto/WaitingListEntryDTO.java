package Shared.dto;


import java.io.Serializable;
import java.time.LocalDateTime;

//A simplified data transfer object (DTO) for transferring waiting list information
//from the server to the client. Contains only the user's username and the date they were added.
 // Used for UI display and network communication to avoid sending full domain models.


public class WaitingListEntryDTO implements Serializable {
  private final int bookId;
  private final String username;
  private final LocalDateTime addedAt;


public WaitingListEntryDTO(int bookId, String username, LocalDateTime addedAt) {
    this.bookId = bookId;
    this.username = username;
    this.addedAt = addedAt;
  }


  public String getUsername() {
    return username;
  }
  public int getBookId() {
    return bookId;
  }
  public LocalDateTime getAddedAt() {
    return addedAt;
  }
}