package repository;

import model.User;
import repository.UserDAO;
import repository.UserDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/bfl";
    String user = "postgres";
    String password = "AAA25";

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
      UserDAO userDAO = new UserDAOImpl(conn);

      User newUser = new User(
          "kira1998",
          "Kira Mramor",
          "kira@gmail.com",
          "1234",
          "4555555555",
          "Aarhus",
          "/avatars/kira.png"
      );

      userDAO.save(newUser);
      System.out.println("User is in db!");
    } catch (SQLException e) {
      System.err.println("Error connection to db: " + e.getMessage());
    }
  }
}
