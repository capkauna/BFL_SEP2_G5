package network;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageClient
{
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost" , 1234);
    OutputStream out = socket.getOutputStream();

    // byte[] imageBytes = Files.readAllBytes(Paths.get(""));
    // here we need to put picture name like "picturename.jpg"

    DataOutputStream dataOut = new DataOutputStream(out);
    dataOut.writeInt(imageBytes.length);

    out.write(imageBytes);
    out.flush();

    System.out.println("");
    socket.close;
  }

}
