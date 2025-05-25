package Server.model.status;

import Shared.dto.enums.BookStatus;
import Server.model.Book;
import Server.model.User;

public class Unavailable implements Status
{
  @Override public void lendTo(Book b, User u)
  {
    throw new UnsupportedOperationException("Book cannot be lent out.");
  }

  @Override public void markAsReturned(Book b, User u)
  {
    throw new UnsupportedOperationException("Book is unavailable.");
  }
  @Override
  public void addToWaitingList(Book b, User u)
  {
    // This method could be implemented to add the user to a waiting list
    // if the book is unavailable, but for now, we will leave it unimplemented.
    throw new UnsupportedOperationException("Book is not available to borrow.");
  }

  @Override public void setUnavailable(Book b)
  {
    b.setStatus(new Available());
  }

  @Override public String toString()
  {
    return "Unavailable";
  }
  @Override public BookStatus getStatus()
  {
    return BookStatus.UNAVAILABLE;
  }
}

