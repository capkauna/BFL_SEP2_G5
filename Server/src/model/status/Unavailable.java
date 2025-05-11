package model.status;

import model.Book;
import model.User;

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
}
