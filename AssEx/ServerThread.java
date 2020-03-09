import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerThread implements Runnable{

	private Socket client;

	public ServerThread(Socket client){
		this.client = client;
	}

	public void run(){
		try
        { 
            // Displaying the thread that is running 
            System.out.println ("Thread " + Thread.currentThread().getId() + " is running");
            //OutputStreamWriter osClient = new OutputStreamWriter(this.client.getOutputStream());

            //osClient.write('x');

            PrintWriter outToClient =
        		new PrintWriter(client.getOutputStream(), true);
    		BufferedReader inFromClient =
        		new BufferedReader(
            		new InputStreamReader(client.getInputStream()));

        	System.out.println(inFromClient.readLine());
        	in.close();
        	out.close();
            //osClient.flush();
            this.client.close();
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
	}
}