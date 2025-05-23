package Client.network;

public interface AuthServiceClient
{
  /**
   * Tries to log in with the given credentials.
   * @return true if server accepted the credentials
   * @throws Exception on network or protocol errors
   */
  boolean login(String username, String password) throws Exception;
}
