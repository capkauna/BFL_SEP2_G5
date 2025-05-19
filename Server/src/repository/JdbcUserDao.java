package repository;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserDAO implements UserDAO
{
  private final Connection connection;

  public JdbcUserDAO(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void save(User u) {
    String sql = "INSERT INTO users (username, hashed_pw, full_name, email) VALUES (?, ?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, u.getUserName());
      stmt.setString(2, u.getPasswordHash());
      stmt.setString(3, u.getFullName());
      stmt.setString(4, u.getEmail());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      // Можешь выбросить или логировать по-нормальному, в зависимости от архитектуры
    }
  }
  @Override
  public Optional<User> findByUserName(String username) {
    String sql = "SELECT * FROM users WHERE username = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        User user = User.fromDb(
            rs.getInt("user_id"),
            rs.getString("username"),
            rs.getString("full_name"),
            rs.getString("email"),
            rs.getString("hashed_pw"),
            null, // phoneNumber – нет в БД
            null, // address – нет в БД
            null  // avatarPath – нет в БД
        );
        return Optional.of(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }


}