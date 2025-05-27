  package Server.network;

  import Server.database.*;
  import Server.model.*;
  import Server.service.BookInfoService;
  import Server.service.WaitingListService;
  import Shared.dto.BookSummary;
  import Shared.dto.WaitingListEntryDTO;
  import Shared.network.*;
  import Shared.dto.enums.*;
  import Server.service.AuthService;

  import java.sql.SQLException;
  import java.util.List;
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

              List<BookSummary> summaries = bookInfoService.getAllBookSummaries();
              System.out.println(" -> Server received GET_ALL_BOOKS request. ");
              yield new Response(true, summaries, null);

            } catch (Exception e) {
              yield new Response(false, null, "Failed to get books: " + e.getMessage());
            }

          }
          case GET_BOOK_INFO -> {
            try{
            int bookId = (Integer) request.getPayload();


            // fetch domain object
            Book b = bookInfoService.getBookInfo(bookId);

            // map to a serializable DTO
            var dto = new BookSummary(
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
            WaitingListEntryDTO dto = (WaitingListEntryDTO) request.getPayload();
            WaitingListEntry entry = waitingListService.addEntryDTO(dto);
            // return the newly-created entry back to the client
            yield new Response(true, entry, null);
            }

            case GET_WAITING_LIST -> {
            //not done yet
              try {
                WaitingListDAO wd = JdbcWaitingListDAO.getInstance();
                List<WaitingListRecord> waitingList = wd.findAll();
                yield new Response(true, waitingList, null);
              } catch (Exception e) {
                yield new Response(false, null, "Failed to get waiting list: " + e.getMessage());
              }
            }
          case LEND_BOOK -> {
            //not really done, this is a lot of wilding
            try {
              Lend lendRequest = (Lend) request.getPayload();
              BookDAO bookDAO = JdbcBookDAO.getInstance();
              UserDAO userDAO = JdbcUserDAO.getInstance();
              LendDAO lends = JdbcLendDAO.getInstance();

              Book book = bookDAO.findById(lendRequest.getBookId());
              User user = userDAO.findById(lendRequest.getBorrowerId());

              if (book != null && user != null) {
                // Assuming lendBook method exists in the service
                Lend lend =Lend.lendBook(book, user);

                yield new Response(true, lend,  "Book lent successfully.");
              } else {
                yield new Response(false, null, "Invalid book or user.");
              }
            } catch (Exception e) {
              yield new Response(false, null, "Error lending book: " + e.getMessage());
            }
          }




          default -> new Response(false, null, "Unknown action.");
        };
      } catch (Exception e) {
        return new Response(false, null, "Error: " + e.getMessage());
      }
    }
  }
