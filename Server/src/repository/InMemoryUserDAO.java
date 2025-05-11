package repository;

import repository.UserDAO;
import model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserDAO implements UserDAO
{
  private final Map<String, User> users = new ConcurrentHashMap<>();

  @Override
  public Optional<User> findByUserName(String username)
  {
    return Optional.ofNullable(users.get(username));
  }

  @Override
  public void save(User u)
  {
    if (users.containsKey(u.getUserName()))
    {
      throw new IllegalArgumentException("Username already taken");
    }
    users.put(u.getUserName(), u);
  }
}

