package Client.viewmodel;

import Client.network.ClientSocketHandler;
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
      socketHandler.connect(); // make sure port matches server
    } catch (IOException e) {
      System.err.println("Failed to connect to server: " + e.getMessage());
      e.printStackTrace();
    }
  }


  // used by LoginController once login succeeds:
  public void setCurrentUsername(String username) {
    this.currentUsername = username;
  }
  public String getCurrentUsername() {
    return currentUsername;
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
    return new HomeVM(socketHandler, getCurrentUsername());
  }

  public SearchVM getSearchVM() {

    return new SearchVM(socketHandler);
  }

  public MyLibraryVM getMyLibraryVM() {
    if (currentUsername == null) {
      throw new IllegalStateException("must log in before creating MyLibraryVM");
    }
    return new MyLibraryVM(socketHandler, getCurrentUsername());
  }

  public BookInfoVM getBookInfoVM() {
    if (currentUsername == null)
    {
      throw new IllegalStateException("must log in before creating HomeVM");
    }
    return new BookInfoVM(socketHandler, getCurrentUsername());
  }

  public BookListVM getBookListVM() {
    if (currentUsername == null) {
      throw new IllegalStateException("must log in before creating BookListVM");
    }
    return new BookListVM(getCurrentUsername());
  }

  public EditBookVM getEditBookVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    return new EditBookVM(getCurrentUsername());
  }

  public EditUserVM getEditUserVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    return new EditUserVM(getCurrentUsername());
  }
  public NotificationsVM getNotificationsVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalStateException("must log in before seeing Notifications");
    }
    return new NotificationsVM(getCurrentUsername());
  }
  public ReaderNotesVM getReaderNotesVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalStateException("must log in before creating ReaderNotesVM");
    }
    return new ReaderNotesVM(getCurrentUsername());
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
    /*if (userToView == null) {
      //here is thing that our app crashes all the time
      userToView.setUserName(currentUsername);
      userToView.setFullName("Demo User");
      userToView.setPhoneNumber("0000000000");
      userToView.setEmail("demo@example.com");
      userToView.setAddress("Demo Street");

    }*/
    return new UserProfileVM(userToView, getCurrentUsername());
  }
  public void setUserToView(FullUserDTO user) {
    this.userToView = user;
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
  public WaitingListVM getWaitingListVM()
  {
    if (currentUsername == null)
    {
      throw new IllegalStateException("must log in before creating WaitingListVM");
    }
    return new WaitingListVM(getCurrentUsername());
  }
}

