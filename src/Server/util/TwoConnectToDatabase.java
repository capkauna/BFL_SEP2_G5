package Server.util;

import java.sql.*;

public class TwoConnectToDatabase
{


  public static void main(String[] args) {

    String createTableSQL = """
        
        
                   CREATE SCHEMA IF NOT EXISTS library;
        
                   SET SCHEMA 'library';
        
                   CREATE TABLE users (
                                          user_id SERIAL PRIMARY KEY ,
                                          username VARCHAR(50) UNIQUE NOT NULL ,
                                          full_name VARCHAR(100),
                                          email VARCHAR(100) UNIQUE NOT NULL ,
                                          hashed_pw TEXT NOT NULL ,
                                          phone_number VARCHAR(20),
                                          address TEXT,
                                          avatar TEXT
                   );
        
                   CREATE TABLE books(
                                        book_id SERIAL PRIMARY KEY ,
                                        title VARCHAR (300) NOT NULL ,
                                        author VARCHAR (300),
                                        genre VARCHAR (100),
                                        isbn VARCHAR (20),
                                        format VARCHAR (50),
                                        description TEXT ,
                                        image TEXT,
                                        owner_id INTEGER NOT NULL REFERENCES users(user_id),
                                        status VARCHAR(50),
                                        year INTEGER
                   );
        
        
                   CREATE TABLE lends (
                                          lend_id SERIAL PRIMARY KEY ,
                                          user_id INTEGER REFERENCES users(user_id),
                                          book_id INTEGER REFERENCES books(book_id),
                                          borrower_id INTEGER REFERENCES users(user_id),
                                          start_date DATE NOT NULL ,
                                          end_date DATE
                   );
        
                   CREATE TABLE history_log(
                                               book_id INTEGER REFERENCES books(book_id),
                                               note TEXT NOT NULL ,
                                               added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                   );
        
                   CREATE TABLE notification_log(
                                                    notification_id SERIAL PRIMARY KEY ,
                                                    user_id INTEGER REFERENCES users(user_id),
                                                    message TEXT NOT NULL ,
                                                    notification_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                    book_id INTEGER REFERENCES books(book_id)
                   );
        
                   CREATE TABLE waiting_list (
                                                 entry_id SERIAL PRIMARY KEY ,
                                                 book_id INTEGER REFERENCES books(book_id),
                                                 user_id INTEGER REFERENCES users(user_id),
                                                 added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                   );
        
                   CREATE TABLE notes (
                                          note_id SERIAL PRIMARY KEY ,
                                          book_id INTEGER references books(book_id),
                                          content TEXT NOT NULL ,
                                          added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                   );
        
                   CREATE TABLE read (
                                         record_id SERIAL PRIMARY KEY,
                                         user_id INTEGER REFERENCES users(user_id),
                                         book_id INTEGER REFERENCES books(book_id),
                                         is_read BOOLEAN NOT NULL DEFAULT FALSE,
                                         comment TEXT
                   );
""";
    try
    {

      Class.forName("org.postgresql.Driver");
    }catch (ClassNotFoundException e) {
        System.err.println("PostgreSQL driver not found");
        e.printStackTrace();
        return;
      }


    try (Connection conn = DBConnection.getConnection();
        Statement  stmt = conn.createStatement()) {

      // Split on semicolons, trimming whitespace
      for (String sql : createTableSQL.split(";")) {
        sql = sql.trim();
        if (sql.isEmpty()) continue;
        stmt.execute(sql);
      }

      System.out.println("Schema and tables created successfully.");
    } catch (SQLException e) {
        System.out.println("ERROR");
        e.printStackTrace();
      }
    }
  }



