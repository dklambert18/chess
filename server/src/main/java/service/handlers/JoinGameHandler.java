package service.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.JoinGameService;
import requestObjects.JoinGameRequest;
import spark.Request;

public class JoinGameHandler {

    public Object joinGame(Request req) throws DataAccessException {
        var request = new Gson().fromJson(req.body(), JoinGameRequest.class);
        request.setAuthToken(req.headers("authorization"));

        JoinGameService service = new JoinGameService();

        try {
            return service.joinGame(request);
        } catch(Exception e){
            return e;
        }
    }
}
