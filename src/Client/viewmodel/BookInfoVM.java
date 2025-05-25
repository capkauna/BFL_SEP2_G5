package Client.viewmodel;

import Shared.dto.BookSummary;

public class BookInfoVM
{

  private String username;

  public BookInfoVM( String username) {
    this.username = username;
  }


  public String getTitle() {
    return "Crime and Punishment";
  }

  public String getAuthor() {
    return "Fyodor Dostoevsky";
  }

  public String getGenre() {
    return "Fiction";
  }

  public String getIsbn() {
    return "1234567890123";
  }

  public String getOwner() {
    return "Karina";
  }

  public String getBorrowedBy() {
    return "None";
  }

  public String getLanguage() {
    return "English";
  }

  public String getYear() {
    return "1866";
  }

  public String getReaderNotes() {
    return "Brilliant! A must read.";
  }
}

