package controller;

public class CheckersController implements ActionListener{

	private CheckersView view;
	private CheckersModel model;

	public CheckersController(CheckersView view, CheckersModel model){
		this.model = model;
		this.view = view;
	}
	
	public CheckersView getView(){
		return this.view;
	}

	public void actionPerformed(ActionEvent e){
		// get sources
		// call model funcions
		// update view
	}
}