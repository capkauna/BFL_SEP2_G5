package Server.model.status;

import Shared.dto.enums.BookStatus;
import Server.model.*;

public interface Status
{
  abstract void lendTo(Book b, User u);
  abstract void markAsReturned(Book b, User u);
  abstract void setUnavailable(Book b);
  abstract void addToWaitingList(Book b, User u);

  abstract String toString();
  BookStatus getStatus();

}
