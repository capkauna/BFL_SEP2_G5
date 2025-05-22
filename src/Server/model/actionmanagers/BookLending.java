package Server.model.actionmanagers;
import Server.model.*;
import Server.model.status.*;

import java.time.LocalDate;

public class BookLending
{
  private Book b;
  private User borrower;//borrowing user
  private User owner;
  private String startDate;
  private String endDate;
  private Status status;
  private int lend_id; //will be handled by the database

  public BookLending(Book b, User u)
  {
    this.b = b;
    this.owner = b.getOwner();
    this.borrower = u;
    this.startDate = LocalDate.now().toString();
    this.endDate = null;
    this.status = (Status) new Borrowed(u);
  }

  //TODO make database friendly constructor

  public void setEndDate()
  {
    this.endDate = LocalDate.now().toString();
  }
//TODO make sure that the returning is only handled through this class
  public void returnBook(Book b)
  {
    b.markAsReturned();
    setEndDate();
  }
//database relevant setters
  public void setId(int id)
  {
    this.lend_id = id;
  }


}
