package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class KingMoveCalculator {
    private final ChessBoard board;
    private final ChessPosition position;
    private ChessPiece piece;

    public KingMoveCalculator(ChessBoard board, ChessPosition position, ChessPiece piece){
        this.board = board;
        this.position = position;
        this.piece = piece;
    }

    public ArrayList<ChessMove> getMoves(){
        ArrayList<ChessMove> possible = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                ChessMove move = new ChessMove(position, position.changePosition(i, j));
                if (position.changePosition(i, j).isValid()) {
                    if (board.getPiece(position.changePosition(i, j)) == null) {
                        possible.add(move);
                    } else {
                        if (!board.getPiece(position.changePosition(i, j)).getTeamColor().equals(piece.getTeamColor())) {
                            possible.add(move);
                        }
                    }
                }
            }
        }
        return possible;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (this.getClass() != obj.getClass()){
            return false;
        }
        KingMoveCalculator other = (KingMoveCalculator)obj;
        return board.equals(other.board) && position.equals(other.position);
    }

    @Override
    public int hashCode(){
        return board.hashCode() + position.hashCode();
    }

    @Override
    public String toString(){
        return String.format("KingMoveCalculator(possible moves: %s)", this.position);
    }
}
