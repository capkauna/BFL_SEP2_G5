package network_server_castaways;

public class UserInfoRequest implements Request {
  public final String username;
  public UserInfoRequest(String u) { username=u; }
}
