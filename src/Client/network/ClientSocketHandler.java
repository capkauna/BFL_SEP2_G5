package Client.network;

import Shared.network.Request;
import Shared.network.Response;

import java.io.*;
import java.net.Socket;

public class ClientSocketHandler {
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private Socket socket;
  private String HOST = "localhost";
  private int PORT = 1234;

  public void connect() throws IOException {
    socket = new Socket(HOST, PORT);
    out = new ObjectOutputStream(socket.getOutputStream());
    in = new ObjectInputStream(socket.getInputStream());
  }

  public void sendRequest(Request request) throws IOException {
    out.writeObject(request);
    out.flush();
  }

  public Response readResponse() throws IOException, ClassNotFoundException {
    return (Response) in.readObject();
  }

  public void close() throws IOException {
    if (socket != null) socket.close();
  }


}
