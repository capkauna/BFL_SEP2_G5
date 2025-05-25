package Client.viewmodel;

import Server.repository.JdbcBookDAO;
import Client.view.ViewHandler;
import Client.viewmodel.*;


import java.sql.SQLException;

public class ViewModelFactory
{
  private final HomeVM homeVM;
  private final SearchVM searchVM;
  private final MyLibraryVM myLibraryVM;
  private JdbcBookDAO bookDAO;

  public ViewModelFactory() throws SQLException
  {
    //here is what is depeneded on what
    this.homeVM = new HomeVM();
    this.searchVM = new SearchVM(JdbcBookDAO.getInstance()); //connects to DAO
    this.myLibraryVM = new MyLibraryVM(JdbcBookDAO.getInstance(), new ViewHandler(this));
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
