package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class ChessTeam {
    private ChessGame.TeamColor color;
    private ChessBoard board;

    public ChessTeam(ChessGame.TeamColor color, ChessBoard board){
        this.color = color;
        this.board = board;
    }

    public Collection<Collection<ChessMove>> getMoves(){
        Collection<Collection<ChessMove>> moves = new ArrayList<>();
        for (int i=0; i< 8; i++){
            for (int j=0; j< 8; j++){
                var pos = new ChessPosition(i + 1, j + 1);
                if (board.getPiece(pos) == null){
                    continue;
                } else {
                    var piece = new ChessPiece(board.getPiece(pos).getTeamColor()
                            , board.getPiece(pos).getPieceType());
                    moves.add(piece.pieceMoves(board, pos));
                    /*
                    should I make these into hash sets or something??? so I can iterate through it in categories?? like
                    check knight moves, then bishop moves, etc.??
                     */
                }
            }
        }
        return moves;
    }
}
