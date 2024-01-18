package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
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
        ArrayList<ChessMove> possible = new ArrayList<>();
        //check all moves in up-right direction.
        for (int i=1; this.position.getRow() + i <= 8 && this.position.getColumn() + i <= 8; i++){
                ChessPosition pos = new ChessPosition(this.position.getRow()+i, this.position.getColumn()+i);
                if (board.getPiece(pos) == null){
                    ChessMove to_add = new ChessMove(this.position, pos);
                    possible.add(to_add);
                } else {
                    break;
                }
        }
        //check all moves in the up-left direction
        for (int i=1; this.position.getRow() + i <= 8 && this.position.getColumn() - i > 0; i++){
            ChessPosition pos = new ChessPosition(this.position.getRow()+i, this.position.getColumn()-i);
            if (board.getPiece(pos) == null){
                ChessMove to_add = new ChessMove(this.position, pos);
                possible.add(to_add);
            } else {
                break;
            }
        }
        //check all moves in the lower-right direction
        for (int i=1; this.position.getRow() - i > 8 && this.position.getColumn() + i <= 8; i++){
            ChessPosition pos = new ChessPosition(this.position.getRow()-i, this.position.getColumn()+i);
            if (board.getPiece(pos) == null){
                ChessMove to_add = new ChessMove(this.position, pos);
                possible.add(to_add);
            } else {
                break;
            }
        }
        //check all moves in the lower-left direction
        for (int i=1; this.position.getRow() - i > 0 && this.position.getColumn() - i > 0; i++){
            ChessPosition pos = new ChessPosition(this.position.getRow()-i, this.position.getColumn()-i);
            if (board.getPiece(pos) == null){
                ChessMove to_add = new ChessMove(this.position, pos);
                possible.add(to_add);
            } else {
                break;
            }
        }
        return possible;
    }

    @Override
    public String toString(){
        return String.format("BishopMoveCalculator(position: %s)", this.position);
    }

}

