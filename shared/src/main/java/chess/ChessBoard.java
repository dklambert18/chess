package chess;

import java.util.ArrayList;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private static ArrayList<ArrayList<ChessPiece>> setNormalBoard(){
        //Create the whole board
        ArrayList<ArrayList<ChessPiece>> starting_board = new ArrayList<>();

        //Create the first row (with pieces)
        ArrayList<ChessPiece> white_pieces = new ArrayList<>();
        white_pieces.add(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        white_pieces.add(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        white_pieces.add(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        white_pieces.add(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
        white_pieces.add(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
        white_pieces.add(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        white_pieces.add(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        white_pieces.add(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        //Add the first row to the board
        starting_board.add(white_pieces);

        //Create the second row (with pieces)
        ArrayList<ChessPiece> white_pawns = new ArrayList<>();
        for (int i=0; i<8; i++){
            white_pawns.add(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
        }
        //Add second row to the board
        starting_board.add(white_pawns);
        //add the blank rows
        ArrayList<ChessPiece> empty_row = new ArrayList<>();
        for (int i=0; i<8; i++) {
            empty_row.add(null);
        }
        for (int i=0; i<4; i++){
            starting_board.add(empty_row);
        }
        //Create the pawn row for black
        ArrayList<ChessPiece> black_pawns = new ArrayList<>();
        for (int i=0; i<8; i++){
            black_pawns.add(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }
        //Add pawn row for black
        starting_board.add(black_pawns);
        //Create the first black row (with pieces)
        ArrayList<ChessPiece> black_pieces = new ArrayList<>();
        black_pieces.add(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        black_pieces.add(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        black_pieces.add(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        black_pieces.add(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        black_pieces.add(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        black_pieces.add(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        black_pieces.add(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        black_pieces.add(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        //Add the first row to the board
        starting_board.add(black_pieces);
        return starting_board;
    }

    private static ArrayList<ArrayList<ChessPiece>> setEmpty_board(){
        ArrayList<ArrayList<ChessPiece>> starting_board = new ArrayList<>();
        ArrayList<ChessPiece> empty_row = new ArrayList<>();
        for (int i=0; i<8; i++){
            empty_row.add(null);
        }
        for (int i=0; i<8; i++){
            starting_board.add(empty_row);
        }
        return starting_board;
    }

    //MEMBER VARIABLE
    public ArrayList<ArrayList<ChessPiece>> board;
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
        if (board.get(position.getRow() - 1).get(position.getColumn() - 1) != null){
            throw new RuntimeException("There's already a piece in that spot bitch");
        }
        board.get(position.getRow() - 1).set(position.getColumn() - 1, piece);
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board.get(position.getRow() - 1).get(position.getColumn() - 1);
        //throw new RuntimeException("Not implemented");
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
        //return test.getPiece(new ChessPosition(1, 1)) == getPiece(new ChessPosition(1,1));
        return (test.board.equals(this.board));
    }
}
