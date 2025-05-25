package Server.service;

import Server.database.*;
import Server.model.User;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
  private final JdbcUserDAO dao;
  public AuthService() throws SQLException {
    dao = JdbcUserDAO.getInstance();
  }
  public Optional<User> authenticate(String u, String p) throws SQLException {
    Optional <User> opt = dao.findByUserNameOpt(u);
    if (opt.isPresent() && opt.get().validatePassword(p))
      return opt;
    return Optional.empty();
  }

//  public Optional<User> getUserByUsername(String username) throws SQLException {
//    return JdbcUserDAO.getInstance().findByUserNameOpt(username);
//  }


}