import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDatabase {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String password = "AAA25";

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
}
