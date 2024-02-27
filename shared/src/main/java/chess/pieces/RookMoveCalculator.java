package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class RookMoveCalculator extends LinearMoveCalculator {

    private static final int[][] directionsList = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public RookMoveCalculator(ChessBoard board, ChessPosition position, ChessPiece piece) {
        super(board, position, piece, directionsList);
    }
}
