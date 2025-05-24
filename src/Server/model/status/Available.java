package Server.model.status;

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

  @Override public void markAsReturned(Book b)
  {
    throw new UnsupportedOperationException("Book is already available to lend.");
  }

  @Override public void setUnavailable(Book b)
  {
    b.setStatus(new Unavailable());
  }

  @Override public void addToWaitingList(Book b, User u)
  {
    // This method could be implemented to add the user to a waiting list
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
