package Shared.util;


import Server.model.User;

public class Session {

  // helping class, singltone for users. to see, who is login now

  private static User loggedInUser;

  public static void setLoggedInUser(User user) {
    loggedInUser = user;
  }

  public static User getLoggedInUser() {
    return loggedInUser;
  }
//TODO check this again
  public static void clear() {
    boolean b = loggedInUser == null;
  }

  public static boolean isLoggedIn() {
    return loggedInUser != null;
  }
}