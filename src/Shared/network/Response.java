package Shared.network;

import java.io.Serializable;

public class Response implements Serializable {
  private boolean success;
  private Object data;
  private String errorMessage;

  public Response(boolean success, Object data, String errorMessage) {
    this.success = success;
    this.data = data;
    this.errorMessage = errorMessage;
  }

  public boolean isSuccess() {
    return success;
  }

  public Object getData() {
    return data;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
