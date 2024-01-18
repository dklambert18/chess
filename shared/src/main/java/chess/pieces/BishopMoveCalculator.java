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
        for (int i=1; i < 8; i++){
            ChessPosition pos = new ChessPosition(this.position.getRow()-i, this.position.getColumn()-i);
            if (pos.isOpen()){
                ChessMove move = new ChessMove(position, pos);
                possible.add(move);
            }
            ChessPosition pos1 = new ChessPosition(this.position.getRow()+i, this.position.getColumn()+i);
            if (pos1.isOpen()){
                ChessMove move = new ChessMove(position, pos1);
                possible.add(move);
            }
            ChessPosition pos2 = new ChessPosition(this.position.getRow()-i, this.position.getColumn()+i);
            if (pos2.isOpen()){
                ChessMove move = new ChessMove(position, pos2);
                possible.add(move);
            }
            ChessPosition pos3 = new ChessPosition(this.position.getRow()+i, this.position.getColumn()-i);
            if (pos3.isOpen()){
                ChessMove move = new ChessMove(position, pos3);
                possible.add(move);
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

