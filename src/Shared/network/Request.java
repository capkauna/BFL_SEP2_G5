package Shared.network;

import Shared.dto.enums.Action;
import java.io.Serializable;

public class Request implements Serializable {
  private Action action;
  private Object payload;

  public Request(Action action, Object payload) {
    this.action = action;
    this.payload = payload;
  }

  public Action getAction() {
    return action;
  }

  public Object getPayload() {
    return payload;
  }
}
