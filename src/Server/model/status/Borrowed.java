package Server.model.status;

import Shared.dto.enums.BookStatus;
import Server.model.*;

import java.sql.SQLException;

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

  @Override public void markAsReturned(Book b, User u)
  {
    //TODO: make sure the owner is the one returning the book
    if (b.getOwner() != u) {
      throw new IllegalStateException("Only the owner can mark the book as returned.");
    }

    b.setStatus(new Available());
  }
  @Override public void addToWaitingList(Book b, User u)
  {
    if (b.getOwner().getUserId() == u.getUserId())
    {
      throw new IllegalArgumentException("You cannot add yourself to the waiting list for your own book.");
    }
     //I honestly don't like this, but we don't have time to figure it out properly
    WaitingListEntry.addToWaitingList(b, u);

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
