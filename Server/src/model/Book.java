package model;

import dto.enums.*;
import model.status.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Book
{
  private String title, author, isbn, description, imagePath;
  private Genre genre;
  private User owner;
  private User borrowedBy;

  private Status status;
  private Format format;

  private int bookId;
  private static final AtomicInteger nextId = new AtomicInteger(1);


  public Book(String title, String author, Genre genre, String isbn, Format format, String description, String imagePath, User owner)
  {
    validateTitle(title);
    validateAuthor(author);
    validateIsbn(isbn);
    validateOwner(owner);
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.isbn = isbn;
    this.format = format;
    this.description = description;
    this.imagePath = imagePath;
    this.owner = owner;
    this.status = new Available();
    this.bookId = bookId;//TODO make this come from the database
    //this.bookId = nextId.getAndIncrement();
    //this.borrowedBy = null;
  }
  // Constructor without imagePath
  public Book(String title, String author, Genre genre, String isbn,
      Format format, String description, User owner) {
    this(title, author, genre, isbn, format, description, null, owner);
  }

  //Validators
  private void validateTitle(String title)
  {
    if (title == null || title.isEmpty())
    {
      throw new IllegalArgumentException("Title cannot be null or empty");
    }
  }
  //TODO: check if author is a valid name
  private void validateAuthor(String author)
  {
    if (author == null || author.isEmpty())
    {
      throw new IllegalArgumentException("Author cannot be null or empty");
    }
  }
  private void validateIsbn(String isbn)
  {
    if (isbn == null || isbn.length() != 13)
    {
      throw new IllegalArgumentException("ISBN must be 13 characters long");
    }
    if (!isbn.matches("\\d+"))
    {
      throw new IllegalArgumentException("ISBN must be a number");
    }
  }
  private void validateOwner(User owner)
  {
    if (owner == null)
    {
      throw new IllegalArgumentException("Owner cannot be null");
    }
  }
  //only for state use
  private void validateBorrower(User borrower)
  {
    if (borrower.equals(owner))
    {
      throw new IllegalArgumentException("Owner cannot borrow their own book");
    }
  }


  //getters
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
  public int getBookId()
  {
    return bookId;
  }
  public User getBorrowedBy()
  {
    return borrowedBy;
  }
//setters
  public void setTitle(String title)
  {
    validateTitle(title);
    this.title = title;
  }
  public void setAuthor(String author)
  {
    validateAuthor(author);
    this.author = author;
  }
  public void setIsbn(String isbn)
  {
    validateIsbn(isbn);
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
    validateOwner(owner);
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
  public void setBorrowedBy(User borrowedBy)
  {
    validateBorrower(borrowedBy);
    this.borrowedBy = borrowedBy;
  }
  public void removeBorrower()
  {
    this.borrowedBy = null;
  }



  //Status methods
  public void lendTo(User user)
  {
    status.lendTo(this, user);
  }
  public void markAsReturned()
  {
    status.markAsReturned(this);
  }
  public void setUnavailable()
  {
    status.setUnavailable(this);
  }
}
