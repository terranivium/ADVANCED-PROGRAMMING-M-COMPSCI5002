import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Server{

    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(58901)) {
            System.out.println("Checkers Server is Running...");
            var pool = Executors.newFixedThreadPool(2);
            while (true) {
                Game game = new Game();
                pool.execute(game.new Player(listener.accept(), 'r'));
                System.out.println("Player 1 connected...");
                pool.execute(game.new Player(listener.accept(), 'b'));
                System.out.println("Player 2 connected...");
                game.setupBoard(); // initialise game board with pieces
            }
        }
    }
}

class Game {

    // Board cells numbered 0-8, top to bottom, left to right; null if empty
    private Player[][] board = new Player[8][8];
    Player currentPlayer;

    public Game(){

    }

    // populates the board with player pieces
    public void setupBoard(){
        int i;
        int j;
        for (i=0;i<8;i++){
            for (j=0;j<8;j++){
                board[j][i] = null;
            }
        }
        for (i=1;i<8;i+=2) {
            board[1][i] = currentPlayer;
            board[5][i] = currentPlayer.opponent;
            board[7][i] = currentPlayer.opponent;
        }
        for (i=0;i<8;i+=2) {
            board[0][i] = currentPlayer;
            board[2][i] = currentPlayer;
            board[6][i] = currentPlayer.opponent;
        }
    }

    public boolean hasWinner() {
        return (board[0] != null && board[0] == board[1] && board[0] == board[2])
                || (board[3] != null && board[3] == board[4] && board[3] == board[5])
                || (board[6] != null && board[6] == board[7] && board[6] == board[8])
                || (board[0] != null && board[0] == board[3] && board[0] == board[6])
                || (board[1] != null && board[1] == board[4] && board[1] == board[7])
                || (board[2] != null && board[2] == board[5] && board[2] == board[8])
                || (board[0] != null && board[0] == board[4] && board[0] == board[8])
                || (board[2] != null && board[2] == board[4] && board[2] == board[6]);
    }

    // Move logic handled here, checks for legal/illegal movements
    public synchronized void move(int moveFrom1, int moveFrom2, int moveTo1, int moveTo2, Player player) {
        System.out.print(moveFrom1 + "" + moveFrom2 + " " + moveTo1 + "" + moveTo2 + "\n");
        if (player != currentPlayer) {
            throw new IllegalStateException("Not your turn...");
        } else if (player.opponent == null) {
            throw new IllegalStateException("You don't have an opponent...");
        } else if (board[moveTo1][moveTo2] != null) {
            throw new IllegalStateException("Board square already occupied...");
        } else if (board[moveFrom1][moveFrom2] == null) {
            throw new IllegalStateException("Board square has no piece...");
        } else if (board[moveFrom1][moveFrom2] != currentPlayer) {
            throw new IllegalStateException("Cannot move opponents pieces...");
        }
//        System.out.println(Math.abs((moveFrom1 + moveFrom2) - (moveTo1 + moveTo2)));
//        System.out.println(currentPlayer.mark);
//        System.out.println(moveTo2 - moveFrom2);
//        System.out.println(Math.abs(moveTo1 - moveFrom1));

        // control valid moves
        // standard move
        if ((currentPlayer.mark == 'r' &&
                    (moveTo1 - moveFrom1) == 1 &&
                    Math.abs(moveTo2 - moveFrom2) == 1) ||
                (currentPlayer.mark == 'b' &&
                        (moveTo1 - moveFrom1) == -1 &&
                        Math.abs(moveTo2 - moveFrom2) == 1)) {
            board[moveFrom1][moveFrom2] = null;
            board[moveTo1][moveTo2] = currentPlayer;
            currentPlayer = currentPlayer.opponent;
            // check for jump
//        } else if((currentPlayer.mark == 'r' &&
//                    (moveTo1 - moveFrom1) == 2 &&
//                    Math.abs(moveTo2 - moveFrom2) == 2 &&
//                    board[(moveTo1-moveFrom1)/2][Math.abs(moveTo2-moveFrom2)/2].mark == 'b') ||
//                (currentPlayer.mark == 'b' &&
//                        (moveTo1 - moveFrom1) == -2 &&
//                        Math.abs(moveTo2 - moveFrom2) == 2 &&
//                        board[(moveTo1-moveFrom1)/2][Math.abs(moveTo2-moveFrom2)/2].mark == 'r')){
//            board[moveFrom1][moveFrom2] = null;
//            board[(moveTo1-moveFrom1)/2][(moveTo2-moveFrom2)/2] = null;
//            board[moveTo1][moveTo2] = currentPlayer;
//            currentPlayer = currentPlayer.opponent;
        } else{
            throw new IllegalStateException("Invalid move " + currentPlayer.mark + ": " + moveFrom1 + moveFrom2 + moveTo1 + moveTo2);
        }
    }

    class Player implements Runnable {
        char mark;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;

        public Player(Socket socket, char mark) {
            this.socket = socket;
            this.mark = mark; // either 'r' for red or 'b' for black
            try{
                setup(); // assign current and opponent player
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                processCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("OTHER_PLAYER_LEFT");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + mark);
            if (mark == 'r') {
                currentPlayer = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                opponent.output.println("MESSAGE Your move");
            }
        }

        private void processCommands() {
            while (input.hasNextLine()) {
                var command = input.nextLine();
                if (command.startsWith("QUIT")) {
                    return;
                } else if (command.startsWith("MOVE")) {
                    processMoveCommand(Integer.parseInt(command.substring(5, 6)), Integer.parseInt(command.substring(6, 7)), Integer.parseInt(command.substring(7, 8)), Integer.parseInt(command.substring(8, 9)));
                }
            }
        }

        private void processMoveCommand(int moveFrom1, int moveFrom2, int moveTo1, int moveTo2) {
            try {
                move(moveFrom1, moveFrom2, moveTo1, moveTo2, this);
                output.println("VALID_MOVE");
                opponent.output.println("OPPONENT_MOVED " + moveFrom1 + moveFrom2 + moveTo1 + moveTo2);
//                if (hasWinner()) {
//                    output.println("VICTORY");
//                    opponent.output.println("DEFEAT");
//                } else if (boardFilledUp()) { no possible moves
//                    output.println("TIE");
//                    opponent.output.println("TIE");
//                }
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }
    }
}