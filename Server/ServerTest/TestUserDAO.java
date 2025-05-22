import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TestUserDAO
{
  private User testUser;

  @BeforeEach
  public void setUp()
  {
    // set up any shared resources or configurations
    testUser = new User("Bugs", "Bugs Bunny", "bugs@bunny.lt", "0000",
        "1234567890", "Bunny Street 20");
  }

  @Test
  public void testUserDAOConstructor() throws SQLException
  {
    User newUser = new User("Lola", "Lola Bunny", "lola@bunny.lt", "1111", "0987654321", "Bunny Street 21");
    //wip
  }





}
