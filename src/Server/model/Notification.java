package Server.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Notification implements Serializable
{
  private int notificationId;
  private int userId;
  private String message;
  private LocalDateTime notificationDate;
  private int bookId;

  public Notification(int notificationId, int userId, String message, LocalDateTime notificationDate, int bookId) {
    this.notificationId = notificationId;
    this.userId = userId;
    this.message = message;
    this.notificationDate = notificationDate;
    this.bookId = bookId;
  }
  public int getNotificationId() {
    return notificationId;
  }
  public void setNotificationId(int notificationId) {
    this.notificationId = notificationId;
  }
  public int getUserId() {
    return userId;
  }
  public void setUserId(int userId) {
    this.userId = userId;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public LocalDateTime getNotificationDate() {
    return notificationDate;
  }
  public void setNotificationDate(LocalDateTime notificationDate) {
    this.notificationDate = notificationDate;
  }
  public int getBookId() {
    return bookId;
  }
  public void setBookId(int bookId) {
    this.bookId = bookId;
  }
  @Override
  public String toString() {
    return "Notification{" +
            "notificationId=" + notificationId +
            ", userId=" + userId +
            ", message='" + message + '\'' +
            ", notificationDate=" + notificationDate +
            ", bookId=" + bookId +
            '}';
  }

}
