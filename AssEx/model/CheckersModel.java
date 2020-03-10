//package model;

import java.io.*;
import java.util.*;

public class CheckersModel{

    private char[][] board; // Stores the checkerboard, with chars 'r','b','_'
    private int redCheckers; // Number of red checkers on the board
    private int blackCheckers; // Number of black checkers on the board
    private char playerTurn; // Either 'r' or 'b', for who's move it currently is.

    // Constructs default Checkers object, initializing board to starting
    // playing position.
    public CheckersModel(){
		board = new char[8][8];
		redCheckers = 12;
		blackCheckers = 12;
		playerTurn = 'r';

		// Initialize board with all the red and black checkers in starting
		// positions.
		int i,j;
		for (i=0;i<8;i++){
		    for (j=0;j<8;j++){
				board[i][j] = '_';
		    }
		}
		for (i=1;i<8;i+=2) {
		    board[i][1] = 'r';
		    board[i][5] = 'b';
		    board[i][7] = 'b';
		}
		for (i=0;i<8;i+=2) {
		    board[i][0] = 'r';
		    board[i][2] = 'r';
		    board[i][6] = 'b';
		}
    }

    // Print out the checkerboard, by looping through all board positions in
    // appropriate order.
    public String printBoard(){
		int i;
		int j;
		String output;
		output = "  1 2 3 4 5 6 7 8 x\n";
		for (i=0;i<8;i++) {
		    output += (i+1) + " ";
		    for (j=0;j<8;j++) {
				output += board[j][i] + " ";
		    }
		    output += "\n";
		}
		output += "y\n";
		return output;
    }

    // This method executes one move.
    public void getNextMove() throws IOException {
	
		Scanner userInput = new Scanner(System.in);

		if (playerTurn=='r'){
		    System.out.println("It is your turn, red.");
		} else {
		    System.out.println("It is your turn, black.");
		}

		boolean moved = false;
		// Loops until legal move is entered.
		while (!moved) {
		    // Reads in square to move from and to.
		    System.out.println("Enter from the square you would like to move from.");
		    System.out.print("Enter as a 2-digit number. (e.g. if you were moving from");
		    System.out.println(" x=1,y=3, enter 13");
		    int moveFrom = userInput.nextInt();

		    System.out.print("Enter from the square you would like to move to, ");
		    System.out.println("using the same convention.");
		    int moveTo = userInput.nextInt();

		    // Checks to see if move is valid, if so, executes it.
		    if (validMove(moveFrom, moveTo)){
				executeMove(moveFrom, moveTo);
				moved = true;
		    } else {
				System.out.println("That was an invalid move, try again.");
			}
		}

		// Update playerTurn it is.
		if (playerTurn == 'r'){
		    playerTurn = 'b';
		} else {
		    playerTurn = 'r';
		}
	}

    // Checks if a move is valid.
    public boolean validMove(int moveFrom, int moveTo) {

		// Gets array indeces corresponding to the move, from parameters.
		int xFrom = moveFrom / 10 - 1;
		int yFrom = moveFrom % 10 - 1;
		int xTo = moveTo / 10 - 1;
		int yTo = moveTo % 10 - 1;
		
		// Check if indeces in range, if not, return false.
		if (xFrom < 0 || xFrom > 7 || yFrom < 0 || yFrom > 7 || xTo < 0 || xTo > 7 || yTo < 0 || yTo > 7){ 
		    	return false;
		// Check to see you are moving your piece to a blank square.
		} else if (board[xFrom][yFrom]==playerTurn && board[xTo][yTo]=='_') {
		    // Checks case of simple move
		    if (Math.abs(xFrom-xTo)==1){
				if ((playerTurn == 'r') && (yTo - yFrom == 1)){
			    	return true;
				} else if((playerTurn == 'b') && (yTo - yFrom == -1)){
			    	return true;
				}
		    // Checks case of a jump
		    } else if (Math.abs(xFrom-xTo)==2) {
				if (playerTurn == 'r' && (yTo - yFrom == 2) && board[(xFrom+xTo)/2][(yFrom+yTo)/2] == 'b'){
				    	return true;  
				} else if (playerTurn == 'b' && (yTo - yFrom == -2) && board[(xFrom+xTo)/2][(yFrom+yTo)/2] == 'r'){
				    	return true;
				    }
			}
		}
		// If move is neither a simple one or a jump, it is not legal.
		return false;
    }

    // Executes a move.
    public void executeMove(int moveFrom, int moveTo) {
		// Gets array indeces corresponding to the move, from parameters.
		int xFrom = moveFrom / 10 - 1;
		int yFrom = moveFrom % 10 - 1;
		int xTo = moveTo / 10 - 1;
		int yTo = moveTo % 10 - 1;
		
		// Change appropriate board elements and decrement redCheckers or
		// blackCheckers if necessary.
		board[xFrom][yFrom] = '_';
		board[xTo][yTo] = playerTurn;
		if (Math.abs(xTo - xFrom) == 2){
		    board[(xFrom+xTo)/2][(yFrom+yTo)/2] = '_';
		    if (playerTurn == 'r'){
				redCheckers--;
		    } else{
				blackCheckers--;
		    }
		}
    }

    // Checks to see if game is over based on number of checkers left.
    public boolean gameOver() {
		return (redCheckers == 0 || blackCheckers == 0);
    }

    // Returns color of the winner.
    public String winnerIs() {
		if (blackCheckers == 0){
		    return "red";
		} else {
		    return "black";
		}
    }

  //   public static void main(String args[]) throws IOException {

		// // Setup and print out checker board.
		// CheckersModel model = new CheckersModel();
		// System.out.print(model.printBoard());
		
		// // // Loop until game is over.
		// // while (!this.model.gameOver()){
		// //     //Execute a move and print the board out afterwards.
		// //     this.model.getNextMove();
		// //     System.out.print(this.model.printBoard());
		// // }
		// // // Announce winner.
		// // System.out.println("The winner is " + model.winnerIs());
  //   }
}