package Server.network;

public class RequestParser {
  public static Request parse(String line) {
    if (line.startsWith("INFO:")) {
      return new UserInfoRequest(line.substring(5));
    }
    // fallback: login
    String[] parts = line.split(":", 2);
    return new LoginRequest(parts[0], parts.length>1 ? parts[1] : "");
  }
}
