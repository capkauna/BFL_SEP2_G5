package Server.model;

import java.time.LocalDate;
import java.util.Objects;

public class Lend {
  private Integer lendId, ownerId, bookId, borrowerId;
  private LocalDate startDate, endDate;
//


  public Lend( Integer ownerId, Integer bookId, Integer borrowerId) {
    this.ownerId = ownerId;
    this.bookId = bookId;
    this.borrowerId = borrowerId;
    this.startDate = LocalDate.now();
    this.endDate = null;
    this.lendId = null;//will be created by database
  }
  //constructod for DB hydration
  public Lend (Integer lendId, Integer ownerId, Integer bookId, Integer borrowerId, LocalDate startDate, LocalDate endDate) {
    this.lendId = lendId;
    this.ownerId = ownerId;
    this.bookId = bookId;
    this.borrowerId = borrowerId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Integer getLendId() {
    return lendId;
  }

  //TODO: make sure this is the book's owner id, not the borrower
  public Integer getOwnerId() {
    return ownerId;
  }

  public Integer getBookId() {
    return bookId;
  }

  public Integer getBorrowerId() {
    return borrowerId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }
//TODO make sure this gets called upon book return
  public void setEndDate() {
    this.endDate = LocalDate.now();

  }




//for db use
public static Lend fromDb(int lendId,
    int ownerId,
    int bookId,
    int borrowerId,
    LocalDate start,
    LocalDate end) {
  return new Lend(lendId, ownerId, bookId, borrowerId, start, end);
}


  public void setLendId(int lendId)
  {
    this.lendId = lendId;
  }

  public static Lend lendBook(Book b, User u)
  {
    b.lendTo(u);
    return new Lend(b.getOwner().getUserId(), b.getBookId(), u.getUserId());
  }
  public void returnBook(Book b)
  {
    this.setEndDate();
    b.markAsReturned();
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Lend lend = (Lend) o;
    return Objects.equals(lendId, lend.lendId) && Objects.equals(ownerId,
        lend.ownerId) && Objects.equals(bookId, lend.bookId) && Objects.equals(
        borrowerId, lend.borrowerId) && Objects.equals(startDate,
        lend.startDate) && Objects.equals(endDate, lend.endDate);
  }

  @Override public int hashCode()
  {
    return Objects.hash(lendId, ownerId, bookId, borrowerId, startDate,
        endDate);
  }

  @Override public String toString()
  {
    return "Lend{" + "lendId=" + lendId + ", ownerId=" + ownerId + ", bookId="
        + bookId + ", borrowerId=" + borrowerId + ", startDate=" + startDate
        + ", endDate=" + endDate + '}';
  }
}
