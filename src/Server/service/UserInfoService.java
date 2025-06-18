package Server.service;


import Server.database.*;
import Server.model.Book;
import Server.model.User;
import Shared.dto.BookSummaryDTO;
import Shared.dto.FullUserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserInfoService
{
  private final UserDAO userDataBase = JdbcUserDAO.getInstance();
  private final BookDAO bookDataBase = JdbcBookDAO.getInstance();

  public UserInfoService()  throws SQLException
  {
//    this.userDataBase = JdbcUserDAO.getInstance();
//    this.bookDataBase = JdbcBookDAO.getInstance();
  }
//public static UserInfoService getInstance() throws SQLException {
//    return new UserInfoService();
//  }

  public FullUserDTO getUserInfo(int userId) throws SQLException {
    return getFullUserDTO(userId);
  }

  private FullUserDTO getFullUserDTO(int userId) throws SQLException
  {
    User userToDTO = userDataBase.findById(userId);
    return new FullUserDTO(userToDTO.getUserId(), userToDTO.getUsername(),
        userToDTO.getEmail(), userToDTO.getFullName(),
        userToDTO.getPhoneNumber(), userToDTO.getAddress(),
        userToDTO.getAvatar());
  }

  public ArrayList<FullUserDTO> getAllUsers() throws SQLException {
    ArrayList<User> usersToDTO = userDataBase.findAll();
    ArrayList <FullUserDTO> users = new ArrayList<>();
    for (User user : usersToDTO) {
      FullUserDTO newUser = new FullUserDTO(user.getUserId(), user.getUsername(),
          user.getEmail(), user.getFullName(),
          user.getPhoneNumber(), user.getAddress(),
          user.getAvatar());
      users.add(newUser);
    }
    return users;
  }

  public ArrayList<BookSummaryDTO> getUserBooks(int userId) throws SQLException {
    ArrayList<Book> books = bookDataBase.findMyBooks(userId);
    ArrayList<BookSummaryDTO> bookSummaries = new ArrayList<>();
    for (Book book : books) {
      BookSummaryDTO newBook = new BookSummaryDTO(book.getBookId(), book.getTitle(),
          book.getAuthor(), book.getIsbn(), book.getOwner().getUserName(), book.getFormat(), book.getGenre(),
          book.getStatus().toString(),
          book.getDescription(), book.getImage());
      bookSummaries.add(newBook);
    }
         return bookSummaries;
    }


  public void updateUser(FullUserDTO user) throws SQLException {
    User userToDb = new User(user.getUserName(),
        user.getEmail(), user.getFullName(), user.getPhoneNumber(),
        user.getAddress(), user.getAvatar());
    userDataBase.update(userToDb);
    //this does not update the userId, so it should be set in the DTO
  }


}
