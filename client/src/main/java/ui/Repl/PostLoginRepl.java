package ui.Repl;

public class PostLoginRepl extends Repl{
    String serverUrl;
    PostLoginRepl(String url){
        serverUrl = url;
    }

    @Override
    public void run() {
        System.out.println("hello");
    }
    @Override
    public String help() {
        return "help";
    }

    @Override
    public void printLine() {

    }
}
