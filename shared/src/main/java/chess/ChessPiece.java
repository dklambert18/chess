package chess;

import chess.pieces.BishopMoveCalculator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final PieceType type;
    private final ChessGame.TeamColor color;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {

        this.type = type;
        this.color = pieceColor;

        //throw new RuntimeException("Not implemented");
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {

        return this.color;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
        //throw new RuntimeException("Not implemented");
        }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        BishopMoveCalculator move_calc = new BishopMoveCalculator(board, myPosition);
        return move_calc.getMoves();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (obj.getClass()!=this.getClass()){
            return false;
        }
        ChessPiece test = (ChessPiece) obj;
        return test.color == this.color && test.type == this.type;
    }


    @Override
    public int hashCode(){
        return this.type.hashCode() + this.color.hashCode();
    }

    @Override
    public String toString(){
        return String.format("ChessPiece(piece type: %s, piece color: %s", getPieceType(), getTeamColor());
    }


}
