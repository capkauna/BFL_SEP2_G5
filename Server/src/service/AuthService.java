package service;

import repository.UserDAO;
import model.User;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
  private final UserDAO userDao;

  public AuthService(UserDAO userDao) {
    this.userDao = userDao;
  }

  /** Returns the authenticated model.User, or empty if credentials are invalid */
  public Optional<User> login(String username, String rawPassword)
      throws SQLException
  {
    return userDao.findByUserName(username)
        .filter(u -> u.validatePassword(rawPassword));
  }
}