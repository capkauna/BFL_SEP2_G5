package Server.model;

import java.util.*;

public class WaitingListManager {
  private final Map<Book, List<WaitingList>> waitingLists = new HashMap<>();// Map of books to their waiting lists

  public void addToWaitingList(Book book, User user) { // Add a user to the waiting list for a book
    waitingLists.putIfAbsent(book, new ArrayList<>());

    List<WaitingList> list = waitingLists.get(book);
    boolean alreadyExists = list.stream().anyMatch(e -> e.getUser().equals(user)); // Check if the user is already on the waiting list

    if (alreadyExists) {
      throw new IllegalArgumentException("User is already on the waiting list.");
    }

    list.add(new WaitingList(user));
  }

  public void removeFromWaitingList(Book book, User user) {
    if (waitingLists.containsKey(book)) {
      waitingLists.get(book).removeIf(entry -> entry.getUser().equals(user));
    }
  }

  public List<WaitingList> getWaitingList(Book book) {
    return waitingLists.containsKey(book)
        ? Collections.unmodifiableList(waitingLists.get(book))
        : Collections.emptyList();
  }
}