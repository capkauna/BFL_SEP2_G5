package Client.viewmodel;

import Client.network.ClientSocket;
import javafx.scene.control.Label;
import Shared.Requests.LoginResponse;
import Shared.Requests.LoginRequest;

import java.io.IOException;

public class LogInVM {
    private final ClientSocket socketClient;

    public LogInVM() {
        this.socketClient = ClientSocket.getInstance();
    }

    public void login(String username, String password, Label errorLabel) {
        try {
            LoginResponse response = socketClient.login(username, password);

            if (response.isSuccess()) {
                System.out.println("Login successful for user: " + username);
                errorLabel.setVisible(false);
                // TODO: launch main window with userId = response.getUserId()
            } else {
                System.out.println("Login failed: " + response.getMessage());
                errorLabel.setText(response.getMessage());
                errorLabel.setVisible(true);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            errorLabel.setText("Error connecting to server");
            errorLabel.setVisible(true);
        }
    }
}