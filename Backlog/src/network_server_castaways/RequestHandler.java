package network_server_castaways;

import Server.service.AuthService;
public interface RequestHandler<T extends Request> {
  Response handle(T request, AuthService auth) throws Exception;
}




