package Client.viewmodel;

import Client.network.AuthServiceClient;
import Client.network.ClientSocketHandler;
import Client.network.SocketAuthServiceClient;
import Shared.dto.FullUserDTO;

import java.io.IOException;

public class ViewModelFactory
{
  //private final AuthServiceClient authClient;
  private final ClientSocketHandler socketHandler;
  private FullUserDTO userToView;

  // capture who just logged in
  private String currentUsername;


  public ViewModelFactory() {
    socketHandler = new ClientSocketHandler();
    try {
      socketHandler.connect("localhost", 1234); // make sure port matches server
    } catch (IOException e) {
      System.err.println("Failed to connect to server: " + e.getMessage());
      e.printStackTrace();
    }
  }


  // used by LoginController once login succeeds:
  public void setCurrentUsername(String username) {
    this.currentUsername = username;
  }

  public LogInVM getLogInVM() {
    // pass the factory itself so LoginVM can tell it who logged in
    return new LogInVM(socketHandler, this);
  }

  public HomeVM getHomeVM() {
    if (currentUsername == null)
    {
      throw new IllegalStateException("must log in before creating HomeVM");
    }
    return new HomeVM(socketHandler, currentUsername);
  }

  public SearchVM getSearchVM() {

    return new SearchVM();
  }

  public MyLibraryVM getMyLibraryVM() {
    if (currentUsername == null) {
      throw new IllegalStateException("must log in before creating MyLibraryVM");
    }
    return new MyLibraryVM(socketHandler, currentUsername);
  }

  public BookInfoVM getBookInfoVM() {
    if (currentUsername == null)
    {
      throw new IllegalStateException("must log in before creating HomeVM");
    }
    return new BookInfoVM(currentUsername);
  }

  public BookListVM getBookListVM() {
    if (currentUsername == null) {
      throw new IllegalStateException("must log in before creating BookListVM");
    }
    return new BookListVM(currentUsername);
  }

  public EditBookVM getEditBookVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    return new EditBookVM(currentUsername);
  }

  public EditUserVM getEditUserVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    return new EditUserVM(currentUsername);
  }
  public NotificationsVM getNotificationsVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalStateException("must log in before seeing Notifications");
    }
    return new NotificationsVM(currentUsername);
  }
  public ReaderNotesVM getReaderNotesVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalStateException("must log in before creating ReaderNotesVM");
    }
    return new ReaderNotesVM(currentUsername);
  }
  public UserListVM getUserListVM()
  {
    return new UserListVM();
  }
  public UserProfileVM getUserProfileVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    return new UserProfileVM(userToView ,currentUsername);
  }
  //TODO: revisit this
  public UserSummaryVM getUserSummaryVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    return new UserSummaryVM(currentUsername, userToView.getFullName(), 3, 20);
  }
}

