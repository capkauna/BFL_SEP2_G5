package Server.model;

public class UserSummary //meant to be a summary of the user information
{
  private final String name, fullName, address;


  public UserSummary(String name, String fullName, String address )
  {
    this.name = name;
    this.fullName = fullName;
    this.address = address;
  }

  public String getName()
  {
    return name;
  }

  public String getFullName()
  {
    return fullName;
  }

  public String getAddress()
  {
    return address;
  }

}
