package dataAccess;

import chess.ChessGame;
import dataAccess.DAOs.GameDAO;
import dataAccess.ServiceErrors.ServiceErrorAlreadyTaken;
import model.GameData;

import java.lang.reflect.Array;
import java.util.*;

public class MemoryGameDAO implements GameDAO {
    private static HashMap<Integer, GameData> data = new HashMap<>();
    public MemoryGameDAO(){
    }
    @Override
    public int createGame(String gameName) {
        Random random = new Random();
        int gameID = Math.abs(random.nextInt());
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(gameID, null, null, gameName, game);
        data.put(gameID, gameData);
        return gameID;
    }

    @Override
    public void deleteGame(int gameID) throws DataAccessException {
        if (data.isEmpty()){
            throw new DataAccessException("Game Not Found");
        }
        else if (data.containsKey(gameID)){
            data.remove(gameID);
        }
        else {
            throw new DataAccessException("Game Not Found");
        }
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        if (data.containsKey(gameID)){
            return data.get(gameID);
        }
        else {
            throw new DataAccessException("Game Not Found");
        }
    }

    @Override
    public String getWhiteUser(int gameID) throws DataAccessException {
        if (data != null && data.containsKey(gameID)){
            return data.get(gameID).whiteUsername();
        }
        else {
            return null;
        }
    }

    @Override
    public String getBlackUser(int gameID) {
        if (data != null && data.containsKey(gameID)){
            return data.get(gameID).blackUsername();
        }
        else {
            return null;
        }
    }

    @Override
    public void updateWhiteUser(int gameID, String whiteUsername) throws DataAccessException {
        if (data.containsKey(gameID)){
            var oldValue = data.get(gameID);
            var newValue = new GameData(oldValue.gameID(), whiteUsername, oldValue.blackUsername(), oldValue.gameName(),
                    oldValue.game());
            data.replace(gameID, newValue);
        }
        else {
            throw new DataAccessException("Game Not Found");
        }
    }

    @Override
    public void updateBlackUser(int gameID, String blackUsername) throws DataAccessException {
        if (data.containsKey(gameID)){
            var oldValue = data.get(gameID);
            var newValue = new GameData(oldValue.gameID(), oldValue.whiteUsername(), blackUsername, oldValue.gameName(),
                    oldValue.game());
            data.replace(gameID, newValue);
        }
        else {
            throw new DataAccessException("Game Not Found");
        }
    }

    public ArrayList<GameData> listGames(){
        ArrayList<GameData> games = new ArrayList<>();
        int counter = 0;
        for (int id : data.keySet()){
            GameData gameData = data.get(id);
            var newGame = new GameData(id, gameData.whiteUsername(), gameData.blackUsername(),
                                gameData.gameName(), null);
            games.add(newGame);
        }
        return games;
    }

    public Boolean findGameName(String gameName){
        for (Map.Entry<Integer, GameData> game : data.entrySet()){
            if (game.getValue().gameName().equals(gameName)){
                return true;
            }
        }
        return false;
    }

    public Boolean isColorTaken(String color,  int gameID){
        if (color.equals("WHITE")){
            return data.get(gameID).whiteUsername() != null;
        }
        if (color.equals("BLACK")){
            return data.get(gameID).blackUsername() != null;
        }
        return false;
    }

    @Override
    public void clear() {
        data.clear();
    }

    public int size(){
        return data.size();
    }
}
