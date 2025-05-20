import repository.JdbcUserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDatabase {


  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/bfl";
    String user = "postgres";
    String password = "amyclaw";

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
    try {

      Class.forName("org.postgresql.Driver");


      try (Connection connection = DriverManager.getConnection(url, user, password)) {
        System.out.println("SUCCESS");
      } catch (SQLException e) {
        System.out.println("ERROR");
        e.printStackTrace();
      }
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL not found:");
      e.printStackTrace();
    }
  }

  //public JdbcUserDAO.create("username1", "John Doe", "john@doe.com", "1524", "1234567890", "123 Main St");
}
