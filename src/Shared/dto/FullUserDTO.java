package Shared.dto;

// this is meant specifically for the main user view
public class FullUserDTO
{
  private String userName, fullName, email,phoneNumber, address, avatar, moreInfo;

  public FullUserDTO(String userName, String fullName, String email,  String phoneNumber, String address, String avatar)
  {
    this.userName = userName;
    this.fullName = fullName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.avatar = avatar;
    this.moreInfo = moreInfo;
  }
  public String getUserName() {
    return userName;
  }

  public String getFullName() {
    return fullName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public String  getAvatar() {
    return avatar;
  }

  public String getMoreInfo() {
    return moreInfo;
  }

}
