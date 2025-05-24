// Server/network/RequestDispatcher.java
//import Server.service.AuthService;
//import java.util.HashMap;
//import java.util.Map;

//public class RequestDispatcher {
//  private final AuthService auth;
//  private final Map<Class<? extends Request>, RequestHandler<?>> handlers = new HashMap<>();
//
//  public RequestDispatcher(AuthService auth) {
//    this.auth = auth;
//    // register handlers:
//    handlers.put(LoginRequest.class,   new LoginRequestHandler());
//    handlers.put(UserInfoRequest.class,    new InfoRequestHandler());
//    // ...add more as you go
//  }
//
//  @SuppressWarnings("unchecked")
//  public Response dispatch(Request req) throws Exception {
//    RequestHandler<Request> handler = (RequestHandler<Request>)handlers.get(req.getClass());
//    if (handler == null) throw new IllegalArgumentException("No handler for " + req);
//    return handler.handle(req, auth);
//  }
//}
