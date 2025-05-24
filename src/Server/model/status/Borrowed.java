package Server.model.status;

import Shared.dto.enums.BookStatus;
import Server.model.*;

public class Borrowed implements Status
{
  private User borrower;
  public Borrowed(User borrower)
  {
    this.borrower = borrower;
  }

  @Override public void lendTo(Book b,User u)
  {
    throw new UnsupportedOperationException("Book is already borrowed");
  }

  @Override public void markAsReturned(Book b)
  {
    //TODO: make sure the owner is the one returning the book
    b.setStatus(new Available());
  }
  @Override public void addToWaitingList(Book b, User u)
  {
     //TODO: This method could be implemented to add the user to a waiting list
  }

  @Override public void setUnavailable(Book b)
  {
    throw new UnsupportedOperationException("Book is currently borrowed.");
  }
  @Override public String toString()
  {
    return "Borrowed by " + borrower.getUserName();
  }
  @Override public BookStatus getStatus()
  {
    return BookStatus.BORROWED;
  }
}
