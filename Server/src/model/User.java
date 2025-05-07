package model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class User
{
  private String userName;
  private String fullName;
  private String email;
  private String passwordHash; //security
  private String phoneNumber;
  private String address;
  private String userAvatarPath;

  private final int id;
  private static final AtomicInteger nextId = new AtomicInteger(1);
  //adding id to account for the user being recognized even if they change their username
  //AtomicInteger is thread-safe and allows for concurrent access to the id

  //quality of life
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"); //simplifies some logic
  private static final int MAX_USERNAME_LENGTH = 20;
  private static final int MIN_USERNAME_LENGTH = 4;
  private static final int MAX_FULLNAME_LENGTH = 40;
  private static final int MAX_EMAIL_LENGTH = 35;
  private static final int MIN_PASSWORD_LENGTH = 4;
  private static final int MAX_PHONE_NUMBER_LENGTH = 11;
  private static final int MIN_PHONE_NUMBER_LENGTH = 8;

//constructors
  public User(String userName, String fullName, String email, String rawPassword, String phoneNumber, String address, String userAvatarPath)
  {
    validateUserName(userName);
    validateFullName(fullName);
    validateEmail(email);
    validateRawPassword(rawPassword);
    validatePhoneNumber(phoneNumber);
    validateAddress(address);
    if (userAvatarPath != null && userAvatarPath.isEmpty()) {
      throw new IllegalArgumentException("Avatar path, if provided, cannot be empty");
    }

    this.userName = userName;
    this.fullName = fullName;
    this.email = email;
    this.passwordHash = hashPassword(rawPassword);
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.userAvatarPath = userAvatarPath;
    this.id = nextId.getAndIncrement();
  }

  //constructor without userAvatarPath
  public User(String userName, String name, String email, String rawPassword, String phoneNumber, String address)
  {
    this(userName, name, email, rawPassword, phoneNumber, address, null);
  }

  //VALIDATORS

  private static void validateUserName(String userName)
  {
    if(userName == null || userName.isEmpty() || userName.contains(" ") ||userName.length() < MIN_USERNAME_LENGTH || userName.length() >= MAX_USERNAME_LENGTH)
    {
      throw new IllegalArgumentException("Username cannot be null, empty or contain spaces, and must be between " + MIN_USERNAME_LENGTH + " and " + MAX_USERNAME_LENGTH + " characters long");
    }
  }
  private static void validateFullName(String fullName)
  {
    if(fullName == null || fullName.isEmpty() || fullName.length() >= MAX_FULLNAME_LENGTH)
    {
      throw new IllegalArgumentException("Name cannot be null, empty, or be longer than 40 characters");
    }
  }
  private static void validateEmail(String email)
  {
    if(email == null || email.length() >= MAX_EMAIL_LENGTH || !EMAIL_PATTERN.matcher(email).matches() || email.contains(" "))
    {
      throw new IllegalArgumentException("Please add a valid e-mail address");
    }
  }
  private static void validateRawPassword(String password)
  {
    if(password == null || password.length() < MIN_PASSWORD_LENGTH || password.contains(" ") )
    {
      throw new IllegalArgumentException("Password must be at least 4 characters long and not contain spaces");
    }
  }
  private static void validatePhoneNumber(String phoneNumber)
  {
    if(phoneNumber == null ||
        phoneNumber.length() > MAX_PHONE_NUMBER_LENGTH ||
        phoneNumber.length() < MIN_PHONE_NUMBER_LENGTH ||
        !phoneNumber.matches("\\d+"))
    {
      throw new IllegalArgumentException("Phone number must be a numeric value between " + MIN_PHONE_NUMBER_LENGTH + " and " + MAX_PHONE_NUMBER_LENGTH + " digits long");
    }
  }
  private static void validateAddress(String address)
  {
    if(address == null || address.isEmpty())
    {
      throw new IllegalArgumentException("Address cannot be null or empty");
    }
  }



  public String getUserName()
  {
    return userName;
  }
  public void setUserName(String userName)
  {
    validateUserName(userName);
    this.userName = userName;
  }
  public String getFullName()
  {
    return fullName;
  }
  public void setFullName(String fullName)
  {
    validateFullName(fullName);
    this.fullName = fullName;
  }
  public String getEmail()
  {
    return email;
  }
  public void setEmail(String email)
  {
    validateEmail(email);
    this.email = email;
  }
  public String getPasswordHash()
  {
    return passwordHash;
  }
  public void setPassword(String password)
  {
    validateRawPassword(password);
    this.passwordHash = hashPassword(password);
  }
  public String getPhoneNumber()
  {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber)
  {
    validatePhoneNumber(phoneNumber);
    this.phoneNumber = phoneNumber;
  }
  public String getAddress()
  {
    return address;
  }
  public void setAddress(String address)
  {
    validateAddress(address);
    this.address = address;
  }
  public String getUserAvatarPath()
  {
    return userAvatarPath;
  }
  public void setUserAvatarPath(String userAvatarPath)
  {
    this.userAvatarPath = userAvatarPath;
  }
  public int getId()
  {
    return id;
  }

  //Utilities
  private String hashPassword(String p)
  {
    return Integer.toHexString(p.hashCode());
  }
  public boolean validatePassword(String password) //method to check if the password is correct
  {
    return this.getPasswordHash().equals(hashPassword(password));
  }


  @Override
  public String toString()
  {
    return "User{" +
            "userName='" + getUserName() + '\'' +
            ", name='" + getFullName() + '\'' +
            ", email='" + getEmail() + '\''  +
            ", phoneNumber='" + getPhoneNumber() + '\'' +
            ", address='" + getAddress() + '\'' +
            '}';
  }
}
