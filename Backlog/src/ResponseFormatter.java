public class ResponseFormatter {
  public static String format(Response resp) {
    return switch (resp) {
      case LoginResponse lr ->
          lr.success
              ? "OK:" + lr.username
              : "FAIL";

      case UserInfoResponse ir -> {
        if (!ir.success) yield "FAIL";
        yield String.join(":",
            "OK",
            ir.userName,
            ir.fullName,
            ir.email,
            ir.phone,
            ir.address,
            ir.avatar
        );
      }

      // when you add new Response types, just add another case here:
      // case NewResponse nr -> ...

      default ->
          throw new IllegalArgumentException("Unknown response type: " + resp);
    };
  }
}
