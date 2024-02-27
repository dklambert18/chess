import chess.*;
import server.Server;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        var server = new Server();
        int port = 8080;
        try {
            // Run the server on the specified port
            server.run(port);
            System.out.println("Server is running on port " + port);
        } catch (Exception e) {
            System.err.println("Error starting the server: " + e.getMessage());
            // Handle the exception as needed
        }
    }
}