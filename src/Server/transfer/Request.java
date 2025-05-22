package Server.transfer;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
  private String action;
  private HashMap<String, Object> data;

  public Request(String action) {
    this.action = action;
    this.data = new HashMap<>();
  }

  public String getAction() { return action; }

  public void putData(String key, Object value) {
    data.put(key, value);
  }

  public Object getData(String key) {
    return data.get(key);
  }
}
