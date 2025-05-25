package Shared.dto.enums;

public enum Action {
  LOGIN("LOGIN"),
  REGISTER("REGISTER"),
  LOGOUT("LOGOUT"),
  ADD_TO_WAITING_LIST("ADD_TO_WAITING_LIST"),
  GET_USER_INFO("GET_USER_INFO"),
  GET_BOOK_INFO("GET_BOOK_INFO"),
  REMOVE_FROM_WAITING_LIST("REMOVE_FROM_WAITING_LIST"),
  LEND_BOOK("LEND_BOOK"),
  RETURN_BOOK("RETURN_BOOK"),
  CONFIRM_RETURN("CONFIRM_RETURN"),
  VIEW_BOOK_DETAILS("VIEW_BOOK_DETAILS"),
  MARK_AS_READ("MARK_AS_READ"),
  GET_WAITING_LIST("GET_WAITING_LIST"),
  GET_ALL_BOOKS("GET_ALL_BOOKS"),
  GET_MY_BOOKS("GET_MY_BOOKS");

  private final String action;

  Action(String action) {
    this.action = action;
  }
  public String toString() {
    return action;
  }

}
