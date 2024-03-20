import chess.*;
import ui.Repl.PreLoginRepl;

public class Main {

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        PreLoginRepl repl = new PreLoginRepl("http://localhost:8080");
        repl.run();
    }
}