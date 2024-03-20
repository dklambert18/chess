package ui;

import model.GameData;
import serverFacade.ServerFacade;


import static ui.ClientState.State.LOGGED_IN;
import static ui.ClientState.State.LOGGED_OUT;

public class ChessClient {

    private ServerFacade facade;
    public ClientState.State state = LOGGED_OUT;
    private String auth;

    public ChessClient(String serverUrl) {
        facade = new ServerFacade(serverUrl);
    }

    public String createGame(String... params) {
        assert params.length == 2;
        assert state == LOGGED_IN;
        try {
            var result = facade.createGame(params[1], params[2]);
            return result.gameID().toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String login(String... params) throws Exception {
        String msg;
        if (params.length == 2){
            return "Error: Expected: login <username> <password>";
        }
        if (state != LOGGED_OUT) {
            return "Error: you are already logged in";
        }
        try {
            var result = facade.login(params[1], params[2]);
            state = LOGGED_IN;
            auth = result.authToken();
            return String.format("Success! You are logged in as: " + result.username());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String register(String... params) {
        if (params.length != 4){
            return "Error: Expected: register <username> <password> <email>";
        }
        if (state != LOGGED_OUT){
            return "Error: you are already logged in";
        }
        try {
            var result = facade.register(params[1], params[2], params[3]);
            state = LOGGED_IN;
            auth = result.authToken();
            return String.format("Success! You logged in as: " + result.username());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String joinGame(String... params) {
        if (params.length > 3 || params.length == 1) {

        }
        assert state == LOGGED_IN;
        try {
            var result = facade.joinGame(params[1], params[2], Integer.parseInt(params[3]));
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String logout(String[] params) {
        try {
            var result = facade.logout(auth);
            state = LOGGED_OUT;
            return "logged";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String listGames(String[] params) {
        try {
            var result = facade.listGames(auth);
            var gameList = result.games();
            StringBuilder finalList = new StringBuilder();
            for (GameData game : gameList) {
                String oneLine = "\n" + game.gameName() + " " + game.whiteUsername() + " "
                                    + game.blackUsername() + "\n";
                finalList.append(oneLine);
            }
            return finalList.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
