package dataAccess.dataInterface;

import chess.ChessGame;
import dataAccess.DataAccessException;
import model.GameData;

public interface GameDAO {
    void createGame(String gameName);
    void deleteGame(int gameID) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    String getWhiteUser(int gameID) throws DataAccessException;
    String getBlackUser(int gameID) throws DataAccessException;
    void updateWhiteUser(int gameID) throws DataAccessException;
    void updateBlackUser(int gameID) throws DataAccessException;
    void clear();
}
