package Server;

import Server.network.ClientHandler;
import Server.service.AuthService;
import Server.service.ConnectionPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
  private static final int PORT = 1234;

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server started on port " + PORT);

      // Dependency injection: create shared services here
      AuthService authService = new AuthService();

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("New client connected: " + clientSocket);

        // Pass shared services to each ClientHandler instance
        ClientHandler handler = new ClientHandler(clientSocket, authService);
        new Thread(handler).start();
      }

    } catch (IOException | SQLException e) {
      System.err.println("Server error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
