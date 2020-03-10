import java.net.Socket;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.Scanner;
//import view.*;
//import controller.*

public class Client{

    public static void main(String[] args) {
        String output;
        Socket s = null;
        try {
            s = new Socket("127.0.0.1", 8765);

            //CheckersController controller = new CheckersController();

            PrintWriter outToServer =
                new PrintWriter(s.getOutputStream(), true);
            Scanner inFromServer =
                new Scanner(
                    new InputStreamReader(s.getInputStream()));
            BufferedReader inFromUser = 
                new BufferedReader(
                    new InputStreamReader(System.in));

            //this.controller.clientRuntime();
            //this.view.clientRuntime();
            
            //Scanner scanner = new Scanner(System.in);
            //System.out.println(scanner.nextLine());
            //String c = scanner.nextLine(); // Print the character
            //outToServer.println(c);
            while(inFromServer.hasNext()){
                output = inFromServer.nextLine();
                System.out.println(output);
            }

            inFromUser.close();
            inFromServer.close(); // Close the reader
            outToServer.close(); // Close the reader
            s.close(); // close the socket
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
}