package model.status;

import model.*;

public class Borrowed implements Status
{
  @Override public void lendTo(Book b,User u)
  {
    throw new UnsupportedOperationException("Book is already borrowed");
  }

  @Override public void markAsReturned(Book b)
  {
    b.setStatus(new Available());
    b.removeBorrower();
  }

  @Override public void setUnavailable(Book b)
  {
    throw new UnsupportedOperationException("Book is currently borrowed.");
  }
}
