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
}
