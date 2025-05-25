//package Server;
//
//import Server.model.User;
//import Server.service.AuthService;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.sql.SQLException;
//import java.util.Optional;
//
//public class ServerMain
//{
//  private static final int PORT = 6789;
//
//  public static void main(String[] args) throws IOException, SQLException
//  {
//    AuthService auth = new AuthService();
//    try (ServerSocket server = new ServerSocket(PORT)) {
//      System.out.println("AuthServer listening on port " + PORT);
//      while (true) {
//        Socket sock = server.accept();
//        new Thread(() -> handleClient(sock, auth)).start();
//      }
//    }
//  }
//
//  private static void handleClient(Socket sock, AuthService auth) {
//    try (
//        BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))
//    ) {
//      // --- 1) Read one line: "username:password"
//      String line = in.readLine();
//      if (line == null) return;
//      String[] parts = line.split(":", 2);
//      String username = parts[0];
//      String password = parts.length > 1 ? parts[1] : "";
//
//      // --- 2) Authenticate via AuthService
//      Optional<User> user = auth.authenticate(username, password);
//
//      // --- 3) Respond "OK:username" or "FAIL"
//      String resp = user.map(value -> "OK:" + value.getUserName())
//          .orElse("FAIL");
//      out.write(resp);
//      out.newLine();
//      out.flush();
//    } catch (Exception e) {
//      e.printStackTrace();
//    } finally {
//      try { sock.close(); } catch (IOException ignored) {}
//    }
//  }
//}
