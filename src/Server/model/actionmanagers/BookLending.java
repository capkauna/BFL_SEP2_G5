package Server.model.actionmanagers;
import Server.model.*;
import Server.model.status.*;

import java.time.LocalDate;

public class BookLending
{
  private Book b;
  private User u;//borrowing user
  private User owner;
  private String startDate;
  private String endDate;
  private Status status;
  private int id;

  public BookLending(Book b, User u)
  {
    this.b = b;
    this.owner = b.getOwner();
    this.u = u;
    this.startDate = LocalDate.now().toString();
    this.endDate = null;
    this.status = (Status) new Borrowed(u);
  }

  public void setEndDate()
  {
    this.endDate = LocalDate.now().toString();
  }

  public User getUser(){
    return u;
  }

  public void returnBook(Book b)
  {
    b.markAsReturned();
    setEndDate();
  }
}
