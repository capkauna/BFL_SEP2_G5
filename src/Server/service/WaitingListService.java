package Server.service;

import Server.database.WaitingListDAO;
import Server.model.Book;
import Server.model.User;
import Server.model.WaitingListRecord;
import Server.model.WaitingListEntry;
import Server.database.JdbcBookDAO;
import Server.database.JdbcUserDAO;
import Server.database.JdbcWaitingListDAO;
import Shared.dto.WaitingListEntryDTO;
import Shared.dto.enums.Action;
import Shared.network.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WaitingListService
{
  JdbcWaitingListDAO waitingListRepository;
  JdbcUserDAO userRepository;
  JdbcBookDAO bookRepository;

  public WaitingListService(JdbcWaitingListDAO waitingListRepository)
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

  public List<WaitingListEntry> getFullWaitingList() throws SQLException
  {
    List<WaitingListRecord> waitingListDao = waitingListRepository.findAll();
    List<WaitingListEntry> waitingList = new ArrayList<>();
    for (WaitingListRecord entry : waitingListDao)
    {
      User user = userRepository.findById(entry.getUserId());
      Book book = bookRepository.findById(entry.getBookId());
      waitingList.add(new WaitingListEntry(entry.getEntryId(), user, book, entry.getAddedAt()));
    }
    return waitingList;
  }

  public List<WaitingListEntry> getBookWaitingList(Book b) throws SQLException
  {
    List<WaitingListEntry> waitingListDao = waitingListRepository.getByBookId(b.getBookId());
    List<WaitingListEntry> waitingList = new ArrayList<>();
    for (WaitingListEntry entry : waitingListDao)
    {
      User user = userRepository.findById(entry.getUser().getUserId());
      waitingList.add(new WaitingListEntry(entry.getEntryId(), user, b, entry.getAddedAt()));
    }
    return waitingList;
  }
  public List<WaitingListEntry> getUserWaitingList(User u) throws SQLException
  {
    List<WaitingListEntry> waitingListDao = waitingListRepository.getByUserId(u.getUserId());
    List<WaitingListEntry> waitingList = new ArrayList<>();
    for (WaitingListEntry entry : waitingListDao)
    {
      Book book = bookRepository.findById(entry.getBook().getBookId());
      waitingList.add(new WaitingListEntry(entry.getEntryId(), u, book, entry.getAddedAt()));
    }
    return waitingList;
  }

}
