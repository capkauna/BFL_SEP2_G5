package Server.util;

import Server.database.*;
import Server.model.Book;
import Server.model.Lend;
import Server.model.User;
import Server.model.WaitingListEntry;
import Server.model.actionmanagers.MarkAsRead;
import Shared.dto.enums.Format;
import Shared.dto.enums.Genre;

import java.sql.SQLException;

public class ThreeDataBasePopulationOneRun
{
  public static void main(String[] args)
  {
    // This class is intended to be used for populating the database with initial data.
    // It should be run only once to avoid duplicate entries and the errors they would cause.
    // The actual implementation would typically involve creating users, books, etc.
    // For now, it provides data for testing purposes.
    //it is not intended to be run by clients, it's a one-time server setup script.
    try
    {
      UserDAO users = JdbcUserDAO.getInstance();
      BookDAO books = JdbcBookDAO.getInstance();
      WaitingListDAO waitingList = JdbcWaitingListDAO.getInstance();
      LendDAO lends = JdbcLendDAO.getInstance();
      MarkAsReadDAO markAsRead = JdbcMarkAsReadDAO.getInstance();
      HistoryLogDAO historyLog = JdbcHistoryLogDAO.getInstance();
      NotificationDAO notifications = JdbcNotificationDAO.getInstance();

      User user1 = new User("AAAA", "Aa aa",
          "aaa@aa.com", "aaaa", "123456778",
          "Street A", null);
      User user2 = new User(
          "pikachu",  "Pika Chu",
          "pikachu@pokemon.com","thunderbolt",
          "1001001001","Kanto Route 25",
          null );

      User user3 = new User(
          "charmander","Char Mander",
          "charmander@pokemon.com","fireblast",
          "2002002002","Kanto Route 3",
          null );

      User user4 = new User(
          "squirtle","Squirt Le","squirtle@pokemon.com",
          "watergun","3003003003","Kanto Route 4",
          null );

      User user5 = new User(
          "bulbasaur","Bulba Saur","bulbasaur@pokemon.com",
          "vinewhip","4004004004","Kanto Route 1",
          null );

      User user6 = new User(
          "jigglypuff","Jiggly Puff","jigglypuff@pokemon.com",
          "singalong","5005005005","Kanto Route 7",
          null  );

      User user7 = new User(
          "meowth","Meow Th","meowth@pokemon.com",
          "payday","6006006006","Kanto Route 9",
          null);

      User user8 = new User(
          "psyduck","Psy Duck","psyduck@pokemon.com",
          "confusion","7007007007","Kanto Route 12",
          null );

      User user9 = new User(
          "eevee","eevee","eevee@pokemon.com",
          "evolvemore","8008008008","Kanto Route 17",
          null);

      User user10 = new User("gengar", "Gen Gar",
          "gengar@pokemon.com", "shadowball", "1101101101",
          "Lavender Town", null);
      user1 = users.create(user1);
      user2 = users.create(user2);
      user3 = users.create(user3);
      user4 = users.create(user4);
      user5 = users.create(user5);
      user6 = users.create(user6);
      user7 = users.create(user7);
      user8 = users.create(user8);
      user9 = users.create(user9);
      user10 = users.create(user10);

      Book book1 = new Book("The Great Adventure", "John Doe", 2023,
          Genre.FICTION, "1234567890123", Format.HARDCOVER,
          "An epic tale of adventure and discovery.", null, user1);
      Book book2 = new Book("Mistborn: The Final Empire", "Brandon Sanderson", 2006,
          Genre.FANTASY, "9780765311788", Format.HARDCOVER,
          "A street urchin discovers she is the Mistborn prophesied to overthrow the Lord Ruler.", null, user2);
      Book book3 = new Book("Mistborn: The Well of Ascension", "Brandon Sanderson", 2007,
          Genre.FANTASY, "9780765311795", Format.HARDCOVER,
          "The newly crowned rulers face rebellion and the threat of a world-ending power.", null, user3);
      Book book4 = new Book("Mistborn: The Hero of Ages", "Brandon Sanderson", 2008,
          Genre.FANTASY, "9780765311801", Format.HARDCOVER,
          "As the Final Empire falls, heroes race to decipher ancient prophecies and save the world.", null, user4);
      Book book5 = new Book("Fantastic Beasts and Where to Find Them", "J.K. Rowling", 2001,
          Genre.FANTASY, "9780439321604", Format.PAPERBACK,
          "A comprehensive guide to magical creatures in the Harry Potter universe.", null, user5);
      Book book6 = new Book("The Official Pokémon Artbook", "Ken Sugimori", 2017,
          Genre.NON_FICTION, "9781506718662", Format.HARDCOVER,
          "A stunning collection of official Pokémon artwork and concept sketches.", null, user6);
      Book book7 = new Book("Monster Hunter World: Official Complete Works", "CAPCOM", 2018,
          Genre.NON_FICTION, "9781642750152", Format.HARDCOVER,
          "An in-depth artbook featuring designs, environments, and creatures from Monster Hunter World.", null, user7);
      Book book8 = new Book("To Kill a Mockingbird", "Harper Lee", 1960,
          Genre.FICTION, "9780061120084", Format.PAPERBACK,
          "A young girl confronts racial injustice in the Deep South.", null, user8);
      Book book9 = new Book("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 2011,
          Genre.NON_FICTION, "9780062316097", Format.PAPERBACK,
          "Explores the journey of Homo sapiens from hunter-gatherers to modern society.", null, user9);
      Book book10 = new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", 1979,
          Genre.SCIENCE_FICTION, "9780345391803", Format.PAPERBACK,
          "A hapless human's intergalactic journey after Earth's destruction.", null, user10);

      book1 = books.create(book1);
      book2 = books.create(book2);
      book3 = books.create(book3);
      book4 = books.create(book4);
      book5 = books.create(book5);
      book6 = books.create(book6);
      book7 = books.create(book7);
      book8 = books.create(book8);
      book9 = books.create(book9);
      book10 = books.create(book10);

      WaitingListEntry entry1 = WaitingListEntry.addToWaitingList(book7,user1);
      WaitingListEntry entry2 = WaitingListEntry.addToWaitingList(book7, user5);
      WaitingListEntry entry3 = WaitingListEntry.addToWaitingList(book2, user10);

      entry1 = waitingList.addEntry(entry1.getBook().getBookId(), entry1.getUser().getUserId()).get(0);
      entry2 = waitingList.addEntry(entry2.getBook().getBookId(), entry2.getUser().getUserId()).get(0);
      entry3 = waitingList.addEntry(entry3.getBook().getBookId(), entry3.getUser().getUserId()).get(0);

      MarkAsRead read1 = new MarkAsRead(user2, book1, true, "Loved it!");
      MarkAsRead read2 = new MarkAsRead(user1, book2, false, "Will finish later.");
      MarkAsRead read3 = new MarkAsRead(user1, book3, true, "Great read.");

      markAsRead.create(read1);
      markAsRead.create(read2);
      markAsRead.create(read3);

      Lend l1 = Lend.lendBook(book1, user5);
      Lend l2 = Lend.lendBook(book2, user6);
      Lend lend = lends.createFull(l1);
      Lend lend2 = lends.createFull(l2);

      historyLog.addLog(book1.getBookId(), "Book added to library.");
      historyLog.addLog(book1.getBookId(), "Book lent to user.");
      historyLog.addLog(book5.getBookId(), "Book lent to user.");
      historyLog.addLog(book5.getBookId(), "Book returned in good condition.");

      notifications.addNotification(user7.getUserId(),
          "[UNREAD] You have a new book available.", book1.getBookId());
      notifications.addNotification(user8.getUserId(),
          "Book was returned and can be borrowed now", book5.getBookId());















    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }

  }
}
