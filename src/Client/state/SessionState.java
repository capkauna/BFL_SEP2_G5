package Client.state;

import Shared.dto.FullUserDTO;

public class SessionState
{
  private static SessionState instance;
  private FullUserDTO loggedUser;

  private SessionState(){
  }

  public static SessionState getInstance(){
    if(instance == null){
      instance = new SessionState();
      return instance;
    }
    return instance;
  }

  public void setSessionUser(FullUserDTO loggedUser){
    this.loggedUser = loggedUser;
  }

  public FullUserDTO getLoggedUser(){
    return loggedUser;
  }
}
