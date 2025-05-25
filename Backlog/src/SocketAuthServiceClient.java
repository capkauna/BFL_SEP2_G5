//package Client.network;
//
//import Shared.dto.FullUserDTO;
//
//import javax.naming.AuthenticationException;
//import java.io.*;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//public class SocketAuthServiceClient implements AuthServiceClient {
//  private static final String HOST = "localhost";
//  private static final int PORT = 6789;
//
//  @Override
//    public boolean login(String username, String password) throws IOException {
//      try (
//          Socket sock = new Socket(HOST, PORT);
//          BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
//          BufferedReader in  = new BufferedReader(new InputStreamReader(sock.getInputStream()))
//      ) {
//        // 1) send "username:password"
//        out.write(username + ":" + password);
//        out.newLine();
//        out.flush();
//
//        // 2) read single-line response
//        String resp = in.readLine();
//        return resp != null && resp.startsWith("OK:");
//      }
//    }
//
//  @Override
//  public FullUserDTO getUserInfo(String username) throws AuthenticationException
//  {
//    try (
//        Socket sock = new Socket(HOST, PORT);
//        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
//        BufferedReader in  = new BufferedReader(new InputStreamReader(sock.getInputStream()))
//    ) {
//      // 1) send "username"
//      out.write(username);
//      out.newLine();
//      out.flush();
//
//      // 2) read single-line response
//      String resp = in.readLine();
//      if (resp != null && resp.startsWith("OK:")) {
//        String[] parts = resp.split(":", 7);
//        return new FullUserDTO(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
//      } else {
//        return null;
//      }
//    }
//    catch (IOException e)
//    {
//      throw new RuntimeException(e);
//    }
//  }
//}