package Server.model;

public class BookSummary
{
  private final String title, author, ownerName;
  private final Format format;
  private final Genre genre;
  private BookStatus status;

  public BookSummary (String title, String author, String ownerName, Format format, Genre genre, BookStatus status)
  {
    this.title = title;
    this.author = author;
    this.ownerName = ownerName;
    this.format = format;
    this.genre = genre;
    this.status = status;
  }

  public String getTitle()
  {
    return title;
  }

  public String getAuthor()
  {
    return author;
  }

  public String getOwnerName()
  {
    return ownerName;
  }

  public Format getFormat()
  {
    return format;
  }

  public Genre getGenre()
  {
    return genre;
  }

  public BookStatus getStatus()
  {
    return status;
  }

  public void setStatus(BookStatus status)
  {
    this.status = status;
  }
}
