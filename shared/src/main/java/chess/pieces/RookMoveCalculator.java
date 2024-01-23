package chess.pieces;

import chess.*;

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
        int[][] it = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int[] iter : it){
            for (int i=1; i < 8; i++){
                int rowChange = iter[0] * i;
                int colChange = iter[1] * i;
                ChessPosition newPos = position.changePosition(rowChange, colChange);
                if (newPos.isValid()){
                    if (board.getPiece(newPos) != null){
                        if (board.getPiece(newPos).getTeamColor().equals(piece.getTeamColor())){
                            System.out.println(newPos);
                            System.out.println(board.getPiece(newPos));
                            break;
                        } else {
                            ChessMove move = new ChessMove(position, newPos);
                            possible.add(move);
                            break;
                        }
                    } else {
                        ChessMove move = new ChessMove(position, newPos);
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
