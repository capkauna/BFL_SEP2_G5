package Shared.dto;


import java.io.Serializable;
import java.time.LocalDateTime;

public class WaitingListEntryDTO implements Serializable {
  private final String username;
  private final LocalDateTime requestDate;

  public WaitingListEntryDTO(String username, LocalDateTime requestDate) {
    this.username = username;
    this.requestDate = requestDate;
  }

  // Getters
  public String getUsername() {
    return username;
  }

  public LocalDateTime getRequestDate() {
    return requestDate;
  }
}
