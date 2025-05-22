package repository;

import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUserDAO implements UserDAO
{
  //private final Map<String, model.User> users = new ConcurrentHashMap<>();
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



//changed the create method to use the new model.User object and let most validation checks in the model.User class
  @Override public User create(User newUser) throws SQLException {
     String INSERT_SQL =
        "INSERT INTO users (username, full_name, email, hashed_pw, phone_number, address, avatar) " +
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


  @Override
  public User findById(int id) throws SQLException {
    String sql = "SELECT * FROM users WHERE user_id = ?";
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          User u = new User(
              rs.getString("username"),
              rs.getString("full_name"),
              rs.getString("email"),
              rs.getString("hashed_pw"),
              rs.getString("phone_number"),
              rs.getString("address"),
              rs.getString("avatar")    // if your constructor has an avatar arg
          );
          u.setUserId(rs.getInt("user_id"));   // ← set the ID
          return u;
        }
      }
    }
    return null;
  }


  @Override
  public List<User> findAll() throws SQLException {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM users";
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        User u = new User(
            rs.getString("username"),
            rs.getString("full_name"),
            rs.getString("email"),
            rs.getString("hashed_pw"),
            rs.getString("phone_number"),
            rs.getString("address"),
            rs.getString("avatar")
        );
        u.setUserId(rs.getInt("user_id"));  // ← set the ID
        users.add(u);
      }
    }
    return users;
  }


  @Override public void update(User u) throws SQLException
  {
    String sql = "UPDATE users SET username = ?, full_name = ?, email = ?, hashed_pw = ?, phone_number = ?, address = ? WHERE user_id = ?";
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql))
    {
      ps.setString(1, u.getUserName());
      ps.setString(2, u.getFullName());
      ps.setString(3, u.getEmail());
      ps.setString(4, u.getPasswordHash());
      ps.setString(5, u.getPhoneNumber());
      ps.setString(6, u.getAddress());
      ps.setInt(7, u.getUserId());
      ps.executeUpdate();
    }

  }

  @Override public void delete(int id) throws SQLException
  {
    try(Connection c = DBConnection.getConnection())
    {
      PreparedStatement s = c.prepareStatement(
          "DELETE FROM users WHERE user_id = ?");
      s.setInt(1, id);
      s.executeUpdate();
    }
  }

//TODO check this one again
@Override
public Optional<User> findByUserName(String username) throws SQLException {
  String sql = "SELECT * FROM users WHERE username = ?";
  try (Connection c = DBConnection.getConnection();
      PreparedStatement ps = c.prepareStatement(sql)) {
    ps.setString(1, username);
    try (ResultSet rs = ps.executeQuery()) {
      if (rs.next()) {
        User u = new User(
            rs.getString("username"),
            rs.getString("full_name"),
            rs.getString("email"),
            rs.getString("hashed_pw"),
            rs.getString("phone_number"),
            rs.getString("address"),
            rs.getString("avatar")
        );
        u.setUserId(rs.getInt("user_id"));  // ← set the ID
        return Optional.of(u);
      }
    }
  }
  return Optional.empty();
}


  @Override
  public void save(User u) throws SQLException
  {
    //this method is identical to update() but it's convenient to have it
    String sql = "UPDATE users SET username = ?, full_name = ?, email = ?, hashed_pw = ?, phone_number = ?, address = ? WHERE user_id = ?";
    try (Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql))
    {
      ps.setString(1, u.getUserName());
      ps.setString(2, u.getFullName());
      ps.setString(3, u.getEmail());
      ps.setString(4, u.getPasswordHash());
      ps.setString(5, u.getPhoneNumber());
      ps.setString(6, u.getAddress());
      ps.setInt(7, u.getUserId());
      ps.executeUpdate();
    }
  }

}

