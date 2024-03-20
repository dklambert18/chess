package clientTests;

import org.junit.jupiter.api.*;
import responseObjects.JoinGameResponse;
import responseObjects.ListGamesResponse;
import responseObjects.LogoutResponse;
import responseObjects.RegisterResponse;
import server.Server;
import org.junit.jupiter.api.Assertions;

import serverFacade.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;
    public static ServerFacade facade;
    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        var serverUrl = String.format("http://localhost:" + port);
        facade = new ServerFacade(serverUrl);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void clearServer() throws Exception {
        facade.clearData();
    }

    @Test
    public void clearDataTest() {
        assertDoesNotThrow(() -> {facade.clearData();});
    }

    @Test
    public void registerFail() throws Exception {
        assertThrows(Exception.class, () -> {facade.register(null, null, null);});
    }
    @Test
    public void registerTest() throws Exception {
        RegisterResponse authData = facade.register("player1", "password", "p1@email.com");
        assertTrue(authData.authToken().length() > 10);
    }
    @Test
    public void loginTest() throws Exception {
        RegisterResponse authData = facade.register("username", "password", "email");
        var response = facade.login("username", "password");
        assertTrue(response.authToken().length() > 10);
    }

    @Test
    public void loginFail() throws Exception {
        RegisterResponse authData = facade.register("username", "password", "email");
        assertThrows(Exception.class, () -> {facade.login("teehee", "password");});
    }
    @Test
    public void logoutTest() throws Exception {
        RegisterResponse authData = facade.register("player1", "password", "p1@email.com");
        var response = facade.logout(authData.authToken());
        assertNull(response.response());
    }

    @Test
    public void logoutFail() throws Exception {
        assertThrows(Exception.class, () -> {facade.logout("blah");});
    }

    @Test
    public void createGameTest() throws Exception {
        RegisterResponse response = facade.register("username", "password", "email");
        var auth = response.authToken();
        var result = facade.createGame("game", auth);

        assertEquals(1, (int) result.gameID());
    }

    @Test
    public void createGameFail(){
        assertThrows(Exception.class, () -> {facade.createGame("blah", "game");});
    }
    @Test
    public void joinGameTest() throws Exception {
        RegisterResponse response = facade.register("username", "password", "email");
        var auth = response.authToken();
        var firstResult = facade.createGame("game", auth);
        var finalResult = facade.joinGame(response.authToken(), "WHITE", firstResult.gameID());
        assertEquals(JoinGameResponse.class, finalResult.getClass());
    }

    @Test
    public void joinGameFail() throws Exception {
        RegisterResponse response = facade.register("username", "password", "email");
        var auth = response.authToken();
        var firstResult = facade.createGame("game", auth);
        assertThrows(Exception.class, () -> {facade.joinGame("blah",
                "WHITE", firstResult.gameID());});
    }
    @Test
    public void listGamesTest() throws Exception {
        RegisterResponse response = facade.register("username", "password", "email");
        var auth = response.authToken();
        var firstResult = facade.createGame("game", auth);
        var finalResult = facade.listGames(response.authToken());
        assertEquals(ListGamesResponse.class, finalResult.getClass());
    }

    @Test
    public void listGamesFail() throws Exception {
        RegisterResponse response = facade.register("username", "password", "email");
        var auth = response.authToken();
        var firstResult = facade.createGame("game", auth);
        assertThrows(Exception.class, () -> {facade.listGames("blah");});    }
}
