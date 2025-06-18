package ServerTests;


import Server.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class UserWhiteBoxTest {
  private static Method validateRawPassword;
  private static Method validateUserName;
  private static Method validatePhoneNumber;

  @BeforeAll
  static void setUpReflection() throws Exception {
    //private static validators
    validateRawPassword    = User.class.getDeclaredMethod("validateRawPassword", String.class);
    validateUserName       = User.class.getDeclaredMethod("validateUserName", String.class);
    validatePhoneNumber    = User.class.getDeclaredMethod("validatePhoneNumber", String.class);

    validateRawPassword.setAccessible(true);
    validateUserName.setAccessible(true);
    validatePhoneNumber.setAccessible(true);
  }

  @Test
  void testPrivateValidateRawPasswordRejectsSpaces() {
    // calling the private static method directly
    InvocationTargetException ex = assertThrows(
        InvocationTargetException.class,
        () -> validateRawPassword.invoke(null, "bad pass")
    );
    assertTrue(ex.getCause() instanceof IllegalArgumentException);
  }

  @Test
  void testPrivateValidateUserNameBoundaries() throws Exception {
    // too short
    assertThrows(InvocationTargetException.class,
        () -> validateUserName.invoke(null, "abc"));
    // too long
    String longName = "thisusernameiswaytoolong";
    assertThrows(InvocationTargetException.class,
        () -> validateUserName.invoke(null, longName));
    // valid
    assertDoesNotThrow(() -> validateUserName.invoke(null, "normalName"));
  }

  @Test
  void testFromDbKeepsHashedPasswordIntact() {
    // fromDb bypasses raw‐password hashing
    String existingHash = "cafebabe";
    User u = User.fromDb(
        42,
        "janey",
        "Jane Doe",
        "jane@example.com",
        existingHash,
        "12345678",
        "Some address",
        null
    );
    assertEquals(existingHash, u.getPasswordHash());
  }

  @Test
  void testChangePasswordSuccessAndFailure() {
    // Use the public constructor that hashes the raw password
    User u = new User("joe", "secret");
    String oldHash = u.getPasswordHash();

    // wrong old password → exception
    assertThrows(IllegalArgumentException.class,
        () -> u.changePassword("wrong", "newpass"));

    // correct old password → hash changes
    assertDoesNotThrow(() -> u.changePassword("secret", "newpass"));
    assertNotEquals(oldHash, u.getPasswordHash());
  }

  @Test
  void testSetUserIdTwiceThrows() {
    User u = new User("joe", "pass");
    u.setUserId(100);
    assertThrows(IllegalStateException.class, () -> u.setUserId(200));
  }

  @Test
  void testPrivateValidatePhoneNumberEdgeCases() {
    // too short
    assertThrows(InvocationTargetException.class, () ->
        validatePhoneNumber.invoke(null, "12345"));
    // non-numeric
    assertThrows(InvocationTargetException.class, () ->
        validatePhoneNumber.invoke(null, "1234abcd"));
    // valid
    assertDoesNotThrow(() -> validatePhoneNumber.invoke(null, "12345678"));
  }
}
