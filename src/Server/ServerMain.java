package Server;

import Server.model.User;
import Server.network.*;
import Server.service.AuthService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Optional;

public class ServerMain
{
  private static final int PORT = 6789;

  public static void main(String[] args) throws IOException, SQLException
  {
    AuthService auth = new AuthService();
    RequestDispatcher dispatcher = new RequestDispatcher(auth);

    try (ServerSocket server = new ServerSocket(PORT)) {
      System.out.println("AuthServer listening on port " + PORT);
      while (true) {
        Socket sock = server.accept();
        new Thread(() -> handleClient(sock, dispatcher)).start();
      }
    }
  }

  private static void handleClient(Socket sock, RequestDispatcher dispatcher) {
    try (
        BufferedReader in  = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))
    ) {
      String line = in.readLine();
      if (line == null) return;

      Request req = RequestParser.parse(line);
      Response resp = dispatcher.dispatch(req);
      String respLine = ResponseFormatter.format(resp);

      out.write(respLine);
      out.newLine();
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { sock.close(); } catch (IOException ignored) {}
    }
  }
}
