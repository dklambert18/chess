package dataAccess;

import dataAccess.DAOInterfaces.GameDAO;
import dataAccess.DataAccessException;
import model.GameData;

public class MySQLGameDAO implements GameDAO {
    @Override
    public int createGame(String gameName) {
        return 0;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void updateWhiteUser(int gameID, String whiteUsername) throws DataAccessException {

    }

    @Override
    public void updateBlackUser(int gameID, String blackUsername) throws DataAccessException {

    }

    @Override
    public void clear() {

    }
}
