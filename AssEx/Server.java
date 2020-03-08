import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server{
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
 
            Thread c1 = new Thread(new ServerThread(client1));
            Thread c2 = new Thread(new ServerThread(client2));

            c1.start();
            c2.start();
            try{
                c1.join();
                c2.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("---------");
            System.out.println("PRINT END");
            System.out.println("---------");

            listener.close();
        } catch(Exception e) {
            e.printStackTrace(); 
        }
    }    
}