package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class PawnMoveCalculator {
    private ChessBoard board;
    private ChessPosition position;
    private ChessPiece piece;

    public PawnMoveCalculator(ChessBoard board, ChessPosition position, ChessPiece piece){
        this.board = board;
        this.position = position;
        this.piece = piece;
    }


    public ArrayList<ChessMove> getMoves(){
        ArrayList<ChessMove> possible = new ArrayList<>();
        if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)){
            if (piece.hasMoved){
                if (!board.hasPiece(position.changePosition(1,0)) &&
                        position.changePosition(1, 0).isValid()){
                    ChessMove move = new ChessMove(position, position.changePosition(1,0));
                    possible.add(move);
                }
                if (board.hasPiece(position.changePosition(1,1)) &&
                        position.changePosition(1, 1).isValid()){
                    ChessMove move = new ChessMove(position, position.changePosition(1,1));
                    possible.add(move);
                }
                if (board.hasPiece(position.changePosition(1,-1)) &&
                        position.changePosition(1, -1).isValid()) {
                    ChessMove move = new ChessMove(position, position.changePosition(1, -1));
                    possible.add(move);
                }
            } else if (!board.hasPiece(position.changePosition(2,0)) &&
                    position.changePosition(2, 0).isValid()){
                ChessMove move = new ChessMove(position, position.changePosition(2,0));
                possible.add(move);
            }
        } else {
            if (piece.hasMoved){
                if (!board.hasPiece(position.changePosition(-1,0)) &&
                        position.changePosition(-1, 0).isValid()){
                    ChessMove move = new ChessMove(position, position.changePosition(-1,0));
                    possible.add(move);
                }
                if (board.hasPiece(position.changePosition(-1,1)) &&
                        position.changePosition(-1, 1).isValid()){
                    ChessMove move = new ChessMove(position, position.changePosition(-1,1));
                    possible.add(move);
                }
                if (board.hasPiece(position.changePosition(-1,-1)) &&
                        position.changePosition(-1, -1).isValid()) {
                    ChessMove move = new ChessMove(position, position.changePosition(-1, -1));
                    possible.add(move);
                }
            } else if (!board.hasPiece(position.changePosition(-2,0)) &&
                    position.changePosition(-2, 0).isValid()){
                ChessMove move = new ChessMove(position, position.changePosition(-2,0));
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
