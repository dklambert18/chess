package ui.Repl;

import ui.ChessClient;
import ui.ClientState;

import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class PostLoginRepl {
    String serverUrl;
    ChessClient client;
    ClientState.State state = ClientState.State.LOGGED_IN;

    public PostLoginRepl(String url, ChessClient client){
        serverUrl = url;
        this.client = client;
    }

    public String run() {

        Scanner scanner = new Scanner(System.in);

        String result = "";

        while (!result.equals("quit")){
            printPrompt();
            String line = scanner.nextLine();

            try {
                var request = line.split(" ");
                result = eval(request);

                System.out.println(SET_TEXT_COLOR_GREEN + result + RESET_TEXT_COLOR);
                if (Objects.equals(result.split(" ")[0].toLowerCase(), "logged")){
                    return "logout";
                }
            } catch (Exception e) {
                var msg = e.getMessage();
                System.out.println(msg);
            }
        }
        return "quit";
    }
    public String help() {
        return """
                create <name> - create a game
                list - games
                join <ID> [WHITE|BLACK|<empty>] - join a game
                observe <ID> - observe a game
                logout - stop playing chess
                quit - quit playing chess
                help - get help with the possible commands you dummy
                """;
    }

    public void printPrompt() {
        System.out.println("\n" + SET_TEXT_COLOR_BLUE + "[logged in]" + " >>>" + RESET_TEXT_COLOR);
    }

    public String eval(String... params) throws Exception {
        try {
            return switch (params[0].toLowerCase()) {
                case "logout" -> client.logout(params);
                case "join" -> client.joinGame(params);
                case "observe" -> client.joinGame(params);
                case "list" -> client.listGames(params);
                case "create" -> client.createGame(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch(Exception e){
            return e.getMessage();
        }
    }
}
