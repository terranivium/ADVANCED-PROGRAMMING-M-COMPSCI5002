import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Client {

    private JFrame frame = new JFrame("Checkers");
    private JLabel messageLabel = new JLabel("...");

    private Square[][] board = new Square[8][8];
    private Square moveFrom;
    int fromCol;
    int fromRow;
    private Square moveTo;
    private int redCheckers = 12;
    private int blackCheckers = 12;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    private boolean mousePressed = false;

    public Client(String serverAddress) throws Exception {

        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);

        var boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setBounds(0,0,600,600);
        boardPanel.setLayout(new GridLayout(8, 8, 2, 2));
        for (var i = 0; i < board[0].length; i++) {
            final int k = i;
            for (var j = 0; j < board[0].length; j++) {
                final int l = j;
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    board[i][j] = new Square(i, j);
                    board[i][j].setColour(true);
                } else {
                    board[i][j] = new Square(i, j);
                    board[i][j].setColour(false);
                }
                boardPanel.add(board[k][l]);
            }
            frame.getContentPane().add(boardPanel, BorderLayout.CENTER);
        }
        boardPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                    fromCol = e.getX() / 75;
                    fromRow = e.getY() / 72;
                    moveFrom = board[fromRow][fromCol];
            }
            public void mouseReleased(MouseEvent e){
                    int col = e.getX() / 75;
                    int row = e.getY() / 72;
                    moveTo = board[row][col];
                    out.println("MOVE " + fromRow + fromCol + row + col);
                    System.out.println("MOVE " + fromRow + fromCol + row + col);
            }
        });
    }

    /**
     * The main thread of the client will listen for messages from the server. The
     * first message will be a "WELCOME" message in which we receive our mark. Then
     * we go into a loop listening for any of the other messages, and handling each
     * message appropriately. The "VICTORY", "DEFEAT", "TIE", and
     * "OTHER_PLAYER_LEFT" messages will ask the user whether or not to play another
     * game. If the answer is no, the loop is exited and the server is sent a "QUIT"
     * message.
     */
    public void play() throws Exception {
        try {
            var response = in.nextLine();
            var mark = response.charAt(8);
            var opponentMark = mark == 'r' ? 'b' : 'r';
            frame.setTitle("Checkers: Player " + mark);
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("Valid move, please wait");
                    moveFrom.setText(' ');
                    moveTo.setText(mark);
                    moveFrom.repaint();
                    moveTo.repaint();
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    var loc1 = Integer.parseInt(response.substring(15,16));
                    var loc2 = Integer.parseInt(response.substring(16,17));
                    board[loc1][loc2].setText(opponentMark);
                    board[loc1][loc2].repaint();
                    messageLabel.setText("Opponent moved, your turn");
                } else if (response.startsWith("MESSAGE")) {
                    messageLabel.setText(response.substring(8));
                } else if (response.startsWith("VICTORY")) {
                    JOptionPane.showMessageDialog(frame, "Winner Winner");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    JOptionPane.showMessageDialog(frame, "Sorry you lost");
                    break;
                } else if (response.startsWith("TIE")) {
                    JOptionPane.showMessageDialog(frame, "Tie");
                    break;
                } else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    JOptionPane.showMessageDialog(frame, "Other player left");
                    break;
                }
            }
            out.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
            frame.dispose();
        }
    }

    static class Square extends JPanel {
        JLabel label = new JLabel();
        int i;
        int j;

        public Square(int i, int j) {
            setLayout(new GridBagLayout());
            label.setFont(new Font("Arial", Font.BOLD, 40));
            add(label);
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        public void setColour(boolean squareColour){
            if(squareColour){
                setBackground(Color.white);
            } else setBackground(Color.black);
        }

        public void setText(char text) {
            label.setForeground(text == 'r' ? Color.RED : Color.BLUE);
            label.setText(text + "");
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("127.0.0.1");
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setSize(600, 600);
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        client.play();
    }
}