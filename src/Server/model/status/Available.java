package Server.model.status;

import Server.model.WaitingListEntry;
import Shared.dto.enums.BookStatus;
import Server.model.Book;
import Server.model.User;
import Server.model.actionmanagers.BookLending;

public class Available implements Status
{

  @Override public void lendTo(Book b, User u)
  {
   // b.setBorrowedBy(u);
    if (b.getOwner().equals(u))
    {
      throw new UnsupportedOperationException("You cannot borrow your own book. Consider making it unavailable instead.");
    }
    BookLending lending = new BookLending(b, u);
    b.setStatus(new Borrowed(u));
  }

  @Override public void markAsReturned(Book b, User u)
  {
    throw new UnsupportedOperationException("Book is already available to lend.");
  }

  @Override public void setUnavailable(Book b)
  {
    b.setStatus(new Unavailable());
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

  @Override public String toString()
  {
    return "Available";
  }
  @Override public BookStatus getStatus()
  {
    return BookStatus.AVAILABLE;
  }
}
