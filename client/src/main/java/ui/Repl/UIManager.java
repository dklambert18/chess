package ui.Repl;

public class UIManager {

    public PreLoginRepl preRepl;
    public PostLoginRepl postRepl;
    public GameRepl gameRepl;
    public Repl currRepl;

    public UIManager(String url){
        preRepl = new PreLoginRepl(url);
        currRepl = preRepl;
        postRepl = new PostLoginRepl(url);
        gameRepl = new GameRepl(url);

    }



}
