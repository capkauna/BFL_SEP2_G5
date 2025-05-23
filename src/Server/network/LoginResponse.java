package Server.network;

public class LoginResponse implements Response {
  public final boolean success;
  public final String username; // only set if success
  public LoginResponse(boolean success, String username) {
    this.success = success;
    this.username = username;
  }
}
