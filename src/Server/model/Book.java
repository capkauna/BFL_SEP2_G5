package Server.model;

import Server.model.status.*;
import Shared.dto.enums.Format;
import Shared.dto.enums.Genre;

public class Book
{
  private String title, author, isbn, description, image;
  private Genre genre;
  private User owner;

  private Status status;
  private Format format;

  private Integer bookId, year;
  //private static final AtomicInteger nextId = new AtomicInteger(1);


  public Book(String title, String author, Integer year, Genre genre, String isbn, Format format, String description, String imagePath, User owner)
  {
    validateTitle(title);
    validateAuthor(author);
    validateIsbn(isbn);
    validateOwner(owner);
    this.title = title;
    this.author = author;
    this.year = year;
    this.genre = genre;
    this.isbn = isbn;
    this.format = format;
    this.description = description;
    this.image = imagePath;
    this.owner = owner;
    this.status = new Available();
    this.bookId = null; //will be set by the database

  }
  // Constructor without imagePath
  public Book(String title, String author, Integer year, Genre genre, String isbn,
      Format format, String description, User owner) {
    this(title, author, year, genre, isbn, format, description, null, owner);
  }
  //private constructor for “hydration” from the DB:
  public Book(int bookId, String title, String author,Integer year, Genre genre, String isbn,
      Format format, String description, String imagePath, User owner, Status status) {
    this.bookId = bookId;
    this.title = title;
    this.author = author;
    this.year = year;
    this.genre = genre;
    this.isbn = isbn;
    this.format = format;
    this.description = description;
    this.image = imagePath;
    this.owner = owner;
    this.status = status;
  }

  //Validators
  private void validateTitle(String title)
  {
    if (title == null || title.isEmpty())
    {
      throw new IllegalArgumentException("Title cannot be null or empty");
    }
  }


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

//TODO check if necessary in the end
  private void validateUser(User user, String role) {
    if (user == null)
      throw new IllegalArgumentException(role + " cannot be null");
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
  public Integer getYear()
  {
    return year;
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
  public String getImage()
  {
    return image;
  }
  public int getBookId()
  {
    return bookId;
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
  public void setYear(Integer year)
  {
    this.year = year;
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
  public void setImage(String image)
  {
    this.image = image;
  }
  //Optional setter so DAO can inject the generated id.
  public void setBookId(int bookId)
  {
    this.bookId = bookId;
  }
  public void setUser(User user)
  {
    validateUser(user, "User");
    //this.borrowedBy = user;
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
