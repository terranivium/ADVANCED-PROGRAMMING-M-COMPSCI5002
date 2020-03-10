import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerThread implements Runnable{

	private Socket p1;
    private Socket p2;
    private CheckersModel model;

	public ServerThread(Socket p1, Socket p2){
		this.p1 = p1;
        this.p2 = p2;
        this.model = new CheckersModel();
	}

    public void run(){
        // Displaying the thread that is running 
        System.out.println ("Game thread is running...");

        try{
            BufferedReader inFromClient1 =
                new BufferedReader(
                    new InputStreamReader(this.p1.getInputStream()));
            BufferedReader inFromClient2 =
                new BufferedReader(
                    new InputStreamReader(this.p2.getInputStream()));

            OutputStreamWriter outToClient1 = new OutputStreamWriter(this.p1.getOutputStream());
            OutputStreamWriter outToClient2 = new OutputStreamWriter(this.p2.getOutputStream());
            
            String outString = this.model.printBoard();
            outToClient1.write(outString);
            outToClient2.write(outString);
            outToClient1.flush();
            outToClient2.flush();

            // Loop until game is over.
            while (!this.model.gameOver()){
            //Execute a move and print the board out afterwards.
                if(this.model.getPlayerTurn().equals('r'));{
                    this.model.getNextMove();
                } else if(this.model.getPlayerTurn().equals('b')){

                } else{
                    ;
                }
                
                outString = this.model.printBoard();
                outToClient1.write(outString);
                outToClient2.write(outString);
                outToClient1.flush();
                outToClient2.flush();
            }

            // Announce winner.
            System.out.println();
            System.out.println("The winner is " + this.model.winnerIs());
            outToClient1.close();
            outToClient2.close();
            this.p1.close();
            this.p2.close();
        } catch(Exception e){
            ;
        }
    }
}