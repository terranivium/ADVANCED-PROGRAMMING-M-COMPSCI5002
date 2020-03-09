import java.net.Socket;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.Scanner;
import view.*;
import controller.*

public class Client{
    public static void main(String[] args) {
        Socket s = null;
        try {
            s = new Socket("127.0.0.1", 8765);

            CheckersController controller = new CheckersController();

            PrintWriter outToServer =
                new PrintWriter(s.getOutputStream(), true);
            BufferedReader inFromServer =
                new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            BufferedReader inFromUser = 
                new BufferedReader(
                    new InputStreamReader(System.in));

            this.controller.clientRuntime();
            this.view.clientRuntime();
            
            Scanner scanner = new Scanner(System.in);
            String c = scanner.nextLine(); // Print the character
            outToServer.println(c);

            in.close(); // Close the reader
            out.close(); // Close the reader
            s.close(); // close the socket
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
}