package model.status;

import model.*;

public interface Status
{
  abstract void lendTo(Book b, User u);
  abstract void markAsReturned(Book b);
  abstract void setUnavailable(Book b);
}
