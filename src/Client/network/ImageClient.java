package Client.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ImageClient
{
  public static void main(String[] args) throws IOException
  {
    Socket socket = new Socket("localhost" , 1234);
    OutputStream out = socket.getOutputStream();

    // byte[] imageBytes = Files.readAllBytes(Paths.get(""));
    // here we need to put picture name like "picturename.jpg"
    /*
    DataOutputStream dataOut = new DataOutputStream(out);
    dataOut.writeInt(imageBytes.length);

    out.write(imageBytes);
    out.flush();

    System.out.println("");
    socket.close;

     */
  }

}
