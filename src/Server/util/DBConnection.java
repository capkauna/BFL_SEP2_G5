package Server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
  //port for everyone but Mara commented out for saving purposes
  //private static final String URL      = "jdbc:postgresql://localhost:5432/bestfriendlibrary" + "?currentSchema=library";
  private static final String URL      = "jdbc:postgresql://localhost:5432/bestfriendlibrary" + "?currentSchema=library";
  private static final String USER     = "postgres";
  private static final String PASSWORD = "amyclaw";

  static {
    try {
      // ensure the driver is registered
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new ExceptionInInitializerError("PostgreSQL JDBC Driver not found");
    }
  }


  //This can be extended later to hand back pooled Connections.

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

}
