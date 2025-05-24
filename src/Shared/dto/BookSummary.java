package Shared.dto;

import Shared.dto.enums.BookStatus;
import Shared.dto.enums.Format;
import Shared.dto.enums.Genre;

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
}
