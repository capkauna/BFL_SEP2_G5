package service;

import repository.UserDao;
import model.User;


import java.util.Optional;

public class AuthService {
  private final UserDao userDao;

  public AuthService(UserDao userDao) {
    this.userDao = userDao;
  }

  /** Returns the authenticated User, or empty if credentials are invalid */
  public Optional<User> login(String username, String rawPassword) {
    return userDao.findByUserName(username)
        .filter(u -> u.validatePassword(rawPassword));
  }
}