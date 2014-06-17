package GeneralFiles;
import java.io.Serializable;

/*Name: Cell
propose: This class represents a game tile
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class Cell implements Serializable{

	/*Fields*/
	//serialVersionUID- the object's ID, for saving purposes
	private static final long serialVersionUID = -8920112836633372987L;
	//isPressed- tells if the given tile has been opened already
	private boolean isPressed;
	//isMine- tells if the given tile is a mine
	private boolean isMine;
	//isMarked- tells if the given tile has been marked with a flag
	private boolean isMarked;

	
	/*Behavior*/
	/*Constructors*/
	//creates a new cell
	public Cell(boolean isMine){
		this.isMine= isMine;
		this.isPressed= false;
		this.isMarked= false;
	}//Cell(boolean)


	//changes the isMarked field
	protected void changeMark(){
		this.isMarked= !this.isMarked;
	}//changeMark()


	//tells if this cell is marked
	protected boolean getIsMarked(){
		return this.isMarked;
	}//getIsMarked


	//tells if this cell has been pressed
	protected boolean getIsPressed(){
		return this.isPressed;
	}//getIsPressed


	//changes the state of the isPressed field
	protected void setPressed(boolean change){
		this.isPressed= change;
	}//setPressed(boolean)


	//changes the state of the isMine field to True
	protected void setAsMine(){
		this.isMine= true;
	}//setAsMine()


	//tells if this cell is a mine
	protected boolean getIsMine(){
		return this.isMine;
	}//getIsMine()


	//presses this tile, and tells if it was a mine
	protected boolean press(){
		this.setPressed(true);
		return this.isMine;
	}//press()


}//class Cell
