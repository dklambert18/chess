package serviceTests;

import dataAccess.*;
import dataAccess.ServiceErrors.ServiceErrorBadRequest;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.CreateGameService;
import service.RegisterService;
import requestObjects.CreateGameRequest;
import requestObjects.RegisterRequest;
import responseObjects.RegisterResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateGameServiceTests {

    MySQLGameDAO gameDAO = new MySQLGameDAO();
    public CreateGameService service = new CreateGameService();

    public CreateGameServiceTests() throws DataAccessException {
    }

    @BeforeEach
    void clear() throws DataAccessException {
        new ClearService().clearApp();
    }

    @Test
    void success() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "password",
                "email");
        RegisterService registerService = new RegisterService();
        Object registerResponse = registerService.register(registerRequest);
        var thing = (RegisterResponse) registerResponse;
        var auth = thing.authToken();
        CreateGameRequest req = new CreateGameRequest();

        CreateGameRequest createGameRequest = new CreateGameRequest("game", auth);
        CreateGameService createGameService = new CreateGameService();
        int gameID = createGameService.createGame(createGameRequest).gameID();

        assertEquals(1, gameDAO.size());
        assertEquals("game", gameDAO.getGame(gameID).gameName());
    }

    @Test
    void failure() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "password",
                "email");
        RegisterService registerService = new RegisterService();
        Object registerResponse = registerService.register(registerRequest);
        var thing = (RegisterResponse) registerResponse;
        var auth = thing.authToken();

        CreateGameRequest createGameRequest = new CreateGameRequest("game", auth + "shitty class");

        assertThrows(ServiceErrorUnauthorized.class, () -> {service.createGame(createGameRequest);});

        CreateGameRequest gameRequest = new CreateGameRequest(null, auth);

        assertThrows(ServiceErrorBadRequest.class, () -> {service.createGame(gameRequest);});
    }
}
