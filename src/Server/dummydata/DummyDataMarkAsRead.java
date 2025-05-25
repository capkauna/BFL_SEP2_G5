package Server.dummydata;

import Server.model.Book;
import Server.model.User;
import Server.model.actionmanagers.MarkAsRead;
import Server.database.BookDAO;
import Server.database.UserDAO;
import Server.database.MarkAsReadDAO;
import Server.database.JdbcBookDAO;
import Server.database.JdbcUserDAO;
import Server.database.JdbcMarkAsReadDAO;

import java.sql.SQLException;
import java.util.List;

public class DummyDataMarkAsRead {
  public static void main(String[] args) {
    try {
      UserDAO userDAO = JdbcUserDAO.getInstance();
      BookDAO bookDAO = JdbcBookDAO.getInstance();
      MarkAsReadDAO markAsReadDAO = JdbcMarkAsReadDAO.getInstance();

      List<User> users = userDAO.findAll();
      List<Book> books = bookDAO.findAll();

      User user1 = users.get(0);
      User user2 = users.get(1);
      Book book1 = books.get(0);
      Book book2 = books.get(1);
      Book book3 = books.get(2);

      MarkAsRead read1 = new MarkAsRead(user2, book1, true, "Loved it!");
      MarkAsRead read2 = new MarkAsRead(user1, book2, false, "Will finish later.");
      MarkAsRead read3 = new MarkAsRead(user1, book3, true, "Great read.");

      markAsReadDAO.create(read1);
      markAsReadDAO.create(read2);
      markAsReadDAO.create(read3);

      System.out.println("Created MarkAsRead entries:");
      List<MarkAsRead> allReads = markAsReadDAO.findAll();
      for (MarkAsRead entry : allReads) {
        System.out.printf("User: %s | Book: %s | Read: %s | Notes: %s%n",
            entry.getUser().getUserName(),
            entry.getBook().getTitle(),
            entry.isRead() ? "Yes" : "No",
            entry.getNotes());
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}