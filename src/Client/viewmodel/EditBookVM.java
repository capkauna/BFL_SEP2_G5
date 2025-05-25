package Client.viewmodel;

public class EditBookVM
{
  private String username;
  private String bookTitle;

  public EditBookVM(String username) {
    this.username = username;
    //TODO: fetch book info from server, if it exists
  }
}
