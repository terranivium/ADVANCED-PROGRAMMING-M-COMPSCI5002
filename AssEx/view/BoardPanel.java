package view;

import java.awt.event.ActionListener;

public class BoardPanel extends JPanel{

	// Import model board in this class for visual updates.

	public BoardPanel(){
		for(int i = 0; i < 64; i++) {
  			JPanel square = new JPanel(new BorderLayout());
  			this.add(square);

 
  			int row = (i / 8) % 2;
  			if (row == 0){
  				square.setBackground( i % 2 == 0 ? Color.blue : Color.white );
  			} else {
  				square.setBackground( i % 2 == 0 ? Color.white : Color.blue );
  			}
		}
}