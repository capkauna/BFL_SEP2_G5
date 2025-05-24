package Server;

import Server.model.User;
import Server.repository.JdbcUserDAO;
import Server.repository.UserDAO;
import Shared.Requests.LoginRequest;
import Shared.Requests.LoginResponse;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    private static final int PORT = 8888;
    private static final UserDAO userDAO;

    static {
        try {
            userDAO = JdbcUserDAO.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize user DAO", e);
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())
            ) {
                Object request = in.readObject();

                if (request instanceof LoginRequest) {
                    LoginRequest loginRequest = (LoginRequest) request;
                    handleLogin(loginRequest, out);
                } else {
                    out.writeObject(new LoginResponse(false, "Unknown request type"));
                }

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        }

        private void handleLogin(LoginRequest request, ObjectOutputStream out) throws IOException {
            try {
                Optional<User> userOpt = userDAO.findByUserName(request.getUsername());

                List<User> usersOpt = userDAO.findAll();
                for (User user : usersOpt) {
                    System.out.println(user.getUserName());
                }

                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    if (user.validatePassword(request.getPassword())) {
                        out.writeObject(new LoginResponse(true, "Login successful", user.getUserId()));
                    } else {
                        out.writeObject(new LoginResponse(false, "Invalid username or password"));
                    }
                } else {
                    out.writeObject(new LoginResponse(false, "Invalid username or password"));
                }
            } catch (SQLException e) {
                System.err.println("Database error during login: " + e.getMessage());
                out.writeObject(new LoginResponse(false, "Server error occurred"));
            }
        }
    }
}