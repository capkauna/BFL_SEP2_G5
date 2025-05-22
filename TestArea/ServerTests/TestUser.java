package ServerTests;

import Server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser
{
  private User testUser;

  @BeforeEach
  public void setUp()
  {
    // set up any shared resources or configurations
    testUser = new User("janey", "Jane Doe", "janedoe@mail.com",
        "1234", "5252525252", "Janes Street 20");
  }
  @Test
  public void testUserConstructor()
  {
    User user = new User("testUser", "Test model.User", "user@email.com", "0000", "1234567890", "123 Test St");
    assertEquals ("testUser", user.getUserName());
  }

  @Test
  public void testNullParametersUserConstructor()
  {
    assertThrows(IllegalArgumentException.class,
        () -> new User(null, null, null, null, null, null));
  }
  @Test
  public void testEmptyParametersUserConstructor()
  {
    assertThrows(IllegalArgumentException.class,
        () -> new User("", "", "", "", "", ""));
  }
  //username tests
  @Test
  public void testGoodUserName()
  {
    testUser.setUserName("goodusername");
  }
  @Test
  public void testNullUserName()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setUserName(null));
  }
  @Test
  public void testEmptyUserName()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setUserName(""));
  }
  @Test
  public void testSpaceUserName()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setUserName("user name"));
  }
  @Test
  public void testLongUserName()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setUserName("thisusernameiswaytoolong"));
  }

  //name tests
  @Test
  public void testGoodName()
  {
    testUser.setFullName("good name");
  }
  @Test
  public void testNullName()
  {
   assertThrows (IllegalArgumentException.class, () -> testUser.setFullName(null));
  }
  @Test
  public void testEmptyName()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setFullName(""));
  }
  @Test
  public void testLongName()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setFullName("thisnameiswaytoolongandshouldthrowanerror"));
  }

  //e-mail tests
  @Test
  public void testGoodEmail()
  {
    testUser.setEmail("goodemail@mail.com");
  }
  @Test
  public void testNullEmail()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setEmail(null));
  }
  @Test
  public void testEmptyEmail()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setEmail(""));
  }
  @Test
  public void testInvalidEmail()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setEmail("invalidemail"));
  }


  //password tests
  @BeforeEach
  void setUp() {
    testUser = new User("1234");
  }

  @Test
  public void testShortPassword()
  {
    assertThrows (IllegalArgumentException.class, () -> new User("22"));
  }
  @Test
  public void testNullPassword()
  {
    assertThrows (IllegalArgumentException.class, () -> new User(null));
  }
  @Test
  public void testEmptyPassword()
  {
    assertThrows (IllegalArgumentException.class, () -> new User(""));
  }
  @Test
  public void testValidPassword()
  {
   assertTrue(testUser.validatePassword("1234"));
   assertFalse(testUser.validatePassword("0000"));
  }
  @Test
  public void testInvalidPassword()
  {
    assertThrows (IllegalArgumentException.class, () -> new User ("1234 5678"));
  }

  //phone number tests
  @Test
  public void testGoodPhoneNumber()
  {
    testUser.setPhoneNumber("1234567890");
    assertEquals("1234567890", testUser.getPhoneNumber());
  }
  @Test
  public void testNullPhoneNumber()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setPhoneNumber(null));
  }
  @Test
  public void testEmptyPhoneNumber()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setPhoneNumber(""));
  }
  @Test
  //number too short
  public void testInvalidPhoneNumber()
  {
   assertThrows (IllegalArgumentException.class, () -> testUser.setPhoneNumber("1234567"));
  }
  @Test
  //number too long
  public void testInvalidPhoneNumber2()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setPhoneNumber("123456789011234567"));
  }
  @Test
  //number contains letters or spaces
  public void testInvalidPhoneNumber3()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setPhoneNumber("123456789a"));
    assertThrows(IllegalArgumentException.class, () -> testUser.setPhoneNumber("123 456 7890"));
  }
  //address tests
  @Test
  public void testGoodAddress()
  {
    testUser.setAddress("123 Test St");
    assertEquals("123 Test St", testUser.getAddress());
  }
  @Test
  public void testNullAddress()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setAddress(null));
  }
  @Test
  public void testEmptyAddress()
  {
    assertThrows (IllegalArgumentException.class, () -> testUser.setAddress(""));
  }




}
