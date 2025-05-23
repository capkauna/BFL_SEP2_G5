/*package Server.dummydata;

import Server.model.Book;
import Server.model.User;
import Server.model.WaitingListEntry;
import Server.repository.*;

import java.sql.SQLException;
import java.util.List;
import shared.model.Genre;

public class DummyWaitingList
{
   try {
  UserDAO userDAO = JdbcUserDAO.getInstance();
  BookDAO bookDAO = JdbcBookDAO.getInstance();
  JdbcWaitingListDAO waitingListDAO = JdbcWaitingListDAO.getInstance();

  // Create sample users
  User user1 = new User("Tom", "Tom Cat", "tom@cat.com", "pass1", "111222333", "Cat Alley 1", null);
  User user2 = new User("Jerry", "Jerry Mouse", "jerry@mouse.com", "pass2", "444555666", "Mouse Hole 2", null);
  user1 = userDAO.create(user1);
  user2 = userDAO.create(user2);

  // Create sample books
  Book book1 = new Book("Cat and Mouse", "Author X", 2023, Genre.FICTION, "1111222233334", Format.HARDCOVER, "Fun chase story", null, user2);
  Book book2 = new Book("Quiet House", "Author Y", 2023, Genre.NON_FICTION, "5555666677778", Format.EBOOK, "About silence", null, user1);
  book1 = bookDAO.create(book1);
  book2 = bookDAO.create(book2);

  // Add users to the waiting list for each other's books
  boolean add1 = waitingListDAO.addEntry(user1.getUserId(), book1.getBookId());
  boolean add2 = waitingListDAO.addEntry(user2.getUserId(), book2.getBookId());

  System.out.println("\nWaiting list status:");
  System.out.println("Tom added to '" + book1.getTitle() + "': " + add1);
  System.out.println("Jerry added to '" + book2.getTitle() + "': " + add2);

  // Retrieve and display waiting list entries
  List<WaitingListEntry> book1WaitingList = JdbcWaitingListDAO.getByBookId(book1.getBookId());
  List<WaitingListEntry> book2WaitingList = JdbcWaitingListDAO.getByBookId(book2.getBookId());

  System.out.println("\nWaiting list for '" + book1.getTitle() + "':");
  for (WaitingListEntry entry : book1WaitingList) {
    System.out.printf("- %s (added at %s)\n", entry.getUser().getUserName(), entry.getAddedAt());
  }

  System.out.println("\nWaiting list for '" + book2.getTitle() + "':");
  for (WaitingListEntry entry : book2WaitingList) {
    System.out.printf("- %s (added at %s)\n", entry.getUser().getUserName(), entry.getAddedAt());
  }

  // Test removal from waiting list
  boolean removed = waitingListDAO.removeEntry(user2.getUserId(), book2.getBookId());
  System.out.println("\nRemoved Jerry from '" + book2.getTitle() + "': " + removed);

} catch (SQLException e) {
  System.out.println("Error during dummy waiting list test: " + e.getMessage());
}
}*/
