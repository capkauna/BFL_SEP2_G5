package Client.viewmodel;

import Server.repository.JdbcBookDAO;
import Client.view.ViewHandler;
import Client.viewmodel.*;


import java.sql.SQLException;

public class ViewModelFactory
{
  private final LogInVM logInVM;
  private final HomeVM homeVM;
  private final SearchVM searchVM;
  private final MyLibraryVM myLibraryVM;
  private JdbcBookDAO bookDAO;

  public ViewModelFactory(ViewHandler viewHandler) throws SQLException
  {
    //here is what is depeneded on what
    this.logInVM = new LogInVM();
    this.homeVM = new HomeVM();
    this.searchVM = new SearchVM(JdbcBookDAO.getInstance()); //connects to DAO
    this.myLibraryVM = new MyLibraryVM(JdbcBookDAO.getInstance());
  }

  public  LogInVM getLogInVM() {
    return logInVM;
  }

  public HomeVM getHomeVM() {
    return homeVM;
  }

  public SearchVM getSearchVM() {
    return searchVM;
  }

  public MyLibraryVM getMyLibraryVM()
  {
    return myLibraryVM;
  }

  public BookInfoVM getBookInfoVM() {
    return new BookInfoVM();
  }

}
