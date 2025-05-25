package network_server_castaways;

public class LoginRequest implements Request {
  public final String username, password;
  public LoginRequest(String u, String p) { username=u; password=p; }
}
