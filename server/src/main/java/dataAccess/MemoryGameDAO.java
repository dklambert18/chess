package dataAccess;

import dataAccess.DataAccessException;
import dataAccess.dataInterface.GameDAO;
import model.GameData;

import java.util.HashMap;

public class MemoryGameData implements GameDAO {
    private static HashMap<Integer, GameData> data;
    MemoryGameData(){
        data = null;
    }
    @Override
    public void createGame(String gameName) {
    }

    @Override
    public void deleteGame(int gameID) throws DataAccessException {

    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public String getWhiteUser(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public String getBlackUser(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void updateWhiteUser(int gameID) throws DataAccessException {

    }

    @Override
    public void updateBlackUser(int gameID) throws DataAccessException {

    }

    @Override
    public void clear() {
        data.clear();
    }
}
