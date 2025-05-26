package Server.dummydata;

import Server.model.Book;
import Server.model.Lend;
import Server.model.User;
import Server.database.*;
import Shared.dto.enums.Format;
import Shared.dto.enums.Genre;

public class DummyLendRunner {
  public static void main(String[] args) {
    try {
      UserDAO userDAO = JdbcUserDAO.getInstance();
      BookDAO bookDAO = JdbcBookDAO.getInstance();
      LendDAO lendDAO = JdbcLendDAO.getInstance();

      User lender1 = userDAO.create(new User("Kira", "Karina", "kira@example.com", "latv123", "10000000", "Home street", null));
      User borrower1 = userDAO.create(new User("Mira", "Miroslava", "mira@example.com", "korea123", "00000001", "Town Ave", null));


      Book books = bookDAO.create(new Book(
          "Harry Potter and the Philosopher's Stone",
          "J. K. Rowling",
          1997,
          Genre.FANTASY,
          "1111222233334",
          Format.HARDCOVER,
          "Fantasy book",
          null,
          lender1
      ));
//      User lender = userDAO.findById(2);
//      User borrower = userDAO.findById(8);

     // Book book = bookDAO.findById(2);


      //int borrowingPeriod = 14; // days
      Lend l1 = Lend.lendBook(books, borrower1);
      Lend lend = lendDAO.createFull(l1);

      System.out.println("Lend created:");
      System.out.printf(
          "Book ID: %d, Lender ID: %d, Borrower ID: %d, Start Date: %s, Return Date: %s%n",
          lend.getBookId(),
          lend.getOwnerId(),
          lend.getBorrowerId(),
          lend.getStartDate(),
          lend.getEndDate()
      );

    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
