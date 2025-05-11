package repository;

import repository.UserDao;
import model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class JdbcUserDao implements UserDao
{

  @Override public Optional<User> findByUserName(String username)
  {
    return Optional.empty();
  }

  @Override public void save(User u)
  {

  }
}