package Server.dummydata;

import Server.model.Book;
import Server.model.HistoryLog;
import Server.model.User;
import Server.repository.*;

import Shared.dto.enums.Genre;
import Shared.dto.enums.Format;

import java.util.List;

public class DummyHistoryLogRunner {
  public static void main(String[] args) {
    try {
      UserDAO userDAO = JdbcUserDAO.getInstance();
      BookDAO bookDAO = JdbcBookDAO.getInstance();
      HistoryLogDAO historyDAO = JdbcHistoryLogDAO.getInstance();

//      User owner = userDAO.create(new User(
//          "Charllli",
//          "Charlie Rob",
//          "chr@example.com",
//          "haha123",
//          "77777777",
//          "Silent Rd",
//          null
//      ));
//
//      Book book = bookDAO.create(new Book(
//          "Harry Potter and the Prisoner of Azkaban",
//          "J. K. Rowling",
//          1999,
//          Genre.ROMANCE,
//          "9999888877776",
//          Format.PAPERBACK,
//          "Classic comedy scripts",
//          null,
//          owner
//      ));
      User owner = userDAO.findByUserName("Charllli");
      Book book = bookDAO.findById(4);


      historyDAO.addLog(book.getBookId(), "Book added to library.");
      historyDAO.addLog(book.getBookId(), "Book lent to user.");
      historyDAO.addLog(book.getBookId(), "Book returned in good condition.");

      List<HistoryLog> logs = historyDAO.findByBookId(book.getBookId());

      System.out.println("History Logs for Book ID: " + book.getBookId());
      for (HistoryLog log : logs) {
        System.out.printf("[%s] %s%n", log.getAddedAt(), log.getNote());
      }

    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
