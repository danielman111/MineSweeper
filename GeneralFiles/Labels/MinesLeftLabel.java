package GeneralFiles.Labels;
import java.awt.Component;

import javax.swing.JLabel;

/*Name: MinesLeftLabel
propose: This class represents the label that shows the mines left
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class MinesLeftLabel extends JLabel{

	/*Fields*/
	//minesLeft- remembers how many mines are left
	private int minesLeft;

	/*Behavior*/
	/*Constructors*/
	//this constructor creates the label and sets the counter
	public MinesLeftLabel(int mines){
		super(String.format("%03d", mines));
		this.minesLeft= mines;
	}//MinesLeftLabel(int)


	//sets the number of mines left
	public void setNumOfMines(int mines){
		this.minesLeft= mines;
		this.setText(String.format("%03d", this.minesLeft));
	}//setNumOfMines(int)


	//decreases the amount of mines left by 1
	public void mineFound(){
		this.minesLeft--;
		this.setText(String.format("%03d", this.minesLeft));	//updates the label
	}//mineFound()


	//increases the amount oof mines left by 1
	public void mineLost(){
		this.minesLeft++;
		this.setText(String.format("%03d", this.minesLeft));	//updates the label
	}//minesLost()


	//returns the number of mines left
	public int getMinesLeft(){
		return this.minesLeft;
	}//getMinesLeft


}//class MinesLeftLabel