package Shared.dto;

import java.io.Serializable;

public class Request implements Serializable {
  private String command;
  private String username;
  private String password;

  public Request(String command, String username, String password) {
    this.command = command;
    this.username = username;
    this.password = password;
  }

  public String getCommand() { return command; }
  public String getUsername() { return username; }
  public String getPassword() { return password; }
}

