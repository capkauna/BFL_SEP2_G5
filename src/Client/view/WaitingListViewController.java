package Client.view;

import Client.viewmodel.WaitingListVM;
import Shared.dto.WaitingListEntryDTO;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class WaitingListViewController {
  @FXML TextField bookIdField;
  @FXML Label    statusLabel;
  @FXML Button addmebutton, okbutton, backButton, lendButton, remove;
  @FXML TableView<WaitingListEntryDTO> waitingListTable;
  @FXML TableColumn<WaitingListEntryDTO, String> usernameColumn, addedat;


  private WaitingListVM vm;
  private ViewHandler viewHandler;
  private boolean fromLendFlow = false;// Indicates if this view is opened from the lend flow
  private String        username;
  private int bookId;

  public void init(ViewHandler vh, WaitingListVM vm, String username, int bookId, boolean fromLendFlow) {
    this.viewHandler = vh;
    this.vm       = vm;
    this.username = username;
    this.bookId   = bookId;
    this.fromLendFlow = fromLendFlow;
    vm.setBookId(bookId);
    vm.loadListFromServer();

    lendButton.setVisible(fromLendFlow);

    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    addedat.setCellValueFactory(new PropertyValueFactory<>("addedAt"));

    waitingListTable.setItems(vm.getWaitingUsers());

  }



//NOT GOOD
  @FXML private void onAddClicked() {

    vm.addToWaitingList(); // now refreshes automatically
    statusLabel.setText("Added to waiting list!");
    addmebutton.setDisable(true);
  }

  @FXML private void onRemoveClicked() {
    //
  }
  @FXML private void onLendClicked() {
    // Logic to handle lending the book
    System.out.println("Lending book with ID: " + bookId);

  }

  @FXML private void onOkClicked() {
    // Logic to handle OK button click
    System.out.println("OK button clicked");
    statusLabel.setText("");
    viewHandler.openBookInfoView(bookId);
  }

  @FXML private void onBackClicked() {
    // Logic to handle Back button click
    System.out.println("Back button clicked");
    statusLabel.setText("");
    viewHandler.openBookInfoView(bookId);
  }
}
