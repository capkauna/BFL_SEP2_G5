//package Server.network;
//
//import Shared.dto.Request;
//import Shared.dto.Response;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.HashMap;
//
//public class LoginServer
//{
//  private static final int PORT = 5555;
//
//  // You can replace this with file loading later
//  private static final HashMap<String, String> users = new HashMap<>();
//
//  static {
//    users.put("mara", "1234");
//    users.put("karina", "abcd");
//  }
//
//  public static void main(String[] args) {
//    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//      System.out.println("Server running on port " + PORT);
//
//      while (true) {
//        Socket clientSocket = serverSocket.accept();
//        new Thread(() -> handleClient(clientSocket)).start();
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  private static void handleClient(Socket socket) {
//    try (
//        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
//    ) {
//      Object obj = in.readObject();
//      if (obj instanceof Request request) {
//        if ("login".equals(request.getCommand())) {
//          String username = request.getUsername();
//          String password = request.getPassword();
//
//          String expected = users.get(username);
//          if (expected != null && expected.equals(password)) {
//            out.writeObject(new Response("success", "Welcome " + username + "!"));
//          } else {
//            out.writeObject(new Response("error", "Invalid username or password."));
//          }
//          out.flush();
//        }
//      }
//    } catch (Exception e) {
//      System.err.println("Error handling client: " + e.getMessage());
//    }
//  }
//}
//
