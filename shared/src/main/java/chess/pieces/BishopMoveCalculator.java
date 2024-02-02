package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class BishopMoveCalculator {
    private final ChessBoard board;
    private final ChessPosition position;
    private final ChessPiece piece;

    public BishopMoveCalculator(ChessBoard board, ChessPosition position, ChessPiece piece){
        this.board = board;
        this.position = position;
        this.piece = piece;
    }

    public ArrayList<ChessMove> getMoves(){
        ArrayList<ChessMove> possible = new ArrayList<>();
        int[][] it = {{1,1},{-1,-1},{-1,1},{1,-1}};
        for (int[] mul : it){
            for (int i=1; i < 8; i++){
                int rowChange = mul[0] * i;
                int colChange = mul[1] * i;
                ChessPosition pos = new ChessPosition(position.getRow() + rowChange,
                        position.getColumn() + colChange);
                if (pos.isValid()){
                    if (board.getPiece(pos) != null) {
                        if (board.getPiece(pos).getTeamColor().equals(piece.getTeamColor())) {
                            break;
                        } else {
                            ChessMove move = new ChessMove(position, pos);
                            possible.add(move);
                            break;
                        }
                    }
                    else if (board.getPiece(pos) == null) {
                        ChessMove move = new ChessMove(position, pos);
                        possible.add(move);
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

