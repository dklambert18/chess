package SQLServiceTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MySQLGameDAO;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SQLGameMethodTests {

    MySQLGameDAO gameDAO = new MySQLGameDAO();

    public SQLGameMethodTests() throws DataAccessException {
    }

    @BeforeEach
    void clear() throws DataAccessException {
        gameDAO.clear();
    }

    @Test
    void createGameSuccess(){
        gameDAO.createGame("game_name");
        assertEquals(1, gameDAO.size());
    }

    @Test
    void createGameFail(){
        gameDAO.createGame("game_name");
        assertThrows(Exception.class, () -> {gameDAO.createGame(null);});
    }

    @Test
    void getGame(){
        gameDAO.createGame("gamename");
        GameData game = gameDAO.getGame(1);
        assertEquals( new GameData(1, null, null, "gamename",
                new ChessGame()), game);
    }

    @Test
    void getGameFail(){
        gameDAO.createGame("gamename");
        assertNull(gameDAO.getGame(2));
    }

    @Test
    void updateWhiteUser() throws DataAccessException {
        gameDAO.createGame("gamename");
        gameDAO.updateWhiteUser(1, "white");
        assertEquals("white", gameDAO.getGame(1).whiteUsername());
    }

    @Test
    void updateWhiteUserFail() throws DataAccessException {
        gameDAO.createGame("gamename");
        gameDAO.updateWhiteUser(3, "white");
        assertNull(gameDAO.getGame(1).whiteUsername());
    }

    @Test
    void updateBlackUser() throws DataAccessException {
        gameDAO.createGame("gamename");
        gameDAO.updateBlackUser(1, "black");
        assertEquals("black", gameDAO.getGame(1).blackUsername());
    }

    @Test
    void updateBlackUserFail() throws DataAccessException {
        gameDAO.createGame("gamename");
        gameDAO.updateBlackUser(3, "black");
        assertNull(gameDAO.getGame(1).blackUsername());
    }

    @Test
    void isColorTaken() {
        gameDAO.createGame("gamename");
        assertFalse(gameDAO.isColorTaken("WHITE", 1));
        assertFalse(gameDAO.isColorTaken("BLACK", 1));
    }

    @Test
    void isColorTakenFail() throws DataAccessException {
        gameDAO.createGame("gamename");
        gameDAO.updateWhiteUser(2, "white");
        gameDAO.updateBlackUser(2, "black");
        assertFalse(gameDAO.isColorTaken("WHITE", 1));
        assertFalse(gameDAO.isColorTaken("BLACK", 1));
    }

    @Test
    void listGames() {
        gameDAO.createGame("gamename");
        var list = gameDAO.listGames();
        assertEquals(1, list.size());
    }

    @Test
    void listGamesFail() {
        assertThrows(Exception.class, () -> {gameDAO.createGame(null);});
        assertEquals(0, gameDAO.size());
    }
}
