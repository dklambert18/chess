package service.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.LoginService;
import requestObjects.LoginRequest;
import spark.Request;

public class LoginHandler {

    public Object login(Request request) throws DataAccessException {
        LoginRequest req = new Gson().fromJson(request.body(), LoginRequest.class);

        LoginService service = new LoginService();
        try {
            return service.login(req);
        } catch (Exception e){
            return e;
        }
    }
}
