  package Server.network;

  import Server.database.*;
  import Server.model.*;
  import Server.service.*;
  import Shared.dto.BookSummaryDTO;
  import Shared.dto.FullUserDTO;
  import Shared.dto.WaitingListEntryDTO;
  import Shared.network.*;

  import java.sql.SQLException;
  import java.util.ArrayList;
  import java.io.ObjectInputStream;
  import java.io.ObjectOutputStream;
  import java.net.Socket;


  public class ClientHandler implements Runnable {
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final AuthService authService;
    private final WaitingListService waitingListService = new WaitingListService();
    private final BookInfoService bookInfoService = new BookInfoService();

    public ClientHandler(Socket socket, AuthService authService)
        throws SQLException
    {
      this.socket = socket;
      this.authService = authService;

    }

    @Override
    public void run() {
      try {
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());

        while (true) {
          Object obj = in.readObject();
          if(obj instanceof Request request){
            Response response = handleRequest(request);
            out.writeObject(response);
          }
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
            var user = authResult.get();
            var fullUser = new FullUserDTO(user.getUserId(),user.getUserName(),user.getFullName(),user.getEmail(),user.getPhoneNumber(),user.getAddress(),user.getAvatar());
            authService.setAuthenticatedUser(username);
            ClientPool pool = ClientPool.getInstance();
            pool.addClient(this);

            if (authResult.isPresent()) {
              yield new Response(true, fullUser, null);
            } else {
              yield new Response(false, null, "Invalid credentials.");
            }

          }
          case GET_ALL_BOOKS -> {

            try {

              ArrayList<BookSummaryDTO> summaries = bookInfoService.getAllBookSummaries();
              System.out.println(" -> Server received GET_ALL_BOOKS request. ");
              yield new Response(true, summaries, null);
            } catch (Exception e) {
              yield new Response(false, null, "Failed to get books: " + e.getMessage());
            }

          }
          case GET_MY_BOOKS -> {
            try {
              int userId = (Integer) request.getPayload();
              ArrayList<BookSummaryDTO> summaries = bookInfoService.getMyBookSummaries(userId);
              System.out.println(" -> Server received GET_MY_BOOKS request. ");
              yield new Response(true, summaries, null);
            } catch (Exception e) {
              yield new Response(false, null, "Failed to get books: " + e.getMessage());
            }

          }
          case GET_BOOK_INFO -> {
            try{
            int bookId = (Integer) request.getPayload();


            // fetch domain object
            Book b = BookInfoService.getBookInfo(bookId);

            // map to a serializable DTO
            var dto = new BookSummaryDTO(
                b.getBookId(),
                b.getTitle(),
                b.getAuthor(),
                b.getIsbn(),
                b.getOwner().getUserName(),
                b.getFormat(),
                b.getGenre(),
                b.getStatus().toString(),
                b.getDescription(),
                b.getImage()
            );

            yield new Response(true, dto, null);
          } catch (SQLException e)
            {
              yield new Response(false, null , "No book found");
            }
          }
          case ADD_TO_WAITING_LIST ->
          {
            WaitingListEntryDTO waitingListEntry = (WaitingListEntryDTO) request.getPayload();
            WaitingListEntry entry = waitingListService.addEntryFromDTO(waitingListEntry);
            // return the newly-created entry back to the client
            yield new Response(true, waitingListEntry, null);
            }

            case GET_WAITING_LIST -> {
            //not done yet
              try {
                int bookId = (Integer) request.getPayload();

                ArrayList<WaitingListEntryDTO> waitingList = waitingListService.getByBookId(bookId);
                System.out.println(" -> Server received GET_WAITING_LIST request for book ID: " + bookId);
                yield new Response(true, waitingList, null);
              } catch (Exception e) {
                System.out.println("Error getting waiting list: " + e.getMessage());
                yield new Response(false, null, "Failed to get waiting ArrayList: " + e.getMessage());
              }
            }
          case LEND_BOOK -> {
            //not really done, this is a lot of wilding
            try {
              Lend lendRequest = (Lend) request.getPayload();
              //bookInfoService.lendBook();
              BookDAO bookDAO = JdbcBookDAO.getInstance();
              UserDAO userDAO = JdbcUserDAO.getInstance();
              LendDAO lends = JdbcLendDAO.getInstance();

              Book book = bookDAO.findById(lendRequest.getBookId());
              User user = userDAO.findById(lendRequest.getBorrowerId());

              if (book != null && user != null) {
                // Assuming lendBook method exists in the service
                Lend lend =Lend.lendBook(book, user);
                lends.create(lend);

                yield new Response(true, lend,  "Book lent successfully.");
              } else {
                yield new Response(false, null, "Invalid book or user.");
              }
            } catch (Exception e) {
              yield new Response(false, null, "Error lending book: " + e.getMessage());
            }
          }
          case GET_ALL_USERS -> {
            try {
              UserInfoService users = new UserInfoService();
              ArrayList<FullUserDTO> allUsers = users.getAllUsers();
              System.out.println(" -> Server received GET_ALL_USERS request. ");
              yield new Response(true, allUsers, null);
            } catch (SQLException e) {
              yield new Response(false, null, "Failed to get users: " + e.getMessage());
            }
          }




          default -> new Response(false, null, "Unknown action.");
        };
      } catch (Exception e) {
        return new Response(false, null, "Error: " + e.getMessage());
      }
    }

    public void close() {
      try {
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null && !socket.isClosed()) socket.close();
      } catch (Exception e) {
        System.out.println("Error closing client handler: " + e.getMessage());
      }
    }

    public FullUserDTO getAuthenticatedUser()
    {
         return authService.getAuthenticatedUser();
    }
//for broadcasting messages to all clients
    public void send(Response response) {
      try {
        out.writeObject(response);
        out.flush();
      } catch (Exception e) {
        System.out.println("Failed to send to client: " + e.getMessage());
      }
    }

  }
