  package Server.network;

  import Shared.network.Request;
  import Shared.network.Response;
  import Shared.dto.enums.Action;
  import Server.service.AuthService;
  import Server.model.User;

  import java.io.ObjectInputStream;
  import java.io.ObjectOutputStream;
  import java.net.Socket;

  public class ClientHandler implements Runnable {
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final AuthService authService;

    public ClientHandler(Socket socket, AuthService authService) {
      this.socket = socket;
      this.authService = authService;
    }

    @Override
    public void run() {
      try {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        while (true) {
          Request request = (Request) in.readObject();
          Response response = handleRequest(request);
          out.writeObject(response);
          out.flush();
        }

      } catch (Exception e) {
        System.out.println("Client disconnected or error: " + e.getMessage());
      }
    }

    private Response handleRequest(Request request) {
      try {
        return switch (request.getAction()) {
          case LOGIN -> {
            String[] credentials = (String[]) request.getPayload();
            String username = credentials[0];
            String password = credentials[1];

            var authResult = authService.authenticate(username, password);
            if (authResult.isPresent()) {
              yield new Response(true, "Login successful", null);
            } else {
              yield new Response(false, null, "Invalid credentials.");
            }

          }
          case GET_ALL_BOOKS -> {
            // TODO: call bookService.getAllBooks();
            yield new Response(false, null, "Not implemented yet.");
          }
          case ADD_TO_WAITING_LIST -> {
            // TODO: call bookService.addToWaitingList(...)
            yield new Response(false, null, "Not implemented yet.");
          }
          default -> new Response(false, null, "Unknown action.");
        };
      } catch (Exception e) {
        return new Response(false, null, "Error: " + e.getMessage());
      }
    }
  }
