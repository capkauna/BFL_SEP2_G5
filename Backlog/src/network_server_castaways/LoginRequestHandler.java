package network_server_castaways;// Server/network/LoginRequestHandler.java
import Server.service.AuthService;
import java.util.Optional;
import Server.model.User;

public class LoginRequestHandler implements RequestHandler<Server.network.LoginRequest> {
  @Override
  public LoginResponse handle(Server.network.LoginRequest req, AuthService auth) throws Exception {
    Optional <User> u = auth.authenticate(req.username, req.password);
    return new LoginResponse(u.isPresent(), req.username);
  }
}