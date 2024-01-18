package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class RookMoveCalculator {
    private ChessBoard board;
    private ChessPosition position;
    private ChessPiece piece;

    public RookMoveCalculator(ChessBoard board, ChessPosition position, ChessPiece piece){
        this.board = board;
        this.position = position;
        this.piece = piece;
    }

    public ArrayList<ChessMove> getMoves(){
        ArrayList<ChessMove> possible = new ArrayList<>();
        int[][] it = {{1,0},{-1,0},{0,1},{0,-1}};
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
        RookMoveCalculator other = (RookMoveCalculator)obj;
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
