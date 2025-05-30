package Server.model.actionmanagers;

import Server.model.*;

public class MarkAsRead
{
  private User user;
  private Book book;
  private boolean isRead;
  private String notes;
  private int id; //will be handled by the database

  public MarkAsRead(User user, Book book, boolean isRead, String notes)
  {
    this.user = user;
    this.book = book;
    this.isRead = isRead;
    this.notes = notes;
  }
  public void toggleRead()
  {
    if(this.isRead)
    {
      this.isRead = false;
    }
    this.isRead = true;
  }
  public void addNotes(String notes)
  {
    this.notes = notes;
  }
  public void removeNotes()
  {
    this.notes = null;
  }
  public User getUser()
  {
    return user;
  }
  public Book getBook()
  {
    return book;
  }
  public boolean isRead()
  {
    return isRead;
  }
  public String getNotes()
  {
    return notes;
  }
  public int getId()
  {
    return id;
  }
  public void setId(int id)
  {
    this.id = id;
  }

}
