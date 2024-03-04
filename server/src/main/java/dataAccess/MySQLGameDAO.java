package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DAOInterfaces.GameDAO;
import model.GameData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLGameDAO implements GameDAO {

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public int createGame(String gameName) {
        String statement = "INSERT INTO games (game_name, board) VALUES (?, ?)";
        String query = "SELECT gameID FROM games where game_name = ?";
        String board = new ChessGame().toString();
        var gameID = 0;
        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, gameName);
            preparedStatement.setString(2, board);
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
        String statement = "SELECT white_username, black_username, game_name, board FROM games where gameID = ?";
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
        String statement = "UPDATE games SET black_username = ? WHERE gameID = ?";
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
        String statement = "UPDATE games SET black_username = ? WHERE gameID = ?";
        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, blackUsername);
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate();
        } catch (SQLException ignored){
        }
    }

    private ChessGame deserializeGame(String game){
        Gson gson = new Gson();
        return gson.fromJson(game, ChessGame.class);
    }

    private String serializeGame(ChessGame game){
        Gson gson = new Gson();
        return gson.toJson(game, ChessGame.class);
    }

    String[] sqlString = {"""
            CREATE TABLE IF NOT EXISTS games (
              gameID int AUTO_INCREMENT NOT NULL,
              white_username varchar(256),
              black_username varchar(256),
              game_name varchar(256) NOT NULL,
              board varchar(512) NOT NULL,
              PRIMARY KEY (gameID)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """};

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
