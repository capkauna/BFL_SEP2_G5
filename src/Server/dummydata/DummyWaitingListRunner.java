package Server.dummydata;

import Server.model.Book;
import Server.model.User;
import Server.model.WaitingListEntry;
import Server.database.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Dummy runner for populating and testing the waiting list functionality.
 * Creates users, books, adds entries to the waiting list, and displays the result.
 * Demonstrates the use of JdbcWaitingListDAO and WaitingListEntry.
 */

public class DummyWaitingListRunner
{
  public static void main(String[] args) {
    try {
      UserDAO userDAO = JdbcUserDAO.getInstance();
      BookDAO bookDAO = JdbcBookDAO.getInstance();
      WaitingListDAO waitingListDAO = JdbcWaitingListDAO.getInstance();

//      // Create users
//      User user3 = new User("Tomas1223", "Tomas Cat", "tomas1123@cat.com", "pass1", "111222333", "Cat Alley 1", null);
//      User user4 = new User("Jerry1224", "Jerry Mouse", "jerry1123@mouse.com", "pass2", "444555666", "Mouse Hole 2", null);
//      user3 = userDAO.create(user3);
//      user4 = userDAO.create(user4);
//
//      // Create books owned by each user
//      Book book4 = new Book("Cat and Mouse", "Author X", 2023, Genre.FICTION, "1111222233334", Format.HARDCOVER, "Fun chase story", null, user4);
//      Book book5 = new Book("Quiet House", "Author Y", 2023, Genre.NON_FICTION, "5555666677778", Format.EBOOK, "About silence", null, user3);
//      book4 = bookDAO.create(book4);
//      book5 = bookDAO.create(book5);
 User user3 = JdbcUserDAO.getInstance().findById(19);
 User user4 = JdbcUserDAO.getInstance().findById(4);

 Book book4 = JdbcBookDAO.getInstance().findById(4);
 Book book5 = JdbcBookDAO.getInstance().findById(5);

      // Add users to each other's book waiting list
      List<WaitingListEntry> afterAdd1 = waitingListDAO.addEntry(user3.getUserId(), book4.getBookId());
      List<WaitingListEntry> afterAdd2 = waitingListDAO.addEntry(user4.getUserId(), book5.getBookId());

      System.out.println("\nTomas added to waiting list for: " + book4.getTitle());
      for (WaitingListEntry entry : afterAdd1) {
        System.out.printf("- %s (added at %s)%n", entry.getUser().getUserName(), entry.getAddedAt());
      }

      System.out.println("\nJerry added to waiting list for: " + book5.getTitle());
      for (WaitingListEntry entry : afterAdd2) {
        System.out.printf("- %s (added at %s)%n", entry.getUser().getUserName(), entry.getAddedAt());
      }

//      // Remove one entry from waiting list
//      List<WaitingListEntry> afterRemove = waitingListDAO.removeEntry(user4.getUserId(), book5.getBookId());
//      System.out.println("\nWaiting list for '" + book5.getTitle() + "' after removal:");
//      for (WaitingListEntry entry : afterRemove) {
//        System.out.printf("- %s (added at %s)%n", entry.getUser().getUserName(), entry.getAddedAt());
//      }

      // Test removeEntryById - remove Tom from book1 list using entryId
      // Find the entry where Tom (user3) is waiting for book4
      int entryIdToRemove = -1;
      for (WaitingListEntry entry : afterAdd1) {
        if (entry.getUser().getUserId() == user3.getUserId()) {
          entryIdToRemove = entry.getEntryId();
          break;
        }
      }

      if (entryIdToRemove != -1) {
        List<WaitingListEntry> afterRemoveById = waitingListDAO.removeEntryById(entryIdToRemove);
        System.out.println("\nAfter removeEntryById (Tom removed from '" + book4.getTitle() + "'):");
        for (WaitingListEntry entry : afterRemoveById) {
          System.out.printf("- %s (added at %s)%n", entry.getUser().getUserName(), entry.getAddedAt());
        }
      } else {
        System.out.println("Tom was not found in the waiting list for " + book4.getTitle());
      }

      // Test exists - check if Jerry is still on the waiting list for book2
      List<WaitingListEntry> existsCheck = waitingListDAO.exists(user3.getUserId(), book4.getBookId());
      System.out.println("\nCheck if Jerry is on the waiting list for '" + book4.getTitle() + "':");
      if (!existsCheck.isEmpty()) {
        System.out.println("Yes, he is on the list.");
      } else {
        System.out.println("No, he is not on the list.");
      }

    } catch (SQLException e) {
      System.out.println("Error during waiting list test: " + e.getMessage());
    }
  }
}

