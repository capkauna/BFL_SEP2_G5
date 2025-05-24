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
  public User authenticate(String u, String p) throws SQLException {
    User opt = dao.findByUserName(u);
    if (opt.getUserName().equals(u) && opt.validatePassword(p))
      return opt;
    return null;
  }

  public User getUserByUsername(String username) throws SQLException {
    return JdbcUserDAO.getInstance().findByUserName(username);
  }
}