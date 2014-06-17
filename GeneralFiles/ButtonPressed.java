package GeneralFiles;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import GeneralFiles.Labels.MinesLeftLabel;

/*Name: ButtonPressed
propose: This class handles a press on one of the game's tiles
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class ButtonPressed implements MouseListener, Serializable {

	/*Fields*/
	//serialVersionUID- the object's ID, for saving purposes
	private static final long serialVersionUID = -773448039245460819L;
	//x- the x coordinate of the tile this is attached to
	private int x;
	//y- the y coordinate of the tile this is attached to
	private int y;
	//boardArr- a link to the whole game board
	private Cell[][] boardArr;
	//buttons- a link to the array of buttons for visual use
	private JButton[][] buttons;
	//minesLabel- a link to the 'mines left' label
	private MinesLeftLabel minesLabel;
	//a link to the game
	private Game gameplay;


	/*Behavior*/
	/*Constructors*/
	//generates a new instance of this class to be attached to a game tile
	public ButtonPressed(int x, int y, Cell[][] boardArr, JButton[][] buttons, MinesLeftLabel minesLabel, Game gameplay){
		this.x= x;
		this.y= y;
		this.boardArr= boardArr;
		this.buttons= buttons;
		this.minesLabel= minesLabel;
		this.gameplay= gameplay;
	}//ButtonPressed(int, int, Cell[][], JButton[][], MinesLeftLabel, Game)


	//simulates a left click on a game tile
	protected void leftClick(int x, int y){
		if ((!this.boardArr[x][y].getIsMarked()) && (!this.boardArr[x][y].getIsPressed())){	//if the tile is not a flag and has not been pressed before
			boolean helper= this.boardArr[x][y].press();	//presses the tile, also getting an indication if it was a mine
			if (helper){	//if it was a mine
				if (!this.gameplay.isFirstMove())	//if this wasen't the first move for the game
					this.gameplay.mineHit();	//ends the game with a mine hit
				else{	//if it is the first move for the game
					mineSwitch();	//moves this mine to another cell without a mine
					leftClick(x, y);	//restarts the leftclick simulation
				}
			}
			else{	//if this cell is not a mine
				int cellNumber= calcCellNum(x, y);	//calculates the amount of mines around this cell
				if (cellNumber == 0){	//if there are no mines
					this.gameplay.grayOutButton(x, y);	//grays-out this cell
					leftClickHelper(x, y);	//recursively calls for all adjacent cells
				}
				else {	//if there are mines around this cell
					this.buttons[x][y].setText("" + cellNumber);	//prints the number of mines on this cell
				}
			}
		}
	}//leftClick(int, int)


	//recursively opens for all adjacent cells to cell [x,y]
	private void leftClickHelper(int x, int y){	//is only called if cell is 0
		for (int i=-1; i <= 1; i++)
			for (int j=-1; j <= 1; j++){
				int newX= x+i;
				int newY= y+j;
				if ((sizeCheck(this.boardArr, newX)) && (sizeCheck(this.boardArr[newX], newY)) && (!((j == 0) && (i == 0)))){	//for the 8 adjacent cells
					if ((!this.boardArr[newX][newY].getIsMarked()) && (!this.boardArr[newX][newY].getIsPressed()) && (!this.boardArr[newX][newY].getIsMine())){	//if the cell is not marked with a flag, not pressed and isen't a mine
						this.boardArr[newX][newY].press();	//press the cell
						int cellNumber= calcCellNum(newX, newY);
						if (cellNumber == 0){	//if this is an empty cell- recursively call for all adjacent cells
							this.gameplay.grayOutButton(newX, newY);
							leftClickHelper(newX, newY);
						}
						else {	//if there are mines around this cell
							this.buttons[newX][newY].setText("" + cellNumber);	//prints the number of mines on this cell
						}
					}
				}
			}
	}//leftClickHelper(int, int)


	//switchs places between a given mine and a cell that isen't a mine
	private void mineSwitch(){
		boolean found= false;
		for (int i=0; ((i < this.boardArr.length) && (!found)); i++)	//looks through the board for a cell without a mine in it
			for (int j=0; ((j < this.boardArr[0].length) && (!found)); j++)
				if ((!this.boardArr[i][j].getIsMine()) && (!((j == y) && (i == x)))){
					Cell temp= this.boardArr[i][j];
					this.boardArr[i][j]= this.boardArr[x][y];
					this.boardArr[x][y]= temp;
					found= true;
				}
	}//mineSwitch()


	//checks if this value is a valid cell in on the board
	private boolean sizeCheck(Cell[] arr, int value){
		return ((value < arr.length) && (value >= 0));
	}//sizeCheck(Cell[], int)


	//checks if this value is a valid cell in on the board
	private boolean sizeCheck(Cell[][] arr, int value){
		return ((value < arr.length) && (value >= 0));
	}//sizeCheck(Cell[][], int)


	//marks/unmarks the cell with a flag
	private void rightClick(){
		if((this.minesLabel.getMinesLeft() == 0) && (!this.boardArr[x][y].getIsMarked())){	//if there are too many flags on the game board
			JOptionPane.showMessageDialog(this.gameplay, "You have reached the maximum number of mines", "Can't place flag", JOptionPane.ERROR_MESSAGE);
		}	
		else {
			if (!this.boardArr[x][y].getIsPressed()){
				if (this.boardArr[x][y].getIsMarked()){	//if the cell isen't pressed and is marked
					this.boardArr[x][y].changeMark();	//unmarks this cell
					this.buttons[x][y].setIcon(null);
					this.minesLabel.mineLost();
				}
				else {	//if the cell isen't pressed and isen't marked
					this.boardArr[x][y].changeMark();	//marks this cell
					this.buttons[x][y].setIcon(Game.FLAG_ICON);
					this.minesLabel.mineFound();
				}

			}
		}
	}//rightClick()


	//simulates the bonus click
	private void bonusClick(){
		if ((this.boardArr[x][y].getIsPressed()) && (calcCellNum(x, y) > 0) && (calcCellNum(x, y) == calcFlagNum(x, y))){	//if this cell is a numbered cell (must be pressed) and has enough flags around it
			for (int i=-1; i <= 1; i++)
				for (int j=-1; j <= 1; j++){
					int newX= x+i;
					int newY= y+j;
					if ((sizeCheck(this.boardArr, newX)) && (sizeCheck(this.boardArr[newX], newY)) && (!((j == 0) && (i == 0))))	//for the 8 adjacent cells
						leftClick(newX, newY);	//opens those cells
				}
		}
	}//bonusClick()
	

	//counts the amount of mines around this cell
	private int calcCellNum(int x, int y){
		int ans= 0;
		for (int i=-1; i <= 1; i++)
			for (int j=-1; j <= 1; j++){
				int newX= x+i;
				int newY= y+j;
				if ((sizeCheck(this.boardArr, newX)) && (sizeCheck(this.boardArr[newX], newY)) && (!((j == 0) && (i == 0)))){	//if this is a valid cell
					if (this.boardArr[newX][newY].getIsMine()){	//if the cell is a mine
						ans++;
					}
				}
			}
		return ans;
	}//calcCellNum(int, int)
	
	
	//counts the amount of flags around this cell
	private int calcFlagNum(int x, int y){
		int ans= 0;
		for (int i=-1; i <= 1; i++)
			for (int j=-1; j <= 1; j++){
				int newX= x+i;
				int newY= y+j;
				if ((sizeCheck(this.boardArr, newX)) && (sizeCheck(this.boardArr[newX], newY)) && (!((j == 0) && (i == 0)))){	//if this is a valid cell
					if (this.boardArr[newX][newY].getIsMarked()){	//if flagged
						ans++;
					}
				}
			}
		return ans;
	}//calcFlagNum(int, int)


	//implements the MouseListener interface
	//simulates a click on one of the game tiles
	public void mouseReleased(MouseEvent e) {
		if (this.gameplay.isFirstMove())
			this.gameplay.startTimer();
		if (SwingUtilities.isLeftMouseButton(e)){
			leftClick(x, y);
			if (this.gameplay.isFirstMove())
				this.gameplay.setFirstMove(false);
			this.gameplay.isWon();
		}
		else if (SwingUtilities.isRightMouseButton(e))
			rightClick();
	}//mouseReleased(MouseEvent)
	

	//implements the MouseListener interface
	public void mouseClicked(MouseEvent e){}

	//implements the MouseListener interface
	public void mouseEntered(MouseEvent e) {}

	//implements the MouseListener interface
	public void mouseExited(MouseEvent e) {}

	//implements the MouseListener interface
	//simulates a bonus click on one of the game tiles
	public void mousePressed(MouseEvent e) {
		int bothMask = MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK;
	    if ((e.getModifiersEx() & bothMask) == bothMask){
	    	bonusClick();
			this.gameplay.isWon();
	    }	
	}//mousePressed(MouseEvent)


}//class ButtonPressed