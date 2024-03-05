package serviceTests;

import dataAccess.*;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CreateGameService;
import service.ListGamesService;
import service.RegisterService;
import service.requestObjects.CreateGameRequest;
import service.requestObjects.ListGamesRequest;
import service.requestObjects.RegisterRequest;
import service.responseObjects.RegisterResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListGamesServiceTests {
    MySQLUserDAO userDAO = new MySQLUserDAO();
    MySQLGameDAO gameDAO = new MySQLGameDAO();
    MySQLAuthDAO authDAO = new MySQLAuthDAO();

    public ListGamesServiceTests() throws DataAccessException {
    }

    @BeforeEach
    void clear() throws DataAccessException {
        gameDAO.clear();
        authDAO.clear();
        userDAO.clear();
    }

    @Test
    void success() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "password",
                "email");
        RegisterService registerService = new RegisterService();
        Object registerResponse = registerService.register(registerRequest);
        var thing = (RegisterResponse) registerResponse;
        var auth = thing.authToken();

        CreateGameRequest createGameRequest = new CreateGameRequest("game", auth);
        CreateGameService gameService = new CreateGameService();
        int gameID = gameService.createGame(createGameRequest).gameID();

        CreateGameRequest createGameRequest1 = new CreateGameRequest("teehee", auth);
        int gameID1 = gameService.createGame(createGameRequest1).gameID();

        ListGamesService service = new ListGamesService();
        var result = service.listGames(new ListGamesRequest(auth));

        assertEquals(2, result.games().size());
    }

    @Test
    void failure() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "password",
                "email");
        RegisterService registerService = new RegisterService();
        Object registerResponse = registerService.register(registerRequest);
        var thing = (RegisterResponse) registerResponse;
        var auth = thing.authToken();

        CreateGameRequest createGameRequest = new CreateGameRequest("game", auth);
        CreateGameService gameService = new CreateGameService();
        int gameID = gameService.createGame(createGameRequest).gameID();

        ListGamesService service = new ListGamesService();
        assertThrows(ServiceErrorUnauthorized.class, () -> {service.listGames(new
                                                ListGamesRequest(auth + "shit"));});
    }
}
