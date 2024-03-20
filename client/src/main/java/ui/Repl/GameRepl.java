package ui.Repl;

public class GameRepl extends Repl{

    String serverUrl;
    GameRepl(String url){
        serverUrl = url;
    }

    @Override
    public void run() {
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void printLine() {

    }
}
