package repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO
{
  User create (User newUser) throws
      SQLException;
  User findById(int id) throws SQLException;
  List<User> findAll() throws SQLException;
  void update(User u) throws SQLException;
  void delete(int id) throws SQLException;

  Optional<User> findByUserName(String username) throws SQLException;
  void save(User u) throws SQLException;
}
