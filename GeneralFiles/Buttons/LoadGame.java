package GeneralFiles.Buttons;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import GeneralFiles.Game;

/*Name: LoadGame
propose: This class represents the button that loads the game
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class LoadGame extends JButton implements ActionListener{

	/*Fields*/
	//gameplay- the game that will be loaded
	private Game gameplay;

	/*Behavior*/
	/*Constructors*/
	//this constructor creates the button and links the current game to it
	public LoadGame(Game gameplay){
		super("Load");
		this.gameplay= gameplay;
		this.addActionListener(this);
	}//LoadGame(Game)

	
	//implements the ActionListener interface
	//loads the game when the button is pressed
	public void actionPerformed(ActionEvent e) {
		this.gameplay.loadGame();
	}//actionPerformed(ActionEvent)
	
	
}//class LoadGame
