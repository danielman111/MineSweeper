package GeneralFiles.Popups;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowFocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GeneralFiles.FocusListener;
import GeneralFiles.Game;

/*Name: GameLost
propose: This class creates a popup telling the user he has lost the game
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class GameLost extends JFrame implements ActionListener {

	/*Fields*/
	//lostGame- the game that the user just lost- saved so it can be closed
	private Game lostGame;
	//windowFocusListener- a window focus listener for this window
	private WindowFocusListener windowFocusListener;
	//OK- a button that will close the popup
	private final JButton OK = new JButton("ok");
	//msg- a label telling the user he lost
	private final JLabel msg = new JLabel("you have lost the game");


	/*Behavior*/
	/*Constructors*/
	//this constructor creates the popup and brings it to screen
	public GameLost (Game lostGame){
		super("Game lost");
		this.lostGame= lostGame;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		this.windowFocusListener = new FocusListener(this);
		addWindowFocusListener(this.windowFocusListener);	//adds the windowFocusListener as a listener to this frame
		this.OK.addActionListener(this);	//adds this as a listener to the "ok" button

		GridBagConstraints cont = new GridBagConstraints();
		cont.insets = new Insets(10,5,10,5);	//sets the location of all the components
		GridBagConstraints tLabelConst = (GridBagConstraints)cont.clone();
		tLabelConst.fill = GridBagConstraints.NONE;
		tLabelConst.weightx = 0.0;

		GridBagConstraints grid = (GridBagConstraints)tLabelConst.clone();
		grid.gridx = 0; grid.gridy = 0;
		add(this.msg, grid);

		GridBagConstraints grid3 = (GridBagConstraints)tLabelConst.clone();
		grid3.gridx = 0; grid3.gridy = 4;
		add(this.OK, grid3);

		Dimension size = this.getToolkit().getScreenSize();
		this.setLocation(size.width/2-this.getWidth()/2 - 100, size.height/2 - this.getHeight()/2 - 100);
		this.setSize(200, 100);
		this.setResizable(false);
		this.setVisible(true);
	}//GameLost(Game)

	
	//this method closes the game that was lost and this popup
	public void closeThis(){
		if (this.lostGame != null)
			this.lostGame.replayGame();
		dispose();
	}//closeThis()

	
	//implements the ActionListener interface
	//closes the popup and the game that was lost
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == this.OK)
			dispose();
	}//actionPerformed(ActionEvent)

}//class GameLost
