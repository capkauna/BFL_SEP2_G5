package Server.service;

import Shared.network.Response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
  private static final ConnectionPool instance = new ConnectionPool();
  private final Map<String, Socket> connections = new ConcurrentHashMap<>();

  private ConnectionPool() {}

  public static ConnectionPool getInstance() {
    return instance;
  }

  public void addConnection(String userId, Socket socket) {
    connections.put(userId, socket);
  }

  public void removeConnection(String userId) {
    Socket socket = connections.remove(userId);
    if (socket != null) {
      try {
        socket.close();
      } catch (IOException ignored) {}
    }
  }

  public void broadcast(String message) {
    for (Socket socket : connections.values()) {
      try {
       var out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject( new Response(true,"Notification: "+message, null));
      } catch (IOException ignored) {}
    }
  }

  public void sendToUser(String userId, String message) {
    Socket socket = connections.get(userId);
    if (socket != null) {
      try {
        new PrintWriter(socket.getOutputStream(), true).println(message);
      } catch (IOException ignored) {}
    }
  }
}
