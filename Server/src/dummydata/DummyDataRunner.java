package dummydata;

import repository.*;
import model.*;
import model.status.*;
import util.DBConnection;
import dto.enums.*;

import java.sql.*;
import java.util.List;

public class DummyDataRunner
{
  public static void main(String[] args)
  {
    try
    {
      UserDAO userDAO = JdbcUserDAO.getInstance();
      BookDAO bookDAO = JdbcBookDAO.getInstance();

      User user1 = new User("Bugs", "Bugs Bunny", "bugs@bunny.lt", "password", "123456789", "Looney Tunes", null);
      User user2 = new User("Lola", "Lola Bunny", "lola@bunny.lt", "1111", "0987654321", "Bunny Street 21");
      user1 = userDAO.create(user1);
      user2 = userDAO.create(user2);

      Book book1 = new Book( "Book Title 1", "Author 1", 2023, Genre.FICTION, "1234567890123", Format.HARDCOVER, "Description 1", null, user1);
      Book book2 = new Book( "Book Title 2", "Author 2", 2023, Genre.NON_FICTION, "1234567890124", Format.EBOOK, "Description 2", null, user2);
      Book book3 = new Book( "Book Title 3", "Author 3", 2023, Genre.SCIENCE_FICTION, "1234567890125", Format.PAPERBACK, "Description 3", null, user1);
      book1 = bookDAO.create(book1);
      book2 = bookDAO.create(book2);
      book3 = bookDAO.create(book3);

      // Print out the created users and books
      System.out.println("Created Users:");
      List <User> users = userDAO.findAll();
      for (User u : users)
      {
        System.out.printf(" %d: %s (%s)%n",u.getUserId(), u.getUserName(), u.getEmail());
      }

      System.out.println("Created Books:");
      List <Book> books = bookDAO.findAll();

      for (Book b : books)
      {
        System.out.printf(" %s - %s (%s)%n",b.getTitle(), b.getAuthor(), b.getStatus());
      }

    }
    catch (SQLException e)
    {
      System.out.println("Error creating UserDAO: " + e.getMessage());
    }
  }
}
