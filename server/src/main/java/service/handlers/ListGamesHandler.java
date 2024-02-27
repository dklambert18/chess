package service.handlers;

import service.ListGamesService;
import service.requestObjects.ListGamesRequest;
import service.requestObjects.LogoutRequest;
import service.responseObjects.ListGamesResponse;
import spark.Request;

public class ListGamesHandler {


    public Object listGames(Request req) {
        //convert Spark request to a listGamesRequest
        ListGamesRequest requestObject = new ListGamesRequest(req.headers("Authorization"));

        //create the list games service. return the response from the request or at least try to
        ListGamesService service = new ListGamesService();
        try {
            return service.listGames(requestObject);
        }
        catch (Exception e){
            return e;
        }
    }
}
