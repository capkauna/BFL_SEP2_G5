package repository;

import model.*;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcUserDAO implements UserDAO
{
  private final Map<String, User> users = new ConcurrentHashMap<>();
  private static JdbcUserDAO instance; // Singleton instance, might opt out, not sure yet


  JdbcUserDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static JdbcUserDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new JdbcUserDAO();
    }
    return instance;
  }

//replaced with DBConnection
//  private static Connection getConnection() throws SQLException
//  {
//    return DriverManager.getConnection(
//        "jdbc:postgresql://localhost:5432/postgres/bfl", "postgres", "password");
//  }



//changed the create method to use the new User object and let most validation checks in the User class
  @Override public User create(User newUser) throws SQLException {
     String INSERT_SQL =
        "INSERT INTO users (username, fullname, email, password_hash, phone, address, avatar_path) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(
            INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, newUser.getUserName());
      ps.setString(2, newUser.getFullName());
      ps.setString(3, newUser.getEmail());
      ps.setString(4, newUser.getPasswordHash());
      ps.setString(5, newUser.getPhoneNumber());
      ps.setString(6, newUser.getAddress());
      ps.setString(7, newUser.getUserAvatarPath());
      ps.executeUpdate();

      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) {
          int generatedId = keys.getInt(1);
          newUser.setUserId(generatedId);
        } else {
          throw new SQLException("No ID returned on insert");
        }
      }
      return newUser;
    }
  }

//   public User create(String userName, String name, String email,
//      String rawPassword, String phoneNumber, String address)
//      throws SQLException
//  {
//    try(Connection c = DBConnection.getConnection())
//    {
//      PreparedStatement statement = c.prepareStatement(
//          "INSERT INTO users (userName, name, email, rawPassword, phoneNumber, address) VALUES (?, ?, ?, ?, ?, ?)");
//      statement.setString(1, userName);
//      statement.setString(2, name);
//      statement.setString(3, email);
//      statement.setString(4, rawPassword);
//      statement.setString(5, phoneNumber);
//      statement.setString(6, address);
//      statement.executeUpdate();
//      return new User(userName, name, email, rawPassword, phoneNumber,
//          address);
//    }
//  }

  @Override public UserSummary findById(int id) throws SQLException
  {
    try(Connection c = DBConnection.getConnection())
    {
      PreparedStatement s = c.prepareStatement(
          "SELECT userName, name, address FROM users WHERE id = ?");
      s.setInt(1, id);
      var rs = s.executeQuery();
      if (rs.next())
      {
        String userName = rs.getString("userName");
        return new UserSummary(rs.getString("userName"),
            rs.getString("name"),
            rs.getString("address"));
      }
      else
      {
        return null;
      }
    }
  }

  @Override public List<User> findAll() throws SQLException
  {
    try (Connection c = DBConnection.getConnection())
    {
      PreparedStatement s = c.prepareStatement(
          "SELECT * FROM users");
      ResultSet rs = s.executeQuery();
      List<User> users = new ArrayList<>();
      while (rs.next())
      {
        String userName = rs.getString("userName");
        users.add(new User(rs.getString("userName"),
            rs.getString("name"), rs.getString("email"),
            rs.getString("rawPassword"), rs.getString("phoneNumber"),
            rs.getString("address")));
      }
      return users;
    }
  }

  @Override public void update(User u) throws SQLException
  {

  }

  @Override public void delete(int id) throws SQLException
  {

  }

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

