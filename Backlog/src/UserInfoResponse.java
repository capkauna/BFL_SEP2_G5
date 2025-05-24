public class UserInfoResponse implements Response {
  public final boolean success;
  // these only set if success:
  public final String userName, fullName, email, phone, address, avatar;
  public UserInfoResponse(String u, String f, String e, String p, String a, String av) {
    this.success   = true;
    this.userName  = u;
    this.fullName  = f;
    this.email     = e;
    this.phone     = p;
    this.address   = a;
    this.avatar    = av;
  }
  public UserInfoResponse() {
    this.success = false; this.userName = this.fullName = this.email = this.phone = this.address = this.avatar = null;
  }
}
