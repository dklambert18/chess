package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class PawnMoveCalculator {
    private final ChessBoard board;
    private final ChessPosition position;
    private final ChessPiece piece;

    public PawnMoveCalculator(ChessBoard board, ChessPosition position, ChessPiece piece) {
        this.board = board;
        this.position = position;
        this.piece = piece;
    }


    public ArrayList<ChessMove> getMoves() {

        ChessPiece.PieceType[] types = {ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.BISHOP};

        ArrayList<ChessMove> possible = new ArrayList<>();

        int factor = (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) ? 1 : -1;
        int rowOfPromo = (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) ? 8 : 1;
        int rowOfStart = (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) ? 2 : 7;


        if (position.changePosition(factor, 1).isValid()) {
            if (board.getPiece(position.changePosition(factor, 1)) != null) {
                if (!board.getPiece(position.changePosition(factor, 1)).
                        getTeamColor().equals(piece.getTeamColor())) {
                    if (position.changePosition(factor, 1).getRow() == rowOfPromo){
                        for (ChessPiece.PieceType thing : types){
                            ChessMove move = new ChessMove(position, position.changePosition(factor, 1), thing);
                            possible.add(move);
                        }
                    } else {
//                        System.out.println(board.getPiece(position.changePosition(factor, 1)).getTeamColor() + " " +
//                                board.getPiece(position.changePosition(factor, 1)));
                        ChessMove move = new ChessMove(position, position.changePosition(factor, 1));
                        possible.add(move);
                    }
                }
            }
        }
        if (position.changePosition(factor, -1).isValid()) {
            if (board.getPiece(position.changePosition(factor, -1)) != null) {
                if (position.changePosition(factor, -1).getRow() == rowOfPromo){
                    for (ChessPiece.PieceType thing : types){
                        ChessMove move = new ChessMove(position, position.changePosition(factor, -1), thing);
                        possible.add(move);
                    }
                } else {
                    ChessMove move = new ChessMove(position, position.changePosition(factor, -1));
                    possible.add(move);
                }
            }
        }
        if (position.changePosition(factor, 0).isValid()) {
            if (board.getPiece(position.changePosition(factor, 0)) == null) {
                if (position.changePosition(factor, 0).getRow() == rowOfPromo){
                    for (ChessPiece.PieceType thing : types){
                        ChessMove move = new ChessMove(position, position.changePosition(factor, 0), thing);
                        possible.add(move);
                    }
                    return possible;
                } else {
                    ChessMove move = new ChessMove(position, position.changePosition(factor, 0));
                    possible.add(move);
                }
                if (position.changePosition(2 * factor, 0).isValid() && position.getRow() == rowOfStart) {
                    if (board.getPiece(position.changePosition(2 * factor, 0)) == null) {
                        if (position.changePosition(2 * factor, 0).getRow() == rowOfPromo){
                            for (ChessPiece.PieceType thing : types){
                                ChessMove move = new ChessMove(position, position.changePosition(2 * factor, 0), thing);
                                possible.add(move);
                            }
                        } else {
                            ChessMove move = new ChessMove(position, position.changePosition(2 * factor, 0));
                            possible.add(move);
                        }
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
        PawnMoveCalculator other = (PawnMoveCalculator)obj;
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
