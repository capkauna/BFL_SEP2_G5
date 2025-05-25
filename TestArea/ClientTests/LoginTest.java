package ClientTests;

import Shared.dto.enums.Action;
import Shared.network.Request;
import Shared.network.Response;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class LoginTest {
//  public static void main(String[] args) {
//    try (
//        Socket socket = new Socket("localhost", 5555);
//        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//        Scanner scanner = new Scanner(System.in)
//    ) {
//      System.out.print("Username: ");
//      String username = scanner.nextLine();
//
//      System.out.print("Password: ");
//      String password = scanner.nextLine();
//
//      Request request = new Request(Action.LOGIN, );
//      out.writeObject(request);
//      out.flush();
//
//      Object obj = in.readObject();
//      if (obj instanceof Response response) {
//        System.out.println(response.getStatus() + ": " + response.getMessage());
//      }
//    } catch (Exception e) {
//      System.err.println("Client error: " + e.getMessage());
//    }
//  }
}

