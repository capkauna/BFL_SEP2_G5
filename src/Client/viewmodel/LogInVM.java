package Client.viewmodel;

import Client.network.ClientSocketHandler;
import Shared.dto.enums.Action;
import Shared.network.Request;
import Shared.network.Response;

public class LogInVM {
  private final ClientSocketHandler socketHandler;
  private final ViewModelFactory factory;

  public LogInVM(ClientSocketHandler socketHandler, ViewModelFactory factory) {
    this.socketHandler = socketHandler;
    this.factory = factory;
  }

  public boolean login(String username, String password) {
    try {
      // 🔁 Send credentials as a String array
      String[] credentials = new String[]{username, password};
      Request request = new Request(Action.LOGIN, credentials);

      socketHandler.sendRequest(request);
      Response response = socketHandler.readResponse();

      if (response.isSuccess()) {
        factory.setCurrentUsername(username);  // You could also extract it from the response if needed
        return true;
      } else {
        System.err.println("Login failed: " + response.getErrorMessage());
        return false;
      }

    } catch (Exception e) {
      System.err.println("Login failed: " + e.getMessage());
      return false;
    }
  }
}
