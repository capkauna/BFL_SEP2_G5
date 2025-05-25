  package Server.network;

  import Server.database.JdbcWaitingListDAO;
  import Server.database.WaitingListDAO;
  import Server.model.WaitingListEntry;
  import Shared.network.Request;
  import Shared.network.Response;
  import Shared.dto.enums.Action;
  import Server.service.AuthService;
  import Server.model.User;
  import java.util.List;

  import Server.database.BookDAO;
  import Server.database.JdbcBookDAO;
  import Server.model.Book;

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

            try {
              BookDAO dao = JdbcBookDAO.getInstance();
              List<Book> books = dao.findAll();
              yield new Response(true, books, null);
            } catch (Exception e) {
              yield new Response(false, null, "Failed to get books: " + e.getMessage());
            }

          }
          case ADD_TO_WAITING_LIST -> {
            // TODO:
            //  call bookService.addToWaitingList(...)
            try
            {
              WaitingListDAO wd = JdbcWaitingListDAO.getInstance();
              WaitingListEntry entry = (WaitingListEntry) request.getPayload();
              yield new Response(true, entry, null);
            }
            catch (Exception e)
            {
              yield new Response(false, null, "Failed to add to waiting list: " + e.getMessage());
            }



          }
          default -> new Response(false, null, "Unknown action.");
        };
      } catch (Exception e) {
        return new Response(false, null, "Error: " + e.getMessage());
      }
    }
  }
