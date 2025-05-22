package Server.model.status;

import Shared.dto.enums.BookStatus;
import Server.model.*;

public interface Status
{
  abstract void lendTo(Book b, User u);
  abstract void markAsReturned(Book b);
  abstract void setUnavailable(Book b);

  abstract String toString();
  BookStatus getStatus();

}
