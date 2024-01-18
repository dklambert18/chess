package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KnightMoveCalculator {
    private ChessBoard board;
    private ChessPosition position;

    public KnightMoveCalculator(ChessBoard board, ChessPosition position){
        this.board = board;
        this.position = position;
    }

    public ArrayList<ChessMove> getMoves(){
        ArrayList<ChessMove> possible = new ArrayList<>();
        if (position.changePosition(2,1).isValid()){
            ChessMove move = new ChessMove(position, position.changePosition(2,1));
            possible.add(move);
        }
        if (position.changePosition(1,2).isValid()){
            ChessMove move = new ChessMove(position, position.changePosition(1,2));
            possible.add(move);
        }
        if (position.changePosition(-2,-1).isValid()){
            ChessMove move = new ChessMove(position, position.changePosition(-2,-1));
            possible.add(move);
        }
        if (position.changePosition(-2,1).isValid()){
            ChessMove move = new ChessMove(position, position.changePosition(-2,1));
            possible.add(move);
        }
        if (position.changePosition(2,-1).isValid()){
            ChessMove move = new ChessMove(position, position.changePosition(2,-1));
            possible.add(move);
        }
        if (position.changePosition(-1,-2).isValid()){
            ChessMove move = new ChessMove(position, position.changePosition(-1,-2));
            possible.add(move);
        }
        if (position.changePosition(-1,2).isValid()){
            ChessMove move = new ChessMove(position, position.changePosition(-1,2));
            possible.add(move);
        }
        if (position.changePosition(1,-2).isValid()){
            ChessMove move = new ChessMove(position, position.changePosition(1,-2));
            possible.add(move);
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
        KnightMoveCalculator other = (KnightMoveCalculator)obj;
        return board.equals(other.board) && position.equals(other.position);
    }

    @Override
    public int hashCode(){
        return board.hashCode() + position.hashCode();
    }

    @Override
    public String toString(){
        return String.format("KnightMoveCalculator(possible moves: %s)", this.position);
    }

}
