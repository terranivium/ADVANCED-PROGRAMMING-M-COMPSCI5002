import java.net.Socket;
import java.net.ServerSocket;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server{      
    public static void main(String[] args) {
        ServerSocket listener = null;
        Socket client1 = null;
        Socket client2 = null;
        try {
            listener = new ServerSocket(8765);
            System.out.println("------------------------------");
            System.out.println("|Checkers Server CLI ver. 1.0|");
            System.out.println("------------------------------");

            client1 = listener.accept();
            System.out.println();
            System.out.println("Player One connected!");
            System.out.println("Waiting for second player...");

            client2 = listener.accept();
            System.out.println();
            System.out.println("Player Two connected!");
            
            Thread game = new Thread(new ServerThread(client1, client2));
            game.start();
            
            try {
                game.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            System.out.println();
            System.out.println("-------------------------");
            System.out.println("Server session closing...");
            System.out.println("-------------------------");
            listener.close();
        } catch(Exception e){
            e.printStackTrace(); 
        }
    }    
}