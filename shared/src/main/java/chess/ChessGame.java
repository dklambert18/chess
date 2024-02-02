package chess;

import java.util.ArrayList;
import java.util.Collection;

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
        if (board.getPiece(startPosition) == null){
            return null;
        }
        else {
            var moves = board.getPiece(startPosition).pieceMoves(board, startPosition);

        }
        return new ArrayList<ChessMove>();
    }

    public ChessPosition findKing(TeamColor color){
        for (int i=0; i<8; i++) {
//            System.out.println(i);
            for (int j=0; j<8; j++) {
                ChessPosition pos = new ChessPosition(i + 1, j + 1);
//                System.out.println(pos);
//                System.out.println(board.getPiece(pos));;
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
            throw new InvalidMoveException("helllo bruther");
        }
        else if (isInCheck(teamToMove)) {
            /*
            Check if the move will take the team out of check. If it doesn't, throw an invalid move exception.
             */
            throw new InvalidMoveException("helllo bruther");
        }
        else {
            board.addPiece(new ChessPosition(move.getEndPosition().getRow(), move.getEndPosition().getColumn()),
                                                board.getPiece(move.getStartPosition()));
            board.addPiece(new ChessPosition(move.getStartPosition().getRow(), move.getStartPosition().getColumn()),
                                                null);
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
        /*
        Go through each piece of the opposite color, and see if there is a valid move that would put that piece
        in the King's position.

        Only check moves which don't put the team into Check.
         */
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        /*
        Check if the King is in check and then see if the king can move anywhwere.
         */
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (teamToMove.equals(teamColor)){
            /*
            if valid moves is empty then return true.
             */
        }
        throw new RuntimeException("Not implemented");
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
}
