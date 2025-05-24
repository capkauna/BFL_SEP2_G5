package Client.viewmodel;

import Shared.dto.FullUserDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserProfileVM
{
  private final StringProperty name = new SimpleStringProperty();
  private final StringProperty phone = new SimpleStringProperty();
  private final StringProperty email = new SimpleStringProperty();
  private final StringProperty address = new SimpleStringProperty();
  private final StringProperty moreInfo = new SimpleStringProperty();
  private final StringProperty avatar = new SimpleStringProperty();

  public UserProfileVM(FullUserDTO user) {
    name.set(user.getUserName());
    phone.set(user.getPhoneNumber());
    email.set(user.getEmail());
    address.set(user.getAddress());
    moreInfo.set(user.getMoreInfo());
    avatar.set(user.getAvatar());
  }

  public StringProperty nameProperty() { return name; }
  public StringProperty phoneProperty() { return phone; }
  public StringProperty emailProperty() { return email; }
  public StringProperty addressProperty() { return address; }
  public StringProperty moreInfoProperty() { return moreInfo; }
  public StringProperty avatarProperty() { return avatar; }


  public String getName() { return name.get(); }
  public String getPhone() { return phone.get(); }
  public String getEmail() { return email.get(); }
  public String getAddress() { return address.get(); }
  public String getMoreInfo() { return moreInfo.get(); }
  public String getAvatar() { return avatar.get(); }
}

