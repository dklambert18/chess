package service;

import dataAccess.MemoryAccess.MemoryAuthDAO;
import dataAccess.MemoryAccess.MemoryGameDAO;
import dataAccess.ServiceErrors.ServiceErrorAlreadyTaken;
import dataAccess.ServiceErrors.ServiceErrorBadRequest;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import service.requestObjects.JoinGameRequest;
import service.responseObjects.JoinGameResponse;

public class JoinGameService {
    public MemoryAuthDAO authDAO = new MemoryAuthDAO();
    public MemoryGameDAO gameDAO = new MemoryGameDAO();

    public Object joinGame(JoinGameRequest req) throws Exception {
        if (req.getGameID() == 0){
            throw new ServiceErrorBadRequest();
        }

        var auth = req.getAuthToken();
        if (authDAO.getUser(auth) == null){
            throw new ServiceErrorUnauthorized();
        }

        var username = authDAO.getUser(auth);

        //check to see if the game name is taken in the gameDAO.
        if (gameDAO.isColorTaken(req.getPlayerColor(), req.getGameID())){
            throw new ServiceErrorAlreadyTaken();
        }

        //now we can finally have the user join the game or whatever.
        if (req.getPlayerColor().equals("WHITE")){
            gameDAO.updateWhiteUser(req.getGameID(), username);
        }
        else {
            gameDAO.updateBlackUser(req.getGameID(), username);
        }
        return new JoinGameResponse();
    }

}
