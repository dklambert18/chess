package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KingMoveCalculator {
    private final ChessBoard board;
    private final ChessPosition position;

    public KingMoveCalculator(ChessBoard board, ChessPosition position){
        this.board = board;
        this.position = position;
    }

    public ArrayList<ChessMove> getMoves(){
        ArrayList<ChessMove> possible = new ArrayList<>();
        ChessMove topRight = new ChessMove(position, position.changePosition(1,1));
        ChessMove Right = new ChessMove(position, position.changePosition(0, 1));
        ChessMove bottomRight = new ChessMove(position, position.changePosition(-1, 1));
        ChessMove bottom = new ChessMove(position, position.changePosition(-1, 0));
        ChessMove bottomLeft = new ChessMove(position, position.changePosition(-1, -1));
        ChessMove Left = new ChessMove(position, position.changePosition(0, -1));
        ChessMove topLeft = new ChessMove(position, position.changePosition(1, -1));
        ChessMove Top = new ChessMove(position, position.changePosition(1, 0));
        possible.add(topRight);
        possible.add(Right);
        possible.add(bottomRight);
        possible.add(bottom);
        possible.add(bottomLeft);
        possible.add(Left);
        possible.add(topLeft);
        possible.add(Top);

        for (ChessMove thing : possible){
            if (thing.getEndPosition().isOpen()) {
                if (board.getPiece(thing.getEndPosition()) != null) {
                    possible.remove(thing);
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
        return String.format("KingMoveCalculator(possible moves: %s)", this.getMoves());
    }

}