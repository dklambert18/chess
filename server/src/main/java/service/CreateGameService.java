package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.ServiceErrors.ServiceErrorBadRequest;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import service.requestObjects.CreateGameRequest;
import service.responseObjects.CreateGameResponse;

public class CreateGameService {
    MemoryAuthDAO authDAO = new MemoryAuthDAO();
    MemoryGameDAO gameDAO = new MemoryGameDAO();


    public CreateGameResponse createGame(CreateGameRequest req) throws ServiceErrorBadRequest, ServiceErrorUnauthorized {
        //check for a bad request
        if (req.getGameName() == null){
            throw new ServiceErrorBadRequest();
        }

        //check for authorization
        var user = authDAO.getUser(req.getAuthToken());

        //check if authorization is empty, if it is, send back a different error code.
        if (user == null){
            throw new ServiceErrorUnauthorized();
        }

        //now we can perform the operation and send back the gameID.
        //create game
        var gameID = gameDAO.createGame(req.getGameName());

        //check to see if the
        return new CreateGameResponse(gameID);
    }
}
