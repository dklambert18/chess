package ui;

import model.GameData;
import serverFacade.ServerFacade;


import static ui.ClientState.State.LOGGED_IN;
import static ui.ClientState.State.LOGGED_OUT;

public class ChessClient {

    private final ServerFacade facade;
    public ClientState.State state = LOGGED_OUT;
    private String auth;

    public ChessClient(String serverUrl) {
        facade = new ServerFacade(serverUrl);
    }

    public String createGame(String... params) {
        if (params.length != 2){

        }
        try {
            var result = facade.createGame(params[1], auth);
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
            return "Error: Expected: join <ID> [WHITE|BLACK|<empty>]";
        }
        assert state == LOGGED_IN;
        try {
            if (params.length == 3) {
                var result = facade.joinGame(auth, params[1], Integer.parseInt(params[2]));
            }
            else if (params.length == 2){
                var result = facade.joinGame(auth, null, Integer.parseInt(params[2]));

            }
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String logout(String[] params) {
        try {
            var result = facade.logout(auth);
            state = LOGGED_OUT;
            auth = null;
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
            int i = 1;
            for (GameData game : gameList) {
                String oneLine = "\n" + i + ") " + game.gameName() + ": Game ID - " + game.gameID() +
                        ": White Player - " + game.whiteUsername() + ": Black" +
                        "Player - " + game.blackUsername();
                finalList.append(oneLine);
                i += 1;
            }
            return finalList.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
