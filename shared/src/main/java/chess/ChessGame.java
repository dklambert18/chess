package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board = new ChessBoard();
    private TeamColor teamToMove = TeamColor.WHITE;
    public ChessGame() {
        var board = this.board;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamToMove;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamToMove = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        HashSet<ChessMove> finalMoves = new HashSet<>();
        if (board.getPiece(startPosition) == null) {
            return null;
        } else {
            ChessPiece piece = board.getPiece(startPosition);
            if (piece == null) {
                return null;
            }
            var moves = piece.pieceMoves(board, startPosition);
            checkLegalMoves(moves, finalMoves);
            return finalMoves;
        }
    }

    
    public ChessPosition findKing(TeamColor color){
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                ChessPosition pos = new ChessPosition(i + 1, j + 1);
                if (board.getPiece(pos) != null) {

                    if (board.getPiece(pos).getPieceType().equals(ChessPiece.PieceType.KING) &&
                            board.getPiece(pos).getTeamColor().equals(color)) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        if (!teamToMove.equals(board.getPiece(move.getStartPosition()).getTeamColor())) {
            throw new InvalidMoveException("it's not your turrrrn bruther");
        }
        else if (!move.getEndPosition().isValid()){
            throw new InvalidMoveException("That move was invalid");
        }

        else {
            //putting the new piece into the new position (to test the position and see if the move is actually valid)
            ChessPosition startPosition = move.getStartPosition();

            //getting valid moves to check for all the moves that this piece can legally make.
            Collection<ChessMove> validMoves = validMoves(startPosition);

            //setting the end position of the move.
            ChessPosition endPosition = move.getEndPosition();

            //saving the pieces at the beginning and end spots of the move so we can replace them if we need.
            ChessPiece deadPiece = board.getPiece(endPosition);
            ChessPiece testPiece = board.getPiece(move.getStartPosition());
            //adding the piece to the end position of the move.
            board.addPiece(endPosition, testPiece);

            //replacing the old piece with a null value, will replace this with the old piece if the move isn't valid.
            board.addPiece(startPosition, null);
            if (!validMoves.contains(move)){
                board.addPiece(endPosition, deadPiece);
                board.addPiece(startPosition, testPiece);
                throw new InvalidMoveException("That was an invalid move. Idiot.");
            }
            if (teamToMove.equals(TeamColor.WHITE)){
                setTeamTurn(TeamColor.BLACK);
            } else {
                setTeamTurn(TeamColor.WHITE);
            }
            if (move.getPromotionPiece() != null){
                ChessPiece promotionPiece = new ChessPiece(testPiece.getTeamColor(), move.getPromotionPiece());
                board.addPiece(move.getEndPosition(), promotionPiece);
            }

        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition startPos = findKing(teamColor);
        int moveDir = (teamColor.equals(TeamColor.WHITE)) ? 1 : -1;
        if (startPos == null){
            return false;
        }
        /*
        Checking the diagonals from the King to check for opps.
         */
        int[][] bishopDir = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        for (int[] bDir : bishopDir){
            for (int i=1; i<8; i++) {
                int rowChange = bDir[0] * i + startPos.getRow();
                int colChange = bDir[1] * i + startPos.getColumn();
                ChessPosition pos = new ChessPosition(rowChange, colChange);

                if (!pos.isValid()) {
                    break;
                } else {
                    if (board.getPiece(pos) != null) {
                        ChessPiece currPiece = board.getPiece(pos);
                        if (currPiece.getTeamColor().equals(teamColor)) {
                            break;
                        } else if (!currPiece.getPieceType().equals(ChessPiece.PieceType.BISHOP) &&
                                !currPiece.getPieceType().equals(ChessPiece.PieceType.QUEEN)) {
                            break;
                        }
                        if (currPiece.getPieceType().equals(ChessPiece.PieceType.BISHOP) ||
                                currPiece.getPieceType().equals(ChessPiece.PieceType.QUEEN)) {
                            return true;
                        }
                    }
                }
            }
        }
        /*
        Checking the straight directions for rooks that have the king in check
         */
        int[][] rookDir = {{1,0}, {0,1}, {-1,0}, {0,-1}};
        for (int[] rDir : rookDir){
            for (int i=1; i<8; i++){
                int rowChange = rDir[0] * i + startPos.getRow();
                int colChange = rDir[1] * i + startPos.getColumn();
                ChessPosition pos = new ChessPosition(rowChange, colChange);
                if (pos.isValid()){
                    if (board.getPiece(pos) != null){
                        ChessPiece currPiece = board.getPiece(pos);
                        if (currPiece.getTeamColor().equals(teamColor)){
                            break;
                        }
                        else if (!currPiece.getPieceType().equals(ChessPiece.PieceType.ROOK) &&
                                    !currPiece.getPieceType().equals(ChessPiece.PieceType.QUEEN)){
                            break;
                        }
                        if (currPiece.getPieceType().equals(ChessPiece.PieceType.ROOK) ||
                                currPiece.getPieceType().equals(ChessPiece.PieceType.QUEEN)) {
                            return true;
                        }
                    }
                }
            }
        }
        /*
        Checking the knight opp positions.
         */
        int[][] knightDir = {{1,2}, {1,-2}, {-1,2}, {-1,-2}, {2,1}, {2,-1}, {-2,1}, {-2,-1}};
        for (int[] kDir : knightDir){
            int rowChange = kDir[0] + startPos.getRow();
            int colChange = kDir[1] + startPos.getColumn();
            ChessPosition pos = new ChessPosition(rowChange, colChange);
            if (pos.isValid()){
                if (board.getPiece(pos) != null){
                    if (board.getPiece(pos).getPieceType().equals(ChessPiece.PieceType.KNIGHT)
                            && !board.getPiece(pos).getTeamColor().equals(teamColor)){
                        return true;
                    }
                }
            }
        }
        /*
        Check for pawns that have checked the King
         */
        int[][] pawnDir = {{1, 1}, {1, -1}};
        for (int[] pDir : pawnDir){
            int rowChange = pDir[0] * moveDir + startPos.getRow();
            int colChange = pDir[1] + startPos.getColumn();
            ChessPosition pos = new ChessPosition(rowChange, colChange);
            if (pos.isValid()){
                if (board.getPiece(pos) != null){
                    if (board.getPiece(pos).getPieceType().equals(ChessPiece.PieceType.PAWN)
                            && !board.getPiece(pos).getTeamColor().equals(teamColor)){
                        return true;
                    }
                }
            }
        }
        return isInKingCheck(teamColor);
    }

    public boolean isInKingCheck(TeamColor teamColor){
        ChessPosition startPos = findKing(teamColor);

        for (int i=-1; i<2; i++){
            for (int j=-1; j<2; j++){
                if (i==0 && j==0){
                    continue;
                }
                int rowChange = startPos.getRow() + i;
                int colChange = startPos.getColumn() + j;
                ChessPosition pos = new ChessPosition(rowChange, colChange);
                if (pos.isValid()){
                    ChessPiece piece = board.getPiece(pos);
                    if (piece != null && piece.getPieceType().equals(ChessPiece.PieceType.KING)){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public Collection<ChessPosition> getTeamPositions(TeamColor color){
        HashSet<ChessPosition> posList = new HashSet<>();

        for (int i=1; i<=8; i++){
            for (int j=1; j<=8; j++){
                ChessPosition pos = new ChessPosition(i, j);
                if (board.getPiece(pos) != null){
                    if (board.getPiece(pos).getTeamColor().equals(color)){
                        posList.add(pos);
                    }
                }
            }
        }
        return posList;
    }


    public Collection<Collection<ChessMove>> getTeamMoves(Collection<ChessPosition> piecePositions){
        Collection<Collection<ChessMove>> moves = new HashSet<>();

        if (piecePositions == null){
            return null;
        } else {
            for (ChessPosition pos : piecePositions){
                ChessPiece piece = board.getPiece(pos);
                moves.add(piece.pieceMoves(board, pos));
            }
        }
        return moves;
    }


    public void checkLegalMoves(Collection<ChessMove> moves, Collection<ChessMove> addList){
        for (ChessMove move : moves) {
            ChessPosition startPosition = move.getStartPosition();
            ChessPosition endPosition = move.getEndPosition();
            ChessPiece endPiece = board.getPiece(endPosition);
            ChessPiece testPiece = board.getPiece(move.getStartPosition());

            board.addPiece(endPosition, testPiece);

            //replacing the old piece with a null value, will replace this with the old piece if the move isn't valid.
            board.addPiece(startPosition, null);

            if (!isInCheck(testPiece.getTeamColor())){
                addList.add(move);
            }

            board.addPiece(endPosition, endPiece);
            board.addPiece(startPosition, testPiece);

        }
    }
    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        Collection<ChessMove> finalMoves = new HashSet<>();
        /*
        Check if the King is in check and then see if the king can move anywhere.
         */
        if (!isInCheck(teamColor)){
            return false;
        }
        else {
            Collection<Collection<ChessMove>> moves = getTeamMoves(getTeamPositions(teamColor));
            for (Collection<ChessMove> moveSet : moves){
                checkLegalMoves(moveSet, finalMoves);
            }
        }
        return finalMoves.isEmpty();
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        Collection<ChessMove> finalMoves = new HashSet<>();
        if (!teamToMove.equals(teamColor)){
            return false;
        }
            /*
            if valid moves is empty then return true.
             */
        if (isInCheck(teamColor)){
            return false;
        }
        else {
            Collection<Collection<ChessMove>> moves = getTeamMoves(getTeamPositions(teamColor));
            for (Collection<ChessMove> moveSet : moves){
                checkLegalMoves(moveSet, finalMoves);
            }
        }
        return finalMoves.isEmpty();
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }

        if (o == this){
            return true;
        }

        if (o.getClass()!=getClass()){
            return false;
        }

        ChessGame test = (ChessGame) o;
        return getBoard().equals(((ChessGame) o).getBoard()) && teamToMove.equals(((ChessGame) o).teamToMove);
    }

    @Override
    public int hashCode(){

        return getBoard().hashCode() + teamToMove.hashCode();
    }
}
