package Client.view;

import Server.database.JdbcWaitingListDAO;
import Server.model.WaitingListRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WaitingListViewController {

  @FXML private TableView<WaitingListRecord> waitingListTable;
  @FXML private TableColumn<WaitingListRecord, Integer> userIdColumn;
  @FXML private TableColumn<WaitingListRecord, Integer> entryIdColumn;
  @FXML private TableColumn<WaitingListRecord, String> addedAtColumn;
  @FXML private Button addToListButton;
  @FXML private Button lendButton;
  @FXML private Button closeButton;

  private final ObservableList<WaitingListRecord> listData = FXCollections.observableArrayList();

  private int currentUserId = -1; // will be set from outside, or remain -1
  private int bookId = -1;
  private boolean isOwner = false;

  public void init(int bookId, int userId, boolean isOwner) {
    this.bookId = bookId;
    this.currentUserId = userId;
    this.isOwner = isOwner;

    lendButton.setDisable(!isOwner);
    addToListButton.setDisable(isOwner); // only non-owners can add themselves

    userIdColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getUserId()).asObject());
    entryIdColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getEntryId()).asObject());
    addedAtColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
        data.getValue().getAddedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

    refreshList();
  }

  private void refreshList() {
    try {
      List<WaitingListRecord> all = JdbcWaitingListDAO.getInstance().findAll();
      listData.setAll(all.stream().filter(w -> w.getBookId() == bookId).toList());
      waitingListTable.setItems(listData);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void onAddToListClicked() {
    if (currentUserId == -1 || bookId == -1) {
      System.out.println("User or Book ID is not set.");
      return;
    }
    try {
      JdbcWaitingListDAO.getInstance().addEntry(currentUserId, bookId);
      refreshList();
      addToListButton.setDisable(true);
      addToListButton.setText("Added to list");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void onLendClicked() {
    WaitingListRecord selected = waitingListTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      System.out.println("Lending book " + bookId + " to user " + selected.getUserId());
      try {
        JdbcWaitingListDAO.getInstance().removeEntryById(selected.getEntryId());
        refreshList();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  public void onCloseClicked() {
    Stage stage = (Stage) closeButton.getScene().getWindow();
    stage.close();
  }
}
