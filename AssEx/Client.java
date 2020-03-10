import java.net.Socket;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.Scanner;

public class Client{
    public static void main(String[] args) {
        String output;
        Socket serverConnect = null;
        try {
            serverConnect = new Socket("127.0.0.1", 8765);

            PrintWriter outToServer =
                new PrintWriter(serverConnect.getOutputStream(), true);
            Scanner inFromServer =
                new Scanner(
                    new InputStreamReader(serverConnect.getInputStream()));
            BufferedReader inFromUser = 
                new BufferedReader(
                    new InputStreamReader(System.in));

            while(inFromServer.hasNext()){
                output = inFromServer.nextLine();
                System.out.println(output);
            }

            inFromUser.close();
            inFromServer.close();
            outToServer.close();

            System.out.println();
            System.out.println("----------------------");
            System.out.println("Thank you for playing!");
            System.out.println("----------------------");

            serverConnect.close(); // close the socket
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
}