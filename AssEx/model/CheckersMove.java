package model;

public class CheckersMove{
    // A CheckersMove object represents a move in the game of Checkers.
    // It holds the row and column of the piece that is to be moved
    // and the row and column of the square to which it is to be moved.
    // (This class makes no guarantee that the move is legal.)
    int fromRow
    int fromCol;  // Position of piece to be moved.
    int toRow
    int toCol;      // Square it is to move to.
    
    public CheckersMove(int fromRow, int fromCol, int toRow, int toCol){
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    public boolean isJump(){
        // Test whether this move is a jump.  It is assumed that
        // the move is legal.  In a jump, the piece moves two
        // rows.  (In a regular move, it only moves one row.)
        return (fromRow - toRow == 2 || fromRow - toRow == -2);
    }
}