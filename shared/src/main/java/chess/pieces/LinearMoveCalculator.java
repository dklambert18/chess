package chess.pieces;

import chess.*;

import java.util.ArrayList;

public abstract class LinearMoveCalculator {
    protected ChessBoard board;
    protected ChessPosition position;
    protected ChessPiece piece;
    protected int[][] directionsList;

    public LinearMoveCalculator(ChessBoard board, ChessPosition position, ChessPiece piece, int[][] directionsList) {
        this.board = board;
        this.position = position;
        this.piece = piece;
        this.directionsList = directionsList;
    }

    public ArrayList<ChessMove> getMoves() {
        ArrayList<ChessMove> possible = new ArrayList<>();
        for (int[] iter : directionsList) {
            for (int i = 1; i < 8; i++) {
                int rowChange = iter[0] * i;
                int colChange = iter[1] * i;
                ChessPosition newPos = position.changePosition(rowChange, colChange);
                if (newPos.isValid()) {
                    if (board.getPiece(newPos) != null) {
                        if (board.getPiece(newPos).getTeamColor().equals(piece.getTeamColor())) {
                            break;
                        } else {
                            ChessMove move = new ChessMove(position, newPos);
                            possible.add(move);
                            break;
                        }
                    } else {
                        ChessMove move = new ChessMove(position, newPos);
                        possible.add(move);
                    }
                }
            }
        }
        return possible;
    }
}

