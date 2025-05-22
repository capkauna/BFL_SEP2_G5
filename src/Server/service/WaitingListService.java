package Server.service;

import Server.model.Book;
import Server.model.User;
import Server.model.WaitingListDAO;
import Server.model.WaitingListEntry;
import Server.repository.JdbcBookDAO;
import Server.repository.JdbcUserDAO;
import Server.repository.WaitingListRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WaitingListService
{
  WaitingListRepository waitingListRepository;
  JdbcUserDAO userRepository;
  JdbcBookDAO bookRepository;

  public WaitingListService(WaitingListRepository waitingListRepository)
  {
    try
    {
      this.waitingListRepository = WaitingListRepository.getInstance();
      this.userRepository = JdbcUserDAO.getInstance();
      this.bookRepository = JdbcBookDAO.getInstance();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public List<WaitingListEntry> getAllWaitingLists() throws SQLException
  {
    List<WaitingListDAO> waitingListDao = waitingListRepository.findAll();
    List<WaitingListEntry> waitingList = new ArrayList<>();
    for (WaitingListDAO entry : waitingListDao)
    {
      User user = userRepository.findById(entry.getUserId());
      Book book = bookRepository.findById(entry.getBookId());
      waitingList.add(new WaitingListEntry(entry.getEntryId(), user, book, entry.getAddedAt());
    }

    return waitingList;
  }
}
