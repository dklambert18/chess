package ui.Repl;

import java.util.Objects;
import java.util.Scanner;

import ui.ChessClient;
import ui.EscapeSequences.*;

import static ui.EscapeSequences.*;

public class PreLoginRepl extends Repl {
    private final ChessClient client;

    public PreLoginRepl(String serverUrl){
        client = new ChessClient(serverUrl, this);
    }
    public void run() {
        System.out.println("Welcome to Hikaru's Chess Hub. Type help to get started.");

        Scanner scanner = new Scanner(System.in);
        var result = "";

        while (!result.equals("quit")){
            printPrompt();
            String line = scanner.nextLine();

            try {
                var request = line.split(" ")[0];

                System.out.println(SET_TEXT_COLOR_GREEN + result + RESET_TEXT_COLOR);
            } catch (Exception e) {
                var msg = e.getMessage();
                System.out.println(msg);
            }
        }
        System.out.println();
    }
    @Override
    public String help() {
        return """
                register <username> <password> <email> - to create an account
                login <username> <password> - to login...obviously
                quit - to exit
                help - to see options
                """;
    }

    public void printLine() {

    }

    public void printPrompt(){
        System.out.println("\n" + SET_TEXT_COLOR_BLUE + "[logged out]" + ">>>" + RESET_TEXT_COLOR);
    }

    public String eval(String... params) throws Exception {
        try {
            return switch (params[0]) {
                case "register" -> client.register(params);
                case "login" -> client.login(params);
                case "quit" -> null;
                default -> help();
            };
        } catch(Exception e){
            return e.getMessage();
        }
    }
}
