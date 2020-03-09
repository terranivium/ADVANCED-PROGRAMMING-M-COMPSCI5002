import java.net.Socket;
import java.net.ServerSocket;
import model.*;


public class Server{

    private 

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
    
            // MVC instances, threads share the same model?
            CheckersBoard model = new CheckersBoard();

            Thread c1 = new Thread(new ServerThread(client1, model));
            Thread c2 = new Thread(new ServerThread(client2, model));

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