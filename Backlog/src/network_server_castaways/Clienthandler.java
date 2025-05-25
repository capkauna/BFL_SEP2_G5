//package network_server_castaways;
//import Server.transfer.Request;
//import Server.transfer.Response;
//
//import java.io.*;
//import java.net.Socket;

//public class Clienthandler implements Runnable {
//  private Socket socket;
//  private ObjectInputStream in;
//  private ObjectOutputStream out;
//
//  public Clienthandler(Socket socket) {
//    this.socket = socket;
//    try {
//      out = new ObjectOutputStream(socket.getOutputStream());
//      in = new ObjectInputStream(socket.getInputStream());
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  public void send(Response response) {
//    try {
//      out.writeObject(response);
//      out.flush();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  @Override
//  public void run() {
//    try {
//      while (true) {
//        Request request = (Request) in.readObject();
//        handleRequest(request);
//      }
//    } catch (IOException | ClassNotFoundException e) {
//      System.out.println("Client disconnected.");
//    }
//  }
//
//  private void handleRequest(Request request) {
//    switch (request.getAction()) {
//      case "LOGIN":
//        String username = (String) request.getData("username");
//        String password = (String) request.getData("password");
//        boolean success = loginFromDatabase(username, password);
//        Response response = new Response(success, null, success ? "Login successful" : "Login failed");
//        send(response);
//        break;
//      // handle other cases here...
//    }
//  }
//
//  private boolean loginFromDatabase(String username, String password) {
//    // You would check your real database here
//    return "user".equals(username) && "pass".equals(password);
//  }
//}

