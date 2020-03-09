package view;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JFrame;
import model.*;

public class CheckersView extends JFrame{

	private CheckersBoard model;

	public CheckersView(CheckersBoard model){
		this.model = model;

		// Main JFrame View Settings
		this.setSize(640,480); // Set window/frame size
		this.setResizable(false); // Lock window/frame size
		this.setLocation(100,100); // Set window/frame open location on screen
		this.setTitle("Checkers 2020, ver. 1.0"); // Set window/frame title bar text
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Stop run on window/frame close
		this.setLayout(new GridBagLayout()); // Set layout manager for JFrame

		GridBagConstraints c = new GridBagConstraints(); // Holds constraints for gridbag layout manager
		c.fill = GridBagConstraints.HORIZONTAL;

		// Create new instances of each panel and apply layout rules
		BoardPanel board = new BoardPanel();

		

	}
	
	// View state called on new game instance
	public void newGameState(){

	}

	public void update(){

	}

}