package repository;

import model.User;
import repository.UserDAO;
import repository.UserDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/bfl";
    String user = "postgres";
    String password = "AAA25";

    String createTableSQL = """
        CREATE TABLE IF NOT EXISTS users (
            user_id SERIAL PRIMARY KEY,
            username VARCHAR(50) UNIQUE NOT NULL,
            hashed_pw VARCHAR(255) NOT NULL,
            full_name VARCHAR(100),
            email VARCHAR(100) UNIQUE,
            created_at TIMESTAMP DEFAULT NOW(),
            is_active BOOLEAN DEFAULT TRUE,
            phone_number VARCHAR(15),
            address TEXT,
            avatar_path TEXT
        );
        """;

    try (Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement()) {

      stmt.execute(createTableSQL);
      System.out.println("✅ Таблица users создана или уже существует.");

      // После создания таблицы добавляем пользователя
      UserDAO userDAO = new UserDAOImpl(conn);

      User newUser = new User(
          "Kamir2020",
          "Kamir Mramor",
          "kamir@gmail.com",
          "1234",
          "4555555555",
          "Aarhus",
          "/avatars/kamir.png"
      );

      userDAO.save(newUser);
      System.out.println("✅ User is in db!");

    } catch (SQLException e) {
      System.err.println("❌ Ошибка при работе с БД: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
