package model.status;

import model.Book;
import model.User;

public class Available implements Status
{
  @Override public void lendTo(Book b, User u)
  {
    b.setBorrowedBy(u);
    b.setStatus(new Borrowed());
  }

  @Override public void markAsReturned(Book b)
  {
    throw new UnsupportedOperationException("Book is already available to lend.");
  }

  @Override public void setUnavailable(Book b)
  {
    b.setStatus(new Unavailable());
  }
}
