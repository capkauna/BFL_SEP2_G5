package Server.repository;

import Server.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO
{
  User create (String userName, String name, String email, String rawPassword, String phoneNumber, String address) throws
      SQLException;
  UserSummary findById(int id) throws SQLException;
  List<User> findAll() throws SQLException;
  void update(User u) throws SQLException;
  void delete(int id) throws SQLException;

  Optional<User> findByUserName(String username);
  void save(User u);
  User create(User user1);
}
