package Shared.dto;


import java.io.Serializable;
import java.time.LocalDateTime;

public class WaitingListEntryDTO implements Serializable {
  private final String username;
  private final LocalDateTime addedAt;

  public WaitingListEntryDTO(String username, LocalDateTime addedAt) {
    this.username = username;
    this.addedAt = addedAt;
  }

  public String getUsername() {
    return username;
  }

  public LocalDateTime getAddedAt() {
    return addedAt;
  }
}