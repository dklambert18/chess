package ui.Repl;

import java.util.Objects;
import java.util.Scanner;

import ui.ChessClient;
import ui.ClientState;
import ui.EscapeSequences.*;

import static ui.ClientState.State.LOGGED_IN;
import static ui.EscapeSequences.*;

public class PreLoginRepl {
    private final ChessClient client;
    private final PostLoginRepl postRepl;
    public ClientState.State state;

    public PreLoginRepl(String url){
        client = new ChessClient(url);
        postRepl = new PostLoginRepl(url, client);
        state = ClientState.State.LOGGED_OUT;
    }
    public void run() {
        System.out.println(SET_TEXT_COLOR_BLUE + "Welcome to Hikaru's Chess Hub. Type help to get started." +
                                RESET_TEXT_COLOR);

        Scanner scanner = new Scanner(System.in);
        var result = "";

        while (!result.equals("quit")){
            printPrompt();
            String line = scanner.nextLine();

            try {
                var request = line.split(" ");
                result = eval(request);

                System.out.println(SET_TEXT_COLOR_GREEN + result + RESET_TEXT_COLOR);
                if (Objects.equals(result.split(" ")[0], "Success!")){
                    result = postRepl.run();
                }
            } catch (Exception e) {
                var msg = e.getMessage();
                System.out.println(msg);
            }
        }
    }
    public String help() {
        return """
                register <username> <password> <email> - to create an account
                login <username> <password> - to login...obviously
                quit - to exit
                help - to see options
                """;
    }

    public void printPrompt(){
        System.out.println("\n" + SET_TEXT_COLOR_BLUE + "[logged out]" + " >>>" + RESET_TEXT_COLOR);
    }

    public String eval(String... params) throws Exception {
        try {
            return switch (params[0].toLowerCase()) {
                case "register" -> client.register(params);
                case "login" -> client.login(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch(Exception e){
            return e.getMessage();
        }
    }
}
