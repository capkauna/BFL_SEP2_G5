package Server.database;

import Server.model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface UserDAO
{
  User create (User newUser) throws
      SQLException;
  User findById(int id) throws SQLException;
  ArrayList<User> findAll() throws SQLException;
  void update(User u) throws SQLException;
  void delete(int id) throws SQLException;

  User findByUserName(String username) throws SQLException;

  Optional<User> findByUserNameOpt(String username) throws SQLException;
  void save(User u) throws SQLException;
}
