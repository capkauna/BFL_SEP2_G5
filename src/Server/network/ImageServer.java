package Server.network;

import java.io.*;
import java.net.Socket;

public class ImageServer
{
  public static void main(String[] args) throws IOException
  {
    //ServerSocket serverSocket = new ServerSocket(1234);
    ServerSocket serverSocket = new ServerSocket();//temporary so I can test other things until the ServerSocket class is implemented
    System.out.println("processing picture");

    Socket clientSocket = serverSocket.accept();
    InputStream in = clientSocket.getInputStream();

    DataInputStream dataIn = new DataInputStream(in);

    int length = dataIn.readInt();
    byte[] imageBytes = new byte[length];
    dataIn.readFully(imageBytes);

    FileOutputStream fos = new FileOutputStream("picture.jpg");
    fos.write(imageBytes);
    fos.close();

    System.out.println("picture send");
    clientSocket.close();
    serverSocket.close();

  }
}
