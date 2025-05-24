package Client.network;

import Shared.Requests.LoginRequest;
import Shared.Requests.LoginResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket {
    private static final String HOST = "localhost";
    private static final int PORT = 8888;

    private static ClientSocket instance;

    public static ClientSocket getInstance() {
        if (instance == null) {
            instance = new ClientSocket();
        }
        return instance;
    }

    public LoginResponse login(String username, String password) throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket(HOST, PORT)) {
            // Important: Create output stream first to prevent deadlock
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            LoginRequest request = new LoginRequest(username, password);
            out.writeObject(request);

            Object response = in.readObject();
            if (response instanceof LoginResponse) {
                return (LoginResponse) response;
            } else {
                throw new IOException("Unexpected response type");
            }
        }
    }
}