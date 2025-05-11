package repository;

import model.User;

import java.util.Optional;

public interface UserDao
{
  Optional<User> findByUserName(String username);
  void save(User u);
}
