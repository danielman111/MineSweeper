package GeneralFiles.Buttons;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import GeneralFiles.Game;

/*Name: SaveGame
propose: This class represents the button that saves the game
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class SaveGame extends JButton implements ActionListener{
	
	/*Fields*/
	//gameplay- the game that will be saved
	private Game gameplay;

	
	/*Behavior*/
	/*Constructors*/
	//this constructor creates the button and links the current game to it
	public SaveGame(Game gameplay){
		super("Save");
		this.gameplay= gameplay;
		this.addActionListener(this);
	}//SaveGame(Game)


	//implements the ActionListener interface
	//saves the game when the button is pressed
	public void actionPerformed(ActionEvent e) {
		this.gameplay.saveGame();
	}//actionPerformed(ActionEvent)
	
	
}//class SaveGame