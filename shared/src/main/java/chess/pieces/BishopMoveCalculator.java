package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class BishopMoveCalculator {
    private final ChessBoard board;
    private final ChessPosition position;

    public BishopMoveCalculator(ChessBoard board, ChessPosition position){
        this.board = board;
        this.position = position;
    }

    public ArrayList<ChessMove> getMoves(){
        //bottom left direction
        ArrayList<ChessMove> possible = new ArrayList<>();
        int[][] it = {{1,1},{-1,-1},{-1,1},{1,-1}};
        for (int[] mul : it){
            for (int i=1; i < 8; i++){
                ChessPosition pos = new ChessPosition(this.position.getRow() + i * mul[0],
                        this.position.getColumn() + i * mul[1]);
                if (pos.isValid()){
                    ChessMove move = new ChessMove(position, pos);
                    possible.add(move);
//                    if (board.getPiece(pos)!=null){
//                        break;
//                    }
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
        BishopMoveCalculator other = (BishopMoveCalculator)obj;
        return board.equals(other.board) && position.equals(other.position);
    }

    @Override
    public int hashCode(){
        return board.hashCode() + position.hashCode();
    }
    @Override
    public String toString(){
        return String.format("BishopMoveCalculator(possible moves: %s)", this.getMoves());
    }

}

