package Server.network;

import java.util.HashSet;
import java.util.Set;

public class ClientPool
{
  private static ClientPool instance;
  private final Set<ClientHandler> authenticatedClients;

  private ClientPool() {
    authenticatedClients = new HashSet<>();
  }
  public static ClientPool getInstance() {
    if (instance == null) {
      instance = new ClientPool();
    }
    return instance;
  }
  public void addClient(ClientHandler client) {
    authenticatedClients.add(client);
    System.out.println("Client connected: " + client.getAuthenticatedUser().getUserName());
  }
  public void removeClient(ClientHandler client) {
    authenticatedClients.remove(client);
    System.out.println("Client disconnected: " + client.getAuthenticatedUser().getUserName());
  }
}
