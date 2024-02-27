package dataAccess.DAOs;

import dataAccess.DataAccessException;
import model.GameData;

public interface GameDAO {
    int createGame(String gameName);
    void deleteGame(int gameID) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    String getWhiteUser(int gameID) throws DataAccessException;
    String getBlackUser(int gameID) throws DataAccessException;
    void updateWhiteUser(int gameID, String whiteUsername) throws DataAccessException;
    void updateBlackUser(int gameID, String blackUsername) throws DataAccessException;
    void clear();
}
