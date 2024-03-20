package service;

import dataAccess.*;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import model.GameData;
import requestObjects.ListGamesRequest;
import responseObjects.ListGamesResponse;

import java.util.ArrayList;

public class ListGamesService {
    MySQLAuthDAO authDAO = new MySQLAuthDAO();
    MySQLGameDAO gameDAO = new MySQLGameDAO();

    public ListGamesService() throws DataAccessException {
    }

    public ListGamesResponse listGames(ListGamesRequest req) throws ServiceErrorUnauthorized, DataAccessException {
        ArrayList<GameData> gameList;
        var user = authDAO.getUser(req.authToken());

        if (user == null){
            throw new ServiceErrorUnauthorized();
        }

        else {
            gameList = gameDAO.listGames();
        }
        return new ListGamesResponse(gameList);
    }
}
