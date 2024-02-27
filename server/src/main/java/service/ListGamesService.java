package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import model.GameData;
import service.requestObjects.ListGamesRequest;
import service.responseObjects.ListGamesResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
