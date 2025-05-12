package repository;

import repository.UserDAO;
import model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class JdbcUserDao implements UserDAO
{

  @Override public Optional<User> findByUserName(String username)
  {
    return Optional.empty();
  }

  @Override public void save(User u)
  {

  }
}