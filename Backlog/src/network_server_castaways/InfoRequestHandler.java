//package network_server_castaways;// Server/network/InfoRequestHandler.java
//import Server.service.AuthService;
//import java.util.Optional;
//import Server.model.User;
//
//public class InfoRequestHandler implements RequestHandler<UserInfoRequest>
//{
//  @Override
//  public UserInfoResponse handle(UserInfoRequest req, AuthService auth) throws Exception {
//    Optional<User> opt = auth.getUserByUsername(req.username);
//    if (opt.isEmpty()) return new UserInfoResponse();
//    User u = opt.get();
//    return new UserInfoResponse(
//        u.getUserName(),
//        u.getFullName(),
//        u.getEmail(),
//        u.getPhoneNumber(),
//        u.getAddress(),
//        u.getAvatar()
//    );
//  }
//}