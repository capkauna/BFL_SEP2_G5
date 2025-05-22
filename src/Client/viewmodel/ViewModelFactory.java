package viewmodel;

import repository.JdbcBookDAO;
import view.ViewHandler;


public class ViewModelFactory
{
  private final HomeVM homeVM;
  private final SearchVM searchVM;
  private final MyLibraryVM myLibraryVM;

  public ViewModelFactory() {
    //here is what is depeneded on what
    this.homeVM = new HomeVM(new ViewHandler(this));
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
}
