package Server.model.status;

import dto.enums.BookStatus;
import Server.model.Book;
import Server.model.User;

public class Unavailable implements Status
{
  @Override public void lendTo(Book b, User u)
  {
    throw new UnsupportedOperationException("Book cannot be lent out.");
  }

  @Override public void markAsReturned(Book b)
  {
    throw new UnsupportedOperationException("Book is unavailable.");
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

