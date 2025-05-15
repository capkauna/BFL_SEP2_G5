package dto.enums;

public enum BookStatus
{
  AVAILABLE ("AVAILABLE"),
  UNAVAILABLE("UNAVAILABLE"),
  BORROWED("BORROWED");

  private String status;

  BookStatus (String status) {
    this.status = status;
  }
  public String getStatus() {
    return status;
  }
}
