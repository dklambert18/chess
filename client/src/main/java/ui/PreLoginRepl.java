package ui;

import java.util.Scanner;
import ui.EscapeSequences.*;

import static ui.EscapeSequences.*;

public class PreLoginRepl {
    private ChessClient client;

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
                result = client.eval(line);
                System.out.println(SET_TEXT_COLOR_GREEN + result + RESET_TEXT_COLOR);
            } catch (Exception e) {
                var msg = e.getMessage();
                System.out.println(msg);
            }
        }
        System.out.println();
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
        System.out.println("\n" + SET_TEXT_COLOR_BLUE + "[logged out]" + ">>>" + RESET_TEXT_COLOR);
    }
}
