package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class QueenMoveCalculator extends LinearMoveCalculator {

    private static final int[][] directionsList = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}, {1,1}, {1,-1}, {-1, 1}, {-1, -1}};


    public QueenMoveCalculator(ChessBoard board, ChessPosition position, ChessPiece piece){
        super(board, position, piece, directionsList);
    }
}
