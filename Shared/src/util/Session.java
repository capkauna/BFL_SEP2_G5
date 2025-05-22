package util;

import model.User;

public class Session {

  // helping class, singltone for users. to see, who is login now

  private static User loggedInUser;

  public static void setLoggedInUser(User user) {
    loggedInUser = user;
  }

  public static User getLoggedInUser() {
    return loggedInUser;
  }

  public static void clear() {
    loggedInUser == null;
  }

  public static boolean isLoggedIn() {
    return loggedInUser != null;
  }
}
