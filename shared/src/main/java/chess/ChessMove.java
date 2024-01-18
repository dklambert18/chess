package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private final ChessPosition start;
    private final ChessPosition end;
    private final ChessPiece.PieceType type;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {

        this.start = startPosition;
        this.end = endPosition;
        this.type = ChessPiece.PieceType.BISHOP;
    }

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition){
        this(startPosition,endPosition, null);
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        //return this.start;
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        throw new RuntimeException("Not implemented");
        //return this.end;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return this.type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (this.getClass() != obj.getClass()){
            return false;
        }
        ChessMove other = (ChessMove)obj;
        return (other.getEndPosition()==this.getEndPosition() && other.getStartPosition() == this.getStartPosition()
        && other.getPromotionPiece().equals(this.getPromotionPiece()));
    }

    @Override
    public int hashCode(){
        return start.hashCode() + end.hashCode() + getPromotionPiece().hashCode() - 20;
    }

    @Override
    public String toString(){
        return String.format("ChessMove(start position: %s, end position: %s, promotion piece type: %s", this.getStartPosition(),
    this.getEndPosition(), this.getPromotionPiece());
    }


}
