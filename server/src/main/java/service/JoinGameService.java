package service;

import dataAccess.*;
import dataAccess.ServiceErrors.ServiceErrorAlreadyTaken;
import dataAccess.ServiceErrors.ServiceErrorBadRequest;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import requestObjects.JoinGameRequest;
import responseObjects.JoinGameResponse;

public class JoinGameService {
    public MySQLAuthDAO authDAO = new MySQLAuthDAO();
    public MySQLGameDAO gameDAO = new MySQLGameDAO();

    public JoinGameService() throws DataAccessException {
    }

    public Object joinGame(JoinGameRequest req) throws Exception {
        if (req.getGameID() == 0){
            throw new ServiceErrorBadRequest();
        }

        var auth = req.getAuthToken();
        if (authDAO.getUser(auth) == null){
            throw new ServiceErrorUnauthorized();
        }

        var username = authDAO.getUser(auth);

        //check to see that the game exists
        if (gameDAO.getGame(req.getGameID()).gameName() == null){
            throw new ServiceErrorBadRequest();
        }

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
