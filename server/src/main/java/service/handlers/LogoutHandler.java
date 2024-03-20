package service.handlers;

import dataAccess.DataAccessException;
import service.LogoutService;
import requestObjects.LogoutRequest;
import spark.Request;

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
