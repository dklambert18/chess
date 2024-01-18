package chess;

import chess.pieces.*;

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
    public boolean hasMoved;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.type = type;
        this.color = pieceColor;
        this.hasMoved = false;
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
        KingMoveCalculator moveCalculator;
        BishopMoveCalculator bishopMoveCalculator;
        PawnMoveCalculator pawnMoveCalculator;
        RookMoveCalculator rookMoveCalculator;
        QueenMoveCalculator queenMoveCalculator;
        KnightMoveCalculator knightMoveCalculator;
        if (this.type.equals(PieceType.KING)) {
                moveCalculator = new KingMoveCalculator(board, myPosition, this);
                return moveCalculator.getMoves();
            }
        if (this.type.equals(PieceType.BISHOP)) {
                bishopMoveCalculator = new BishopMoveCalculator(board, myPosition);
                return bishopMoveCalculator.getMoves();
            }
        if (this.type.equals(PieceType.PAWN)) {
                pawnMoveCalculator = new PawnMoveCalculator(board, myPosition, this);
                return pawnMoveCalculator.getMoves();
            }
        if (this.type.equals(PieceType.ROOK)){
                rookMoveCalculator = new RookMoveCalculator(board, myPosition, this);
                return rookMoveCalculator.getMoves();
            }
        if (this.type.equals(PieceType.QUEEN)){
                queenMoveCalculator = new QueenMoveCalculator(board, myPosition);
                return queenMoveCalculator.getMoves();
            } else {
            knightMoveCalculator = new KnightMoveCalculator(board, myPosition);
            return knightMoveCalculator.getMoves();
        }
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
