package serviceTests;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearServiceTest {

    MySQLUserDAO userDAO = new MySQLUserDAO();
    MemoryGameDAO gameDAO = new MemoryGameDAO();
    MemoryAuthDAO authDAO = new MemoryAuthDAO();

    public ClearServiceTest() throws DataAccessException {
    }

    @Test
    void clear() throws DataAccessException {
        //Inserts a user into the userDAO.
        userDAO.createUser("username", "password", "email");

        //inserts a game into the gameDAO.
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(69, "white",
                "black", "game", game);
        gameDAO.createGame(gameData.gameName());

        //inserts an auth into the authDAO.
        authDAO.createAuth("username");

        //clear the app
        ClearService clearService = new ClearService();
        clearService.clearApp();

        //do the final checks to see if it cleared
//        assertEquals(0, userDAO.size());
//        assertEquals(0, gameDAO.size());
//        assertEquals(0, authDAO.size());
    }
}
