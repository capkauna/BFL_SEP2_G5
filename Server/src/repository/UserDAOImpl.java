package repository;

import model.User;
import model.UserSummary;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
  private final Connection conn;

  public UserDAOImpl(Connection conn) {
    this.conn = conn;
  }

  @Override public User create(User newUser) throws SQLException
  {
    return null;
  }

  @Override public UserSummary findById(int id) throws SQLException
  {
    return null;
  }

  @Override public List<User> findAll() throws SQLException
  {
    return List.of();
  }

  @Override public void update(User u) throws SQLException
  {

  }

  @Override public void delete(int id) throws SQLException
  {

  }

  @Override
  public Optional<User> findByUserName(String username) {
    String sql = "SELECT * FROM users WHERE username = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        User user = User.fromDb(
            rs.getInt("user_id"),
            rs.getString("username"),
            rs.getString("full_name"),
            rs.getString("email"),
            rs.getString("hashed_pw"),
            rs.getString("phone_number"),
            rs.getString("address"),
            rs.getString("avatar_path")
        );
        return Optional.of(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  @Override
  public void save(User user) {
    String sql = """
            INSERT INTO users\s
            (username, hashed_pw, full_name, email, phone_number, address, avatar_path)
            VALUES (?, ?, ?, ?, ?, ?, ?)
           \s""";

    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, user.getUserName());
      stmt.setString(2, user.getPasswordHash());
      stmt.setString(3, user.getFullName());
      stmt.setString(4, user.getEmail());
      stmt.setString(5, user.getPhoneNumber());
      stmt.setString(6, user.getAddress());
      stmt.setString(7, user.getUserAvatarPath());

      stmt.executeUpdate();

      ResultSet keys = stmt.getGeneratedKeys();
      if (keys.next()) {
        int idFromDb = keys.getInt(1);
        System.out.println("User saved with ID: " + idFromDb);
          }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
