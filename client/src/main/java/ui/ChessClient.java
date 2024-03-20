package ui;

import serverFacade.ServerFacade;
import ui.Repl.GameRepl;
import ui.Repl.PostLoginRepl;
import ui.Repl.PreLoginRepl;
import ui.Repl.Repl;

import java.util.Arrays;

import static com.sun.tools.javac.jvm.PoolConstant.LoadableConstant.Int;
import static ui.ClientState.State.LOGGED_IN;
import static ui.ClientState.State.LOGGED_OUT;

public class ChessClient {

    // private serverFacade.ServerFacade server;
    private String username = null;
    private String password = null;
    private String email = null;
    private String auth = null;
    private ServerFacade facade;
    public ClientState.State state = LOGGED_OUT;
    private Object ui;

    public ChessClient(String serverUrl, Repl ui) {
        facade = new ServerFacade(serverUrl);
        this.ui = ui;
    }

    public String createGame(String... params) {
        assert params.length == 2;
        assert state == LOGGED_IN;
        try {
            var result = facade.createGame(params[0], params[1]);
            return result.gameID().toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String login(String... params) throws Exception {
        assert (params.length == 2);
        assert state == LOGGED_OUT;
        try {
            var result = facade.login(params[0], params[1]);
            username = params[0];
            password = params[1];
            return result.username();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String register(String... params) {
        assert (params.length == 3);
        assert state == LOGGED_OUT;
        try {
            var result = facade.register(params[0], params[1], params[2]);
            username = params[0];
            password = params[1];
            email = params[2];
            return result.username();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String joinGame(String... params) {
        assert params.length <= 3;
        assert state == LOGGED_IN;
        try {
            var result = facade.joinGame(params[0], params[1], Integer.parseInt(params[2]));
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

//    private String help() {
//        if (ui instanceof PostLoginRepl) {
//            return ((PostLoginRepl) ui).help();
//        } else if (ui instanceof GameRepl) {
//            return ((GameRepl) ui).help();
//        } else {
//            return ((PreLoginRepl) ui).help();
//        }
//    }
}
