package Client.viewmodel;

import Client.network.AuthServiceClient;
import Client.network.ClientSocketHandler;
import Client.view.ViewHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeVM
{
  private final ClientSocketHandler authClient;
  private final String username;


  public HomeVM(ClientSocketHandler authClient,String username) {
    this.authClient = authClient;
    this.username = username;
  }

}
