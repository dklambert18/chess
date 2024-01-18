package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class QueenMoveCalculator {
    private final ChessBoard board;
    private final ChessPosition position;

    public QueenMoveCalculator(ChessBoard board, ChessPosition position){
        this.board = board;
        this.position = position;
    }

    public ArrayList<ChessMove> getMoves(){
        ArrayList<ChessMove> possible = new ArrayList<>();

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
        QueenMoveCalculator other = (QueenMoveCalculator)obj;
        return board.equals(other.board) && position.equals(other.position);
    }

    @Override
    public int hashCode(){
        return board.hashCode() + position.hashCode();
    }

    @Override
    public String toString(){
        return String.format("QueenMoveCalculator(possible moves: %s)", this.getMoves());
    }
}
