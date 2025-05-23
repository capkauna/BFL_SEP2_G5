package Client.viewmodel;

import Client.network.AuthServiceClient;
import Shared.dto.FullUserDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.naming.AuthenticationException;

public class UserProfileVM
{
  private final AuthServiceClient service;
  public final StringProperty userName  = new SimpleStringProperty();
  public final StringProperty fullName  = new SimpleStringProperty();
  public final StringProperty email     = new SimpleStringProperty();
  public final StringProperty phoneNumber = new SimpleStringProperty();
  public final StringProperty address   = new SimpleStringProperty();
  public final StringProperty avatar    = new SimpleStringProperty();

  public UserProfileVM(AuthServiceClient service)
  {
    this.service = service;
  }
  public void loadUserInfo(String username) throws Exception
  {
    try {
    FullUserDTO info = service.getUserInfo(username);
    userName.set(info.getUserName());
    fullName.set(info.getFullName());
    email.set(info.getEmail());
    phoneNumber.set(info.getPhoneNumber());
    address.set(info.getAddress());
    avatar.set(info.getAvatar());

  } catch (AuthenticationException e) {

    // show error in UI, log, etc.
    System.out.println("Authentication failed: " + e.getMessage());


  }
  }
}
