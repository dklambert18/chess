package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAccess.MemoryAuthDAO;
import dataAccess.MemoryAccess.MemoryGameDAO;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import model.GameData;
import service.requestObjects.ListGamesRequest;
import service.responseObjects.ListGamesResponse;

import java.util.ArrayList;

public class ListGamesService {
    MemoryAuthDAO authDAO = new MemoryAuthDAO();
    MemoryGameDAO gameDAO = new MemoryGameDAO();

    public ListGamesResponse listGames(ListGamesRequest req) throws ServiceErrorUnauthorized, DataAccessException {
        ArrayList<GameData> gameList = new ArrayList<>();
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
