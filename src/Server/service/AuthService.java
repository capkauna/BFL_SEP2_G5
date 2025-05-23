package Server.service;

import Server.repository.*;
import Server.model.User;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
  private final JdbcUserDAO dao;
  public AuthService() throws SQLException {
    dao = JdbcUserDAO.getInstance();
  }
  public Optional<User> authenticate(String u, String p) throws SQLException {
    Optional<User> opt = dao.findByUserName(u);
    if (opt.isPresent() && opt.get().validatePassword(p))
      return opt;
    return Optional.empty();
  }

  public Optional<User> getUserByUsername(String username) throws SQLException {
    return JdbcUserDAO.getInstance().findByUserName(username);
  }
}