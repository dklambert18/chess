package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DAOInterfaces.GameDAO;
import model.GameData;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class MySQLGameDAO implements GameDAO {

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public int createGame(String gameName) {
        String statement = "INSERT INTO games (game_name, board) VALUES (?, ?)";
        ChessGame thing = new ChessGame();
        String game = serializeGame(thing);
        var gameID = 0;
        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, gameName);
            preparedStatement.setString(2, game);
            preparedStatement.executeUpdate() ;

            var info = preparedStatement.getGeneratedKeys();
            if (info.next()){
                gameID = info.getInt(1);
            }
            return gameID;
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameData getGame(int gameID) {
        String statement = "SELECT white_username, black_username, game_name, board FROM games where game_id = ?";
        String whiteUsername;
        String blackUsername;
        String gameName;
        String board;
        ChessGame gameObject;

        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, gameID);
            var info = preparedStatement.executeQuery();
            if (info.next()){
                whiteUsername = info.getString(1);
                blackUsername = info.getString(2);
                gameName = info.getString(3);
                board = info.getString(4);
                gameObject = deserializeGame(board);
            }
            else {
                return null;
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return new GameData(gameID, whiteUsername, blackUsername, gameName, gameObject);
    }

    @Override
    public void updateWhiteUser(int gameID, String whiteUsername) throws DataAccessException {
        String statement = "UPDATE games SET white_username = ? WHERE game_id = ?";
        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, whiteUsername);
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate();
        } catch (SQLException ignored){
        }
    }

    @Override
    public void updateBlackUser(int gameID, String blackUsername) throws DataAccessException {
        String statement = "UPDATE games SET black_username = ? WHERE game_id = ?";
        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, blackUsername);
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate();
        } catch (SQLException ignored){
        }
    }

    public boolean isColorTaken(String playerColor, int gameID){
        String statement = "SELECT white_username, black_username FROM games where game_id = ?";
        String whiteUsername;
        String blackUsername;

        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, gameID);
            var info = preparedStatement.executeQuery();
            if (info.next()){
                whiteUsername = info.getString(1);
                blackUsername = info.getString(2);
            }
            else {
                return false;
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    if ((Objects.equals(playerColor, "WHITE") && whiteUsername != null)) return true;

    else return Objects.equals(playerColor, "BLACK") && blackUsername != null;
    }

    public ArrayList<GameData> listGames() {
        String statement = "SELECT game_id, white_username, black_username, game_name FROM games";
        int gameID;
        String whiteUsername;
        String blackUsername;
        String gameName;
        ArrayList<GameData> finalList = new ArrayList<>();

        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            var info = preparedStatement.executeQuery();
            while (info.next()){
                gameID = info.getInt("game_id");
                whiteUsername = info.getString("white_username");
                blackUsername = info.getString("black_username");
                gameName = info.getString("game_name");
                GameData game = new GameData(gameID, whiteUsername, blackUsername, gameName, null);
                finalList.add(game);
            }
            return finalList;
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private ChessGame deserializeGame(String game){
        Gson gson = new Gson();
        return gson.fromJson(game, ChessGame.class);
    }

    private String serializeGame(ChessGame game){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(game, ChessGame.class);
    }

    String[] sqlString = {"""
            CREATE TABLE IF NOT EXISTS games (
            game_id int AUTO_INCREMENT PRIMARY KEY,
            white_username varchar(256),
            black_username varchar(256),
            game_name varchar(256) NOT NULL,
            board varchar(1000) NOT NULL
            );
            """};

    public int size(){
        var list = listGames();
        return list.size();
    }

    @Override
    public void clear() throws DataAccessException {
        String statement = "TRUNCATE TABLE games";
        try (var conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : sqlString) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
