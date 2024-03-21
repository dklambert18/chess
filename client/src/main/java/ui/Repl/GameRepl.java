package ui.Repl;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.ChessClient;

import java.util.Objects;
import java.util.Scanner;
import static ui.EscapeSequences.*;


public class GameRepl {

    String serverUrl;
    ChessGame game = new ChessGame();
    ChessBoard board = game.getBoard();
    ChessClient client;
    GameRepl(String url, ChessClient client){
        serverUrl = url;
        this.client = client;
        this.board.resetBoard();
    }

    public String run() {

        String result = "";
        Scanner scanner = new Scanner(System.in);

        while (!result.equals("quit")){
            printPrompt();
            System.out.println( SET_TEXT_COLOR_WHITE + "Tell us what you think of our chessboard!!! ");

            String line = scanner.nextLine();
            result = line.split(" ")[0];
        }
        return "quit";
    }

    public void printPrompt() {
        //print top row
        printTopOrBottom();
        int startNumber = 9;
        if (client.color == ChessGame.TeamColor.BLACK) {
            startNumber = 1;
        }
        //start printing the board

        for (int i = 1; i <= 8; i++){
            String startColor = SET_BG_COLOR_WHITE;
            if (i % 2 == 0) {
                startColor = SET_BG_COLOR_LIGHT_GREY;
            }

            ChessPiece[] pieceList;
            if (client.color == ChessGame.TeamColor.WHITE) {
                pieceList = getRowOfPieces(9 - i);
            } else{
                pieceList = getRowOfPieces(i);
            }
            System.out.print(startColor);

            if (client.color == ChessGame.TeamColor.WHITE) {
                printRowOfPieces(pieceList, startColor, 9 - i);
            } else{
                printRowOfPieces(pieceList, startColor, i);
            }


            if (client.color == ChessGame.TeamColor.BLACK) {
                startNumber += 1;
            } else {
                startNumber -= 1;
            }
        }
        printTopOrBottom();
    }

    public ChessPiece[] getRowOfPieces(int rowNumber){
        var finalList = new ChessPiece[8];
        for (int i=1; i<9; i++){
            if (client.color == ChessGame.TeamColor.WHITE) {
                finalList[i - 1] = board.getPiece(new ChessPosition(rowNumber, i));
            } else {
                finalList[8 - i] = board.getPiece(new ChessPosition(rowNumber, i));
            }
        }
        return finalList;
    }

    public void printRowOfPieces(ChessPiece[] pieces, String color, int rowNumber){
        String numberSpace = " " + rowNumber + " ";
        System.out.print(SET_BG_COLOR_BLUE + numberSpace);

        for (int i=0; i<8; i++){
            String piece = getPrintablePiece(pieces[i]);
            System.out.print(color + " " + piece + " ");

            if (Objects.equals(color, SET_BG_COLOR_WHITE)){
                color = SET_BG_COLOR_LIGHT_GREY;
            }
            else {
                color = SET_BG_COLOR_WHITE;
            }
        }
        System.out.print(SET_BG_COLOR_BLUE + numberSpace + SET_BG_COLOR_DARK_GREY + "\n");
    }

    public void printTopOrBottom(){
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String emptySpace = "   ";
        System.out.print(SET_BG_COLOR_BLUE + emptySpace);
        for (int i=0; i<8; i++){
            if ((Objects.equals(client.color, ChessGame.TeamColor.WHITE))) {
                System.out.print("  " + letters[i] + "  ");
            } else {
                System.out.print("  " + letters[7 - i] + "  ");
            }
        }
        System.out.print(SET_BG_COLOR_BLUE + emptySpace + SET_BG_COLOR_DARK_GREY + "\n");
    }

    public String getPrintablePiece(ChessPiece piece){
        if (piece != null) {
            if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                return getString(piece, BLACK_BISHOP, BLACK_ROOK, BLACK_KING, BLACK_QUEEN, BLACK_KNIGHT, BLACK_PAWN);
            } else{
                return getString(piece, WHITE_BISHOP, WHITE_ROOK, WHITE_KING, WHITE_QUEEN, WHITE_KNIGHT, WHITE_PAWN);
            }
        }
        return EMPTY;
    }

    private String getString(ChessPiece piece, String blackBishop, String blackRook, String blackKing, String blackQueen, String blackKnight, String blackPawn) {
        return switch (piece.getPieceType()) {
            case BISHOP -> blackBishop;
            case ROOK -> blackRook;
            case KING -> blackKing;
            case QUEEN -> blackQueen;
            case KNIGHT -> blackKnight;
            case PAWN -> blackPawn;
        };
    }

}
