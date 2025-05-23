package Client.network;

import java.io.*;
import java.net.Socket;

public class SocketAuthServiceClient implements AuthServiceClient {
  private static final String HOST = "localhost";
  private static final int PORT = 6789;

  @Override
  public boolean login(String username, String password) throws IOException {
    try (
        Socket sock = new Socket(HOST, PORT);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        BufferedReader in  = new BufferedReader(new InputStreamReader(sock.getInputStream()))
    ) {
      // 1) send "username:password"
      out.write(username + ":" + password);
      out.newLine();
      out.flush();

      // 2) read single-line response
      String resp = in.readLine();
      return resp != null && resp.startsWith("OK:");
    }
  }
}