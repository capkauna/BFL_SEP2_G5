package Client.view;

import Client.viewmodel.WaitingListVM;
import Shared.dto.WaitingListEntryDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class WaitingListViewController {

  @FXML private TableView<WaitingListEntryDTO> waitingListTable;
  @FXML private TableColumn<WaitingListEntryDTO, Integer> bookIdColumn;
  @FXML private TableColumn<WaitingListEntryDTO, String> titleColumn;
  @FXML private TableColumn<WaitingListEntryDTO, Integer> userIdColumn;
  @FXML private TableColumn<WaitingListEntryDTO, String> userNameColumn;

  @FXML private Button removeButton;
  @FXML private Button lendButton;

  private WaitingListVM viewModel;

  public void init(WaitingListVM viewModel) {
    this.viewModel = viewModel;

    bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
    userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

    waitingListTable.setItems(viewModel.getEntries());
  }

  @FXML
  private void onRemoveClicked() {
    WaitingListEntryDTO selected = waitingListTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      viewModel.removeMeFromWaitingList(selected);
    }
  }

  @FXML
  private void onLendClicked() {
    WaitingListEntryDTO selected = waitingListTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      viewModel.lendBookTo(selected);
    }
  }
}
