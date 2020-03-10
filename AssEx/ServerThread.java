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
        model = new CheckersModel();
	}

    public void run(){
        // Displaying the thread that is running 
        System.out.println ("Thread " + Thread.currentThread().getId() + " is running");

        //osClient.write('x');

        try{
            OutputStreamWriter outToClient1 = new OutputStreamWriter(p1.getOutputStream());
            OutputStreamWriter outToClient2 = new OutputStreamWriter(p2.getOutputStream());
            
            String name = model.printBoard();
            outToClient1.write(name);
            outToClient2.write(name);
            outToClient1.flush();
            outToClient2.flush();

            BufferedReader inFromClient1 =
                new BufferedReader(
                    new InputStreamReader(p1.getInputStream()));
            BufferedReader inFromClient2 =
                new BufferedReader(
                    new InputStreamReader(p2.getInputStream()));

             // MVC instances, threads share the same model

        } catch(Exception e){
            ;
        }
        //System.out.print(name);

        // // Loop until game is over.
                // while (!model.gameOver()){
                // //Execute a move and print the board out afterwards.
                //     model.getNextMove();
                //     model.printBoard();
                // }
                // // Announce winner.
                // System.out.println("The winner is " + model.winnerIs());
    }

	// public void run(){
	// 	try
 //        { 
 //            // Displaying the thread that is running 
 //            System.out.println ("Thread " + Thread.currentThread().getId() + " is running");
 //            //OutputStreamWriter osClient = new OutputStreamWriter(this.client.getOutputStream());

 //            //osClient.write('x');

 //            PrintWriter outToClient =
 //        		new PrintWriter(client.getOutputStream(), true);
 //    		BufferedReader inFromClient =
 //        		new BufferedReader(
 //            		new InputStreamReader(client.getInputStream()));

 //        	System.out.println(inFromClient.readLine());
 //        	in.close();
 //        	out.close();
 //            //osClient.flush();
 //            this.client.close();
 //        } 
 //        catch (Exception e) 
 //        { 
 //            // Throwing an exception 
 //            System.out.println ("Exception is caught"); 
 //        } 
	// }
}