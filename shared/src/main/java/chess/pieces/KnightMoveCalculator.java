package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class KnightMoveCalculator {
    private ChessBoard board;
    private ChessPosition position;
    private final ChessPiece piece;

    public KnightMoveCalculator(ChessBoard board, ChessPosition position, ChessPiece piece){
        this.board = board;
        this.position = position;
        this.piece = piece;
    }

    public ArrayList<ChessMove> getMoves(){
        int[][] changed = {{2,1}, {2,-1}, {-2,1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};
        ArrayList<ChessMove> possible = new ArrayList<>();
        for (int[] c: changed){

            ChessMove move = new ChessMove(position, position.changePosition(c[0], c[1]));
            if (move.getEndPosition().isValid()) {
                System.out.println("" + c[0] + " " + c[1]);

                if (board.getPiece(move.getEndPosition()) == null){
//                    System.out.println(c);
                    possible.add(move);
                }
                else if (board.hasPiece(move.getEndPosition())){

                    if (!board.getPiece(move.getEndPosition()).getTeamColor().equals(piece.getTeamColor())){
                        possible.add(move);
//                        System.out.println("" + c[0] + " " + c[1]);
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
