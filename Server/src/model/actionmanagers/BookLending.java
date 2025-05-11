package model.actionmanagers;
import model.*;
import model.status.*;

import java.time.LocalDate;

public class BookLending
{
  private Book b;
  private User u;
  private String startDate;
  private String endDate;
  private Status status;

  public BookLending(Book b, User u)
  {
    this.b = b;
    this.u = u;
    this.startDate = LocalDate.now().toString();
    this.endDate = null;
    this.status = new Borrowed();
  }

  public void setEndDate()
  {
    this.endDate = LocalDate.now().toString();
  }

  public void returnBook()
  {
    this.status = new Available();
    this.u = null;
    setEndDate();
  }
}
