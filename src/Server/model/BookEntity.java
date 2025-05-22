package Server.model;

public class BookEntity
{
  private int bookId;
  private String title;
  private String publisher;
  private int ownerId;
  private int year;

  public BookEntity(int bookId, String title, String publisher, int ownerId, int year)
  {
    this.bookId = bookId;
    this.title = title;
    this.publisher = publisher;
    this.ownerId = ownerId;
    this.year = year;
  }

  public BookEntity(String title, String publisher, int ownerId, int year)
  {
    this.title = title;
    this.publisher = publisher;
    this.ownerId = ownerId;
    this.year = year;
  }

  public int getBookId()
  {
    return bookId;
  }

  public void setBookId(int bookId)
  {
    this.bookId = bookId;
  }

  public String getTitle()
  {
    return title;
  }

  public String getPublisher()
  {
    return publisher;
  }

  public int getOwnerId()
  {
    return ownerId;
  }

  public int getYear()
  {
    return year;
  }


}
