package dataAccess.DAOInterfaces;

import dataAccess.DataAccessException;
import model.GameData;

public interface GameDAO {
    int createGame(String gameName);
    GameData getGame(int gameID) throws DataAccessException;
    void updateWhiteUser(int gameID, String whiteUsername) throws DataAccessException;
    void updateBlackUser(int gameID, String blackUsername) throws DataAccessException;
    void clear();
}
