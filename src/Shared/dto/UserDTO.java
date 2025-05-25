package Shared.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
  private String username;
  private String displayName;
  private int userId;

  public UserDTO(String username, String displayName, int userId) {
    this.username = username;
    this.displayName = displayName;
    this.userId = userId;
  }

  public String getUsername() { return username; }
  public String getDisplayName() { return displayName; }
  public int getUserId() { return userId; }
}