package Server.service;

import Server.database.*;
import Server.model.User;
import Shared.dto.FullUserDTO;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
  private final JdbcUserDAO dao;
  private FullUserDTO authenticatedUser;
  public AuthService() throws SQLException {
    dao = JdbcUserDAO.getInstance();
  }
  public Optional<User> authenticate(String u, String p) throws SQLException {
    Optional <User> opt = dao.findByUserNameOpt(u);
    if (opt.isPresent() && opt.get().validatePassword(p))
      return opt;
    return Optional.empty();
  }

  private Optional<User> getUserByUsername(String username) throws SQLException {
    return JdbcUserDAO.getInstance().findByUserNameOpt(username);
  }

  public void setAuthenticatedUser(String username) throws SQLException {
    Optional<User> userOpt = getUserByUsername(username);
    if (userOpt.isPresent()) {
      authenticatedUser = new FullUserDTO(
        userOpt.get().getUserId(),
        userOpt.get().getUserName(),
        userOpt.get().getFullName(),
        userOpt.get().getEmail(),
        userOpt.get().getPhoneNumber(),
        userOpt.get().getAddress(),
        userOpt.get().getAvatar()
      );

    } else {
      throw new SQLException("User not found");
    }
  }
  public FullUserDTO getAuthenticatedUser() {
    return authenticatedUser;
  }
}