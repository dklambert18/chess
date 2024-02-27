package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class BishopMoveCalculator extends LinearMoveCalculator{
    private static final int[][] directionsList = {{1,1},{-1,-1},{-1,1},{1,-1}};

    public BishopMoveCalculator(ChessBoard board, ChessPosition position, ChessPiece piece){
        super(board, position, piece, directionsList);
    }
}

