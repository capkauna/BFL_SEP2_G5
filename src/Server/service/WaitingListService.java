package Server.service;

import Server.database.*;
import Server.model.Book;
import Server.model.User;
import Server.model.WaitingListRecord;
import Server.model.WaitingListEntry;
import Shared.dto.WaitingListEntryDTO;

import java.sql.SQLException;
import java.util.ArrayList;


public class WaitingListService
{
  WaitingListDAO waitingListRepository;
  UserDAO userRepository;
  BookDAO bookRepository;

  public WaitingListService()
  {
    try
    {
      this.waitingListRepository = JdbcWaitingListDAO.getInstance();
      this.userRepository = JdbcUserDAO.getInstance();
      this.bookRepository = JdbcBookDAO.getInstance();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public ArrayList<WaitingListEntry> getFullWaitingList() throws SQLException
  {
    ArrayList<WaitingListRecord> waitingListDao = waitingListRepository.findAll();
    ArrayList<WaitingListEntry> waitingList = new ArrayList<>();
    for (WaitingListRecord entry : waitingListDao)
    {
      User user = userRepository.findById(entry.getUserId());
      Book book = bookRepository.findById(entry.getBookId());
      waitingList.add(new WaitingListEntry(entry.getEntryId(), user, book, entry.getAddedAt()));
    }
    return waitingList;
  }

  public ArrayList<WaitingListEntryDTO> getBookWaitingList(Book b) throws SQLException
  {
    ArrayList<WaitingListEntry> waitingListDao = waitingListRepository.getByBookId(b.getBookId());
    ArrayList<WaitingListEntryDTO> waitingList = new ArrayList<>();
    for (WaitingListEntry entry : waitingListDao)
    {
      User user = userRepository.findById(entry.getUser().getUserId());
      waitingList.add(new WaitingListEntryDTO( b.getBookId(),user.getUserName(), entry.getAddedAt()));
    }
    return waitingList;
  }
  public ArrayList<WaitingListEntryDTO> getByBookId(int bookId) throws SQLException
  {
    ArrayList<WaitingListEntry> waitingListDao = waitingListRepository.getByBookId(bookId);
    ArrayList<WaitingListEntryDTO> waitingList = new ArrayList<>();
    for (WaitingListEntry entry : waitingListDao)
    {
      User user = userRepository.findById(entry.getUser().getUserId());
      waitingList.add(new WaitingListEntryDTO(bookId, user.getUserName(), entry.getAddedAt()));
    }
    return waitingList;
  }

  public ArrayList<WaitingListEntry> getUserWaitingList(User u) throws SQLException
  {
    ArrayList<WaitingListEntry> waitingListDao = waitingListRepository.getByUserId(u.getUserId());
    ArrayList<WaitingListEntry> waitingList = new ArrayList<>();
    for (WaitingListEntry entry : waitingListDao)
    {
      Book book = bookRepository.findById(entry.getBook().getBookId());
      waitingList.add(new WaitingListEntry(entry.getEntryId(), u, book, entry.getAddedAt()));
    }
    return waitingList;
  }
  public WaitingListEntry addEntry(User user, Book book) throws SQLException
  {
    ArrayList<WaitingListEntry> existingEntries = waitingListRepository.exists(user.getUserId(), book.getBookId());
    if (!existingEntries.isEmpty())
    {
      return existingEntries.get(0); // Entry already exists, return it
    }
    ArrayList<WaitingListEntry> newEntry = waitingListRepository.addEntry(user.getUserId(), book.getBookId());
    return newEntry.getFirst(); // Return the newly created entry
  }
  public WaitingListEntry addEntryFromDTO (WaitingListEntryDTO dto) throws SQLException {
    // 1) turn DTO → domain objects
    User user = userRepository.findByUserName(dto.getUsername());
    Book book = bookRepository.findById(dto.getBookId());
    // 2) delegate to your existing logic
    return addEntry(user, book);
  }

}
