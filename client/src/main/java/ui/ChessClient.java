package ui;

import server.Server;
//import serverFacade.ServerFacade;
import java.util.Arrays;

public class ChessClient {

   // private serverFacade.ServerFacade server;
    private String visitorName = null;
    private String serverUrl;

    public ChessClient(String serverUrl, PreLoginRepl ui){

    }


    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception e){
            return e.getMessage();
        }
    }

    private String login(String... params){
        return null;
    }

    private String register(String... params){
        return null;
    }

    private String help(){
        return null;
    }
}
