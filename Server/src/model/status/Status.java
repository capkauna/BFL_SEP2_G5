package model.status;

import dto.enums.BookStatus;
import model.*;

public interface Status
{
  abstract void lendTo(Book b, User u);
  abstract void markAsReturned(Book b);
  abstract void setUnavailable(Book b);

  abstract String toString();
  BookStatus getStatus();

}
