package service.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import service.LogoutService;
import service.requestObjects.LogoutRequest;
import service.responseObjects.LogoutResponse;
import spark.Request;

import java.io.Reader;
import java.util.Set;

public class LogoutHandler {

    public Object logout(Request req) throws DataAccessException {

        //convert the http request into the LoginRequest object.
        LogoutRequest logoutRequest = new LogoutRequest(req.headers("Authorization"));

        //create the logout service and logout
        LogoutService service = new LogoutService();

        try {
            return service.logout(logoutRequest);
        } catch (Exception e){
            return e;
        }
    }
}
