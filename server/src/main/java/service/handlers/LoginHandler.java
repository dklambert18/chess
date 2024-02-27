package service.handlers;

import com.google.gson.Gson;
import service.LoginService;
import service.requestObjects.LoginRequest;
import service.responseObjects.LoginResponse;
import spark.Request;

public class LoginHandler {

    public Object login(Request request){
        LoginRequest req = new Gson().fromJson(request.body(), LoginRequest.class);

        LoginService service = new LoginService();
        try {
            return service.login(req);
        } catch (Exception e){
            return e;
        }
    }
}
