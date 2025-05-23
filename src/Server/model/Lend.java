package Server.model;

import java.time.LocalDate;

public class Lend {
  private int lendId;
  private int userId;
  private int bookId;
  private int borrowerId;
  private LocalDate startDate;
  private boolean endDate;

  public Lend(int lendId, int userId, int bookId, int borrowerId, LocalDate startDate) {
    this.lendId = lendId;
    this.userId = userId;
    this.bookId = bookId;
    this.borrowerId = borrowerId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public int getLendId() {
    return lendId;
  }

  public int getUserId() {
    return userId;
  }

  public int getBookId() {
    return bookId;
  }

  public int getBorrowerId() {
    return borrowerId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public boolean isEndDate() {
    return endDate;
  }

  public void setEndDate(boolean endDate) {
    this.endDate = endDate;
  }

  public int getLenderId()
  {
    return userId;
  }

  public LocalDate getLendDate()
  {
    return startDate;
  }

  public LocalDate getReturnDate()
  {
    return null;
  }

  public LocalDate getEndDate()
  {
    return null;
  }

  public int getId()
  {
    return lendId;
  }
}
