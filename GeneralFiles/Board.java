package GeneralFiles;
import java.awt.GridLayout;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JPanel;

import GeneralFiles.Labels.MinesLeftLabel;


/*Name: Game
propose: This class represents a game
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class Board extends JPanel{

	/*Fields*/
	//boardArr- the array of cells- for background computing
	private Cell[][] boardArr;
	//buttons- the array of cells- for visual presentation
	private JButton[][] buttons;
	//events- an array of listeners, each attacked to a game tile
	private ButtonPressed[][] events;
	//minesLabel- a link to the 'mines left' label
	private MinesLeftLabel minesLabel;
	//gameplay- a link to the game
	private Game gameplay;
	//N- the height of the game board
	private final int N;
	//M- the length of the game board
	private final int M;
	//NUM_OF_MINES- the amount of mines this game has
	private final int NUM_OF_MINES;

	
	/*Behavior*/
	/*Constructors*/
	//initializes a new game board
	public Board(int n, int m, int mines, MinesLeftLabel minesLabel, Game gameplay){
		super(new GridLayout(n,m));
		this.N= n;
		this.M= m;
		JButton b;
		this.gameplay= gameplay;
		this.minesLabel= minesLabel;
		this.NUM_OF_MINES= mines;
		this.boardArr= new Cell[this.N][this.M];
		this.buttons= new JButton[this.N][this.M];
		this.events= new ButtonPressed[this.N][this.M];
		for (int i=0; i < this.N; i++)
			for (int j=0; j < this.M; j++){	//for each game tile, sets it as an empty tile
				this.boardArr[i][j]= new Cell(false);
				b= new JButton("");
				this.events[i][j]= new ButtonPressed(i, j, this.boardArr, this.buttons, this.minesLabel, this.gameplay);
				b.addMouseListener(this.events[i][j]);
				add(b);
				this.buttons[i][j]= b;
			}
	}//Board(int, int, int, MinesLeftLabel, Game)


	//sets mines on the game board
	protected void setMines(){
		int minesLeft= this.NUM_OF_MINES;
		for (int i=0; ((i < this.N) && (minesLeft > 0)); i++)
			for (int j=0; ((j < this.M) && (minesLeft > 0)); j++){	//sets all the mines at the begining of the game board
				this.boardArr[i][j].setAsMine();
				minesLeft--;
			}
		for (int i=0; i < this.N; i++)
			for (int j=0; j < this.M; j++){	//for each cell- replace this cell with a random cell
				int newX= (int)(Math.random()*this.N);
				int newY= (int)(Math.random()*this.M);
				Cell temp= this.boardArr[i][j];
				this.boardArr[i][j]= this.boardArr[newX][newY];
				this.boardArr[newX][newY]= temp;
			}
	}//setMines()


	//simulates a state where a mine was hit
	protected void mineHit(){
		for (int i=0; i < this.N; i++)
			for (int j=0; j < this.M; j++){	//for each game tile
				this.boardArr[i][j].press();
				if (this.boardArr[i][j].getIsMine()){	//if this tile is a mine- change its appearance to a mine
					this.buttons[i][j].setIcon(Game.MINE_ICON);
					this.buttons[i][j].setText("");
				}
				if ((this.boardArr[i][j].getIsMarked()) && (!this.boardArr[i][j].getIsMine())){	//if this tile isesn't a mine but is flagged- change its appearance to a false flag
					this.buttons[i][j].setIcon(Game.FALSE_FLAG_ICON);
					this.buttons[i][j].setText("");
				}
			}
	}//mineHit()


	//visually grays-out this button
	protected void grayOutButton(int x, int y){
		this.buttons[x][y].setText("");
		this.buttons[x][y].setContentAreaFilled(false);
		this.buttons[x][y].setOpaque(false);
		this.buttons[x][y].setRolloverEnabled(false);
		this.buttons[x][y].setPressedIcon(null);
	}//grayOutButton(int, int)


	//checks if the user won the game
	protected boolean isWon(){
		boolean won= false;
		int sumpressed= 0;
		for (int i=0; i < this.N; i++)
			for (int j=0; j < this.M; j++)	//for each game tile
				if ((this.boardArr[i][j]).getIsPressed())
					sumpressed++;	//sums the number of tiles pressed
		if (sumpressed == (this.N*this.M-NUM_OF_MINES))	//if the number of pressed tiles is the number off all tiles minus the number of mines
			won= true;	//the user won
		return won;	
	}//isWon()


}//class Board
