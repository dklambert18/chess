package chess;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private static ChessPiece[][] setNormalBoard(){
        //Create the whole board
        ChessPiece[][] starting_board = new ChessPiece[8][8];

        ChessPiece[] white_pieces = new ChessPiece[8];
        white_pieces[0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        white_pieces[1] = (new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        white_pieces[2] = (new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        white_pieces[3] = (new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
        white_pieces[4] = (new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
        white_pieces[5] = (new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        white_pieces[6] = (new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        white_pieces[7] = (new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        starting_board[0] = (white_pieces);


        starting_board[1] = new ChessPiece[8];
        for (int i=0; i<8; i++){
            starting_board[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

        for (int i=2; i<6; i++){
            starting_board[i] = new ChessPiece[8];
            for (int j=0; j<8; j++){
                starting_board[i][j] = null;
            }
        }

        starting_board[6] = new ChessPiece[8];
        for (int i=0; i<8; i++){
            starting_board[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }

        starting_board[7] = new ChessPiece[]{
                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK),
        (new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT))
        ,(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP))
        ,(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN))
        ,(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING))
        ,(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP))
        , (new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT))
        ,(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK))
        };

        return starting_board;
    }

    //MEMBER VARIABLE
    public ChessPiece[][] board;
    public ChessBoard() {
       this.board = setNormalBoard();
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (position.getColumn()>8 || position.getRow()>8 || position.getColumn() < 1 || position.getRow() < 1){
            throw new RuntimeException("Out of bounds");
        }
//        if (board.get(position.getRow() - 1).get(position.getColumn() - 1) != null) {
//            board.get(position.getRow() - 1).remove(position.getColumn() - 1);
//        }
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    public boolean hasPiece(ChessPosition position){
        return board[position.getRow() - 1][position.getColumn() - 1] != null;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        board = setNormalBoard();
        //throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }

        if (obj == this){
            return true;
        }

        if (obj.getClass()!=getClass()){
            return false;
        }

        ChessBoard test = (ChessBoard) obj;
        return (Arrays.deepEquals(test.board, this.board));
    }
}
