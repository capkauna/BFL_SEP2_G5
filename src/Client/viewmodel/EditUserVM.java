package Client.viewmodel;

import javafx.beans.property.*;

public class EditUserVM {
  private final StringProperty username = new SimpleStringProperty();
  private final StringProperty email = new SimpleStringProperty();
  private final StringProperty name = new SimpleStringProperty();
  private final StringProperty dob = new SimpleStringProperty();
  private final StringProperty phone = new SimpleStringProperty();
  private final StringProperty address = new SimpleStringProperty();
  private final StringProperty moreInfo = new SimpleStringProperty();
  private final StringProperty newPassword = new SimpleStringProperty();
  private final StringProperty currentPassword = new SimpleStringProperty();

  public StringProperty usernameProperty() { return username; }
  public StringProperty emailProperty() { return email; }
  public StringProperty nameProperty() { return name; }
  public StringProperty dobProperty() { return dob; }
  public StringProperty phoneProperty() { return phone; }
  public StringProperty addressProperty() { return address; }
  public StringProperty moreInfoProperty() { return moreInfo; }
  public StringProperty newPasswordProperty() { return newPassword; }
  public StringProperty currentPasswordProperty() { return currentPassword; }

  public void addImage() {
    // select image from file system
    System.out.println("Image added (dummy)");
  }

  public void submitChanges() {
    System.out.println("User changes submitted!");
    // send changes to server
  }
}