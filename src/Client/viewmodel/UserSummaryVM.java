package Client.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserSummaryVM {
  private final StringProperty userName = new SimpleStringProperty();
  private final IntegerProperty booksInLibrary = new SimpleIntegerProperty();
  private final IntegerProperty booksRead = new SimpleIntegerProperty();

  public UserSummaryVM(String userName, int booksInLibrary, int booksRead) {
    this.userName.set(userName);
    this.booksInLibrary.set(booksInLibrary);
    this.booksRead.set(booksRead);
  }

  public String getUserName() { return userName.get(); }
  public int getBooksInLibrary() { return booksInLibrary.get(); }
  public int getBooksRead() { return booksRead.get(); }

  public StringProperty userNameProperty() { return userName; }
  public IntegerProperty booksInLibraryProperty() { return booksInLibrary; }
  public IntegerProperty booksReadProperty() { return booksRead; }
}

