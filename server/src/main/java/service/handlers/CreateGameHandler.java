package service.handlers;

import com.google.gson.Gson;
import service.CreateGameService;
import service.requestObjects.CreateGameRequest;
import service.requestObjects.LogoutRequest;
import service.responseObjects.CreateGameResponse;
import spark.Request;

public class CreateGameHandler {

    public Object createGame(Request req){
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
