package Shared.dto;

import Shared.dto.enums.Format;
import Shared.dto.enums.Genre;

import java.io.Serializable;

public class BookSummaryDTO implements Serializable
{
  private final int bookId;
  private final String title, author, ownerName, isbn, description,status, avatar;
  private final Format format;
  private final Genre genre;


  public BookSummaryDTO(int bookId, String title, String author, String isbn,
      String ownerName, Format format, Genre genre, String status,
      String description, String avatar)
  {
    this.bookId = bookId;
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.ownerName = ownerName;
    this.format = format;
    this.genre = genre;
    this.status = status;
    this.description = description;
    this.avatar = avatar;
  }

  public int getBookId()
  {
    return bookId;
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

  public String getIsbn()
  {
    return isbn;
  }

  public String getDescription()
  {
    return description;
  }

  public String getStatus()
  {
    return status;
  }

  public String getAvatar()
  {
    return avatar;
  }

  public Format getFormat()
  {
    return format;
  }

  public Genre getGenre()
  {
    return genre;
  }
}
