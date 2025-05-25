package Server.service;


import Server.database.*;

import java.sql.SQLException;

public class UserInfoService
{
  private final UserDAO userDataBase;
  private final BookDAO bookDataBase;

  public UserInfoService(UserDAO userDataBase)  throws SQLException
  {
    this.userDataBase = JdbcUserDAO.getInstance();
    this.bookDataBase = JdbcBookDAO.getInstance();
  }



}
