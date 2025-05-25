package Client.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EditBookVM
{
  private final StringProperty title = new SimpleStringProperty();
  private final StringProperty author = new SimpleStringProperty();
  private final StringProperty isbn = new SimpleStringProperty();
  private final StringProperty genre = new SimpleStringProperty();
  private final StringProperty format = new SimpleStringProperty();
  private final StringProperty description = new SimpleStringProperty();
  private final StringProperty imagePath = new SimpleStringProperty();
  private final StringProperty errorMessage = new SimpleStringProperty();

  private String currentUserName;

  public EditBookVM(String username) {
    this.currentUserName = username;
    //TODO: fetch book info from server, if it exists
  }

  public boolean validateAndSave() {
    if (isEmpty(title.get()) ||  isEmpty(author.get()) || isEmpty(isbn.get()) ||
    isEmpty(genre.get()) ||  isEmpty(format.get()) || isEmpty(description.get())) {
      errorMessage.set("All fields must be filled.");
      return false;
    }

    // вызов DAO
    System.out.println("Saving book:");
    System.out.println("Title: " + title.get());
    System.out.println("Author: " + author.get());
    // TODO: сохранить в БД

    return true;
  }

  private boolean isEmpty(String value) {
    return value == null || value.trim().isEmpty();
  }

  public StringProperty titleProperty() {
    return title;
  }

  public StringProperty authorProperty() {
    return author;
  }

  public StringProperty isbnProperty() {
    return isbn;
  }

  public StringProperty genreProperty() {
    return genre;
  }

  public StringProperty formatProperty() {
    return format;
  }

  public StringProperty descriptionProperty() {
    return description;
  }

  public StringProperty imagePathProperty() {
    return imagePath;
  }

  public StringProperty errorMessageProperty() {
    return errorMessage;
  }
}