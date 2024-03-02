package serviceTests;

import dataAccess.MemoryAccess.MemoryAuthDAO;
import dataAccess.MemoryAccess.MemoryGameDAO;
import dataAccess.MemoryAccess.MemoryUserDAO;
import dataAccess.ServiceErrors.ServiceErrorAlreadyTaken;
import dataAccess.ServiceErrors.ServiceErrorBadRequest;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CreateGameService;
import service.JoinGameService;
import service.RegisterService;
import service.requestObjects.CreateGameRequest;
import service.requestObjects.JoinGameRequest;
import service.requestObjects.RegisterRequest;
import service.responseObjects.RegisterResponse;

import static org.junit.jupiter.api.Assertions.*;

public class JoinGameServiceTests {

    public MemoryGameDAO gameDAO = new MemoryGameDAO();
    public MemoryAuthDAO authDAO = new MemoryAuthDAO();
    public MemoryUserDAO userDAO = new MemoryUserDAO();

    @BeforeEach
    void clear(){
        gameDAO.clear();
        authDAO.clear();
        userDAO.clear();
    }

    @Test
    @DisplayName("success")
    void successWhiteAndBlack() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "password",
                "email");
        RegisterService registerService = new RegisterService();
        Object registerResponse = registerService.register(registerRequest);
        var thing = (RegisterResponse) registerResponse;
        var auth = thing.authToken();

        assertEquals(1, authDAO.size());
        assertEquals(thing.username(), authDAO.getUser(auth));

        CreateGameRequest createGameRequest = new CreateGameRequest("game", auth);
        CreateGameService createGameService = new CreateGameService();
        int gameID = createGameService.createGame(createGameRequest).gameID();

        JoinGameRequest request = new JoinGameRequest(auth, "WHITE", gameID );
        var response = new JoinGameService().joinGame(request);

        JoinGameRequest request1 = new JoinGameRequest(auth, "BLACK", gameID );
        var response1 = new JoinGameService().joinGame(request1);

        var game = gameDAO.getGame(gameID);

        assertEquals("username", game.whiteUsername());
        assertEquals("username", game.blackUsername());
    }

    @Test
    void joinGameFail() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "password",
                "email");
        RegisterService registerService = new RegisterService();
        Object registerResponse = registerService.register(registerRequest);
        var thing = (RegisterResponse) registerResponse;
        var auth = thing.authToken();

        CreateGameRequest createGameRequest = new CreateGameRequest("game", auth);
        CreateGameService createGameService = new CreateGameService();
        int gameID = createGameService.createGame(createGameRequest).gameID();

        JoinGameRequest request = new JoinGameRequest(auth + "motherfucker", "WHITE", gameID );
        assertThrows(ServiceErrorUnauthorized.class, () -> { new JoinGameService().joinGame(request);});

        JoinGameRequest request1 = new JoinGameRequest(auth, "BLACK", 0 );
        assertThrows(ServiceErrorBadRequest.class, () -> { new JoinGameService().joinGame(request1);});

        JoinGameRequest request2 = new JoinGameRequest(auth, "WHITE", gameID );
        new JoinGameService().joinGame(request2);
        assertThrows(ServiceErrorAlreadyTaken.class, () -> {new JoinGameService().joinGame(request2);});

        JoinGameRequest request3 = new JoinGameRequest(auth, "BLACK", gameID );
        new JoinGameService().joinGame(request3);
        assertThrows(ServiceErrorAlreadyTaken.class, () -> {new JoinGameService().joinGame(request3);});
    }
}
