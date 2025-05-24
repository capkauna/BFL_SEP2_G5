// Server/network/LoginRequestHandler.java
package Server.network;
import Server.service.AuthService;
import java.util.Optional;
import Server.model.User;

public class LoginRequestHandler implements RequestHandler<LoginRequest> {
  @Override
  public LoginResponse handle(LoginRequest req, AuthService auth) throws Exception {
    User u = auth.authenticate(req.username, req.password);
    return new LoginResponse(u.getUserName().equals(req.username), req.username);
  }
}