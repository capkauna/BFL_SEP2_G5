package DataClasses;

import java.util.Objects;
import java.util.stream.Stream;

public class User
{
  private String userName;
  private String name;
  private String email;
  private String password;
  private String phoneNumber;
  private String address;
  private Library userLibrary;

  public User(String userName, String name, String email, String password, String phoneNumber, String address)
  {
    if( Stream.of(userName, name, email, password, phoneNumber, address).anyMatch(Objects::isNull)
        || Stream.of(userName, name, email, password, phoneNumber, address).anyMatch(String::isEmpty))
    {
      throw new IllegalArgumentException("User fields cannot be null or empty");
    }
    this.userName = userName;
    this.name = name;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.userLibrary = new Library(null);
  }
  public String getUserName()
  {
    return userName;
  }
  public void setUserName(String userName)
  {
    if(userName == null || userName.isEmpty() || userName.contains(" ") || userName.length() >= 20)
    {
      throw new IllegalArgumentException("Username cannot be null, empty, contain spaces or be longer than 20 characters");
    }
    this.userName = userName;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    if(name == null || name.isEmpty() || name.length() >= 40)
    {
      throw new IllegalArgumentException("Name cannot be null, empty, or be longer than 40 characters");
    }
    this.name = name;
  }
  public String getEmail()
  {
    return email;
  }
  public void setEmail(String email)
  {
    if(email == null || email.length() >= 35 || !email.contains("@") || !email.contains(".") || email.contains(" "))
    {
      throw new IllegalArgumentException("Please add a valid e-mail address");
    }
    this.email = email;
  }
  public String getPassword()
  {
    return password;
  }
  public void setPassword(String password)
  {
    if(password == null || password.isEmpty())
    {
      throw new IllegalArgumentException("Password cannot be null or empty");
    }
    if(password.length() < 4 || password.contains(" "))
    {
      throw new IllegalArgumentException("Password must be at least 4 characters long or contain spaces");
    }
    this.password = password;
  }
  public boolean validatePassword(String password)
  {
    return this.getPassword().equals(password);
  }
  public String getPhoneNumber()
  {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber)
  {
    if(phoneNumber == null || phoneNumber.isEmpty())
    {
      throw new IllegalArgumentException("Phone number cannot be null or empty");
    }
    if(!phoneNumber.matches("\\d{10}"))
    {
      throw new IllegalArgumentException("Phone number must be 10 digits long");
    }
    this.phoneNumber = phoneNumber;
  }
  public String getAddress()
  {
    return address;
  }
  public void setAddress(String address)
  {
    if(address == null || address.isEmpty())
    {
      throw new IllegalArgumentException("Address cannot be null or empty");
    }
    this.address = address;
  }
  @Override
  public String toString()
  {
    return "User{" +
            "userName='" + userName + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", address='" + address + '\'' +
            '}';
  }
}
