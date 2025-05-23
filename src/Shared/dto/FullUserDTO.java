package Shared.dto;

// this is meant specifically for the main user view
public class FullUserDTO
{
  private String userName, fullName, email,phoneNumber, address, avatar;

  public FullUserDTO(String userName, String fullName, String email,  String phoneNumber, String address, String avatar)
  {
    this.userName = userName;
    this.fullName = fullName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.avatar = avatar;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public String getFullName()
  {
    return fullName;
  }

  public void setFullName(String fullName)
  {
    this.fullName = fullName;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber)
  {
    this.phoneNumber = phoneNumber;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getAvatar()
  {
    return avatar;
  }

  public void setAvatar(String avatar)
  {
    this.avatar = avatar;
  }
}
