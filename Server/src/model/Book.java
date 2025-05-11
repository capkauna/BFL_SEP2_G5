package model;

import dto.enums.Format;
import dto.enums.Genre;
import model.Status.*;

public class Book
{
  private String title, author, isbn, description, imagePath;
  private Genre genre;
  private User owner;
  private Status status;
  private Format format;

  public Book(String title, String author, Genre genre, String isbn, Format format, String description, String imagePath, User owner)
  {
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.isbn = isbn;
    this.format = format;
    this.description = description;
    this.imagePath = imagePath;
    this.owner = owner;
    this.status = new Available();
  }
  // Constructor without imagePath
  public Book(String title, String author, Genre genre, String isbn, Format format, String description, User owner)
  {
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.isbn = isbn;
    this.format = format;
    this.description = description;
    this.owner = owner;
    this.status = new Available();
  }


  public String getTitle()
  {
    return title;
  }
  public String getAuthor()
  {
    return author;
  }
  public String getIsbn()
  {
    return isbn;
  }
  public String getDescription()
  {
    return description;
  }
  public Genre getGenre()
  {
    return genre;
  }
  public Format getFormat()
  {
    return format;
  }
  public User getOwner()
  {
    return owner;
  }
  public Status getStatus()
  {
    return status;
  }
  public String getImagePath()
  {
    return imagePath;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }
  public void setAuthor(String author)
  {
    this.author = author;
  }
  public void setIsbn(String isbn)
  {
    this.isbn = isbn;
  }
  public void setDescription(String description)
  {
    this.description = description;
  }
  public void setGenre(Genre genre)
  {
    this.genre = genre;
  }
  public void setFormat(Format format)
  {
    this.format = format;
  }
  public void setOwner(User owner)
  {
    this.owner = owner;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public void setImagePath(String imagePath)
  {
    this.imagePath = imagePath;
  }
}
