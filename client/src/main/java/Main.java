import chess.*;
import ui.Repl.UIManager;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        UIManager uiManager = new UIManager("http://localhost" + 8080);
    }
}