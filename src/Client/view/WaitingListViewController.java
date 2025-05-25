package Client.view;

import Client.viewmodel.WaitingListVM;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.*;

public class WaitingListViewController {
  @FXML TextField bookIdField;
  @FXML Label    statusLabel;
  @FXML Button addmebutton, okbutton;
  @FXML TableView waitingListTable;
  @FXML TableColumn<String, String> usernameColumn, addedat;





  private WaitingListVM vm;
  private String        username;

  public void init(ViewHandler vh, WaitingListVM vm, String username) {
    this.vm       = vm;
    this.username = username;
  }
//NOT GOOD
  @FXML private void onAddClicked() {
    int bookId = Integer.parseInt(bookIdField.getText());
    try {
      vm.addToWaitingList(bookId);
      statusLabel.setText("Added book #" + bookId + " to your waiting list!");
    } catch (Exception e) {
      statusLabel.setText("Error: " + e.getMessage());
    }
  }
}
