import java.net.Socket;
import java.net.ServerSocket;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server{

    // private CheckersModel model;

    // public void startNewGame(final Socket p1, final Socket p2){
    //     new Thread(new Runnable(){
    //         @Override
    //         public void run(){
    //             // Displaying the thread that is running 
    //             System.out.println ("Thread " + Thread.currentThread().getId() + " is running");
    //             //OutputStreamWriter osClient = new OutputStreamWriter(client.getOutputStream());

    //             //osClient.write('x');

    //             try{
    //                 PrintWriter outToClient1 =
    //                     new PrintWriter(p1.getOutputStream(), true);
    //                 PrintWriter outToClient2 =
    //                     new PrintWriter(p2.getOutputStream(), true);
    //                 BufferedReader inFromClient1 =
    //                     new BufferedReader(
    //                         new InputStreamReader(p1.getInputStream()));
    //                 BufferedReader inFromClient2 =
    //                     new BufferedReader(
    //                         new InputStreamReader(p2.getInputStream()));

    //                  // MVC instances, threads share the same model?
                    
    //                 outToClient1.print(model.printBoard());
    //                 outToClient2.print(model.printBoard());
    //             } catch(Exception e){
    //                 ;
    //             }

    //             // // Loop until game is over.
    //             // while (!model.gameOver()){
    //             // //Execute a move and print the board out afterwards.
    //             //     model.getNextMove();
    //             //     model.printBoard();
    //             // }
    //             // // Announce winner.
    //             // System.out.println("The winner is " + model.winnerIs());
    //         }
    //     }).start();
    // }        

    public static void main(String[] args) {
        ServerSocket listener = null;
        Socket client1 = null;
        Socket client2 = null;
        try {
            listener = new ServerSocket(8765);
            System.out.println("Server initialised...");

            client1 = listener.accept();
            System.out.println("Player One connected!");
            System.out.println("Waiting for second player...");

            client2 = listener.accept();
            System.out.println("Player Two connected!");
            System.out.println("GAME START!");
            
            Thread game = new Thread(new ServerThread(client1, client2));

            game.start();
            try {
                game.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("---------");
            System.out.println("PRINT END");
            System.out.println("---------");

            listener.close();
        } catch(Exception e){
            e.printStackTrace(); 
        }
    }    
}