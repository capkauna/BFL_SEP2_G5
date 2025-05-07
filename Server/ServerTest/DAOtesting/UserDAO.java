package DAOtesting;

import model.User;

import java.util.Optional;

public interface UserDAO
{
  Optional<User> findByUserName(String username);
  void save(User u);
}
