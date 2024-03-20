package service;

import dataAccess.*;
import dataAccess.ServiceErrors.ServiceErrorBadRequest;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import requestObjects.CreateGameRequest;
import responseObjects.CreateGameResponse;

public class CreateGameService {
    MySQLAuthDAO authDAO = new MySQLAuthDAO();
    MySQLGameDAO gameDAO = new MySQLGameDAO();

    public CreateGameService() throws DataAccessException {
    }


    public CreateGameResponse createGame(CreateGameRequest req) throws ServiceErrorBadRequest, ServiceErrorUnauthorized, DataAccessException {
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

        var useless = gameDAO.getGame(0);
        return new CreateGameResponse(gameID);
    }
}
