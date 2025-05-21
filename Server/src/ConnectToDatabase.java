import repository.JdbcUserDAO;
import util.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDatabase {


  public static void main(String[] args) {

    String createTableSQL = """
    CREATE TABLE IF NOT EXISTS books (
                            book_id SERIAL PRIMARY KEY,
                            title VARCHAR(300) NOT NULL,
                            author VARCHAR(300),
                            genre VARCHAR(100),
                            isbn VARCHAR(20),
                            format VARCHAR(50),
                            description TEXT,
                            image TEXT,
                            owner VARCHAR(300),
                            status VARCHAR(50),
                            year INTEGER
                        );
        
                        CREATE TABLE IF NOT EXISTS users (
                            user_id SERIAL PRIMARY KEY,
                            username VARCHAR(50) UNIQUE NOT NULL,
                            full_name VARCHAR(100),
                            email VARCHAR(100) UNIQUE NOT NULL,
                            hashed_pw TEXT NOT NULL,
                            phone_number VARCHAR(20),
                            address TEXT,
                            avatar TEXT
                        );
        
                        CREATE TABLE IF NOT EXISTS lends (
                            lend_id SERIAL PRIMARY KEY,
                            user_id INTEGER REFERENCES users(user_id),
                            book_id INTEGER REFERENCES books(book_id),
                            borrower_id INTEGER REFERENCES users(user_id),
                            start_date DATE NOT NULL,
                            end_date DATE
                        );
        
                        CREATE TABLE IF NOT EXISTS history_log (
                            book_id INTEGER REFERENCES books(book_id),
                            note TEXT NOT NULL,
                            added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        );
        
                        CREATE TABLE IF NOT EXISTS notification_log (
                            notification_id SERIAL PRIMARY KEY,
                            user_id INTEGER REFERENCES users(user_id),
                            message TEXT NOT NULL,
                            notification_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            book_id INTEGER REFERENCES books(book_id)
                        );
        
                        CREATE TABLE IF NOT EXISTS waiting_list (
                            entry_id SERIAL PRIMARY KEY,
                            book_id INTEGER REFERENCES books(book_id),
                            user_id INTEGER REFERENCES users(user_id),
                            added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        );
        
                        CREATE TABLE IF NOT EXISTS notes (
                            note_id SERIAL PRIMARY KEY,
                            book_id INTEGER REFERENCES books(book_id),
                            content TEXT NOT NULL,
                            added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        );
        
                        CREATE TABLE IF NOT EXISTS "read" (
                            record_id SERIAL PRIMARY KEY,
                            user_id INTEGER REFERENCES users(user_id),
                            book_id INTEGER REFERENCES books(book_id),
                            comment TEXT
                        );
""";
    try {

      Class.forName("org.postgresql.Driver");


      try (Connection connection = DBConnection.getConnection()) {
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
