package service.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.CreateGameService;
import requestObjects.CreateGameRequest;
import spark.Request;

public class CreateGameHandler {

    public Object createGame(Request req) throws DataAccessException {
        //parse the json body with gameName into a separate request object.
        CreateGameRequest request = new Gson().fromJson(req.body(), CreateGameRequest.class);

        //set the authToken to the request "authToken" value.
        request.setAuthToken(req.headers("Authorization"));

        //now send the request to the service.
        CreateGameService service = new CreateGameService();

        try{
            return service.createGame(request);
        }
        catch(Exception e){
            return e;
        }
    }
}
