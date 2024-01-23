package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private final int row;
    private final int col;

    public ChessPosition(int row, int col) {

        this.row = row;
        this.col = col;

        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.row;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.col;
        //throw new RuntimeException("Not implemented");
    }

    public boolean isValid(){
        return row <= 8 && col <= 8 && row > 0 && col > 0;
    }

    public ChessPosition changePosition(int r, int c){
        return new ChessPosition(row + r, col + c);
    }

    public boolean endPositionValid(int r, int c){
        return r + row <= 8 && r + row > 0 && col + c > 0 && col + c <= 8;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (obj.getClass() != getClass()){
            return false;
        }
        ChessPosition o = (ChessPosition)obj;
        return this.col == o.col && row == o.row;
    }
    @Override
    public int hashCode(){
        return (getRow() % 4) * 4 + (getColumn() % 4) + getColumn() * 3;
    }

    @Override
    public String toString(){
        return String.format("ChessPosition(row: %d, column: %d)", getRow(), getColumn());
    }
}

