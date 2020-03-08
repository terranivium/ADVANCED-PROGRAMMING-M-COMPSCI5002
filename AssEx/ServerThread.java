import java.net.Socket;
import java.io.OutputStreamWriter;

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
            OutputStreamWriter osClient = new OutputStreamWriter(this.client.getOutputStream());

            osClient.write('x');
            osClient.flush();
            this.client.close();
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
	}
}