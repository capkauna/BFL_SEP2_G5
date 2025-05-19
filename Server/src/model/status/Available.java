package model.status;

import dto.enums.BookStatus;
import model.Book;
import model.User;
import model.actionmanagers.BookLending;

public class Available implements Status
{
  BookLending lending = new BookLending();
  @Override public void lendTo(Book b, User u)
  {
   // b.setBorrowedBy(u);
    lending = new BookLending(b, u);
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
  @Override public String toString()
  {
    return "Available";
  }
  @Override public BookStatus getStatus()
  {
    return BookStatus.AVAILABLE;
  }
}
