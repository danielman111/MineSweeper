package GeneralFiles;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GeneralFiles.Buttons.LoadGame;
import GeneralFiles.Buttons.SaveGame;
import GeneralFiles.Labels.MinesLeftLabel;
import GeneralFiles.Labels.TimerLabel;
import GeneralFiles.Popups.*;

/*Name: Game
propose: This class represents a game
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class Game extends JFrame implements ActionListener, Serializable{

	/*Fields*/
	//serialVersionUID- the object's ID, for saving purposes
	private static final long serialVersionUID = -7044881940009266013L;
	//board- the game's board
	private Board board;
	//mines- the label showing how many mines are left
	private MinesLeftLabel mines;
	//gameTime- the label showing how much time has passed since the game started
	private TimerLabel gameTime;
	//scoresTable- holds the highscores
	private HighScores scoresTable;
	//difficultyButton- allows the user to change difficulty
	private JButton difficultyButton;
	//highscoresButton- allows the user to view the highscores
	private JButton highscoresButton;
	//isFirstMove- remembers if this is the first move of the game
	private boolean isFirstMove;
	//didGameEnd- remembers if the game ended
	private boolean didGameEnd;
	//N- the height of the game board
	private final int N;
	//M- the length of the game board
	private final int M;
	//minesInGame- the amount of mines on the game board
	private final int MINES_IN_GAME;
	//SAVE_GAME_FILE- the location of the game's save file
	private static final String SAVE_GAME_FILE= "lastgame.dat";
	//HIGHSCORES__FILE- the location of the game's highscore save file
	public static final String HIGHSCORES_FILE= "highscores.dat";
	//FLAG_ICON- a flag picture
	public static final ImageIcon FLAG_ICON= new ImageIcon(Game.class.getResource("/GeneralFiles/Pictures/flag.png"));
	//FALSE_FLAG_ICON- a false flag picture
	public static final ImageIcon FALSE_FLAG_ICON= new ImageIcon(Game.class.getResource("/GeneralFiles/Pictures/badflag.png"));
	//MINE_ICON- a mine picture
	public static final ImageIcon MINE_ICON= new ImageIcon(Game.class.getResource("/GeneralFiles/Pictures/mine.png"));
	//CLOCK_ICON- a clock picture
	public static final ImageIcon CLOCK_ICON= new ImageIcon(Game.class.getResource("/GeneralFiles/Pictures/clock.jpg"));

	
	/*Behavior*/
	/*Constructors*/
	//this constructor starts a new game
	public Game(int n, int m, int minesToAdd){
		super("Minesweeper");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.N= n;
		this.M= m;		
		this.MINES_IN_GAME= minesToAdd;
		try{	//trys loading the highscores
			File file = new File(Game.HIGHSCORES_FILE);
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			this.scoresTable= (HighScores)objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
		}	//if the highscores cannot be loaded, initializes them
		catch (Exception e){
			this.scoresTable= new HighScores();
		}

		getContentPane().setLayout(new BorderLayout());	//inits the game fields
		this.isFirstMove= true;
		this.didGameEnd= false;
		this.mines= new MinesLeftLabel(minesToAdd);
		JPanel minesLabel= new JPanel();	//the label that will show the number of mines left
		minesLabel.add(this.mines);
		minesLabel.add(new JLabel(Game.MINE_ICON));
		this.gameTime=  new TimerLabel();
		JPanel timer= new JPanel();	//the label that will show the time
		timer.add(new JLabel(Game.CLOCK_ICON));
		timer.add(this.gameTime);
		JPanel labels= new JPanel(new BorderLayout());	//the panel that will hold both previous panels
		labels.add(timer, BorderLayout.LINE_START);
		labels.add(minesLabel, BorderLayout.LINE_END);
		JPanel buttonsPanel= new JPanel(new FlowLayout());	//the panel that will hold all the game's buttons
		JButton saveButton = new SaveGame(this);
		JButton loadButton= new LoadGame(this);
		JButton newGameButton= new JButton("New game");
		this.difficultyButton= new JButton("Change difficulty");
		this.highscoresButton= new JButton("Highscores");
		this.difficultyButton.addActionListener(this);
		newGameButton.addActionListener(this);
		this.highscoresButton.addActionListener(this);
		buttonsPanel.add(this.difficultyButton);
		buttonsPanel.add(newGameButton);
		buttonsPanel.add(saveButton);
		buttonsPanel.add(loadButton);
		buttonsPanel.add(this.highscoresButton);
		this.board= new Board(this.N, this.M, minesToAdd, this.mines, this);	//generates a new board with the given values
		this.board.setMines();	//sets the mines on the new board
		this.getContentPane().add(labels, BorderLayout.NORTH);
		this.getContentPane().add(board, BorderLayout.CENTER);
		this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		Toolkit tk = Toolkit.getDefaultToolkit();  //sets the game's frame size
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());  
		int nm= Math.max(this.N, this.M);
		xSize= Math.min(nm*(480/9), xSize-50);
		ySize= Math.min(nm*(380/9), ySize-50);
		this.setSize(xSize,ySize);
		this.setResizable(false);
		this.setVisible(true);
	}//Game(int, int, int)


	//starts the game's timer
	protected void startTimer(){
		this.gameTime.timerStart();
	}//startTimer()

	
	//returns the current time
	protected String getTimerTime(){
		return this.gameTime.getTime();
	}//getTimerTime()


	//sets the timer's time
	protected void setTimerTime(int min, int sec){
		this.gameTime.setTime(min, sec);
	}//setTimerTime(int, int)
	
	
	//ends the game
	protected void mineHit(){
		this.board.mineHit();
		this.didGameEnd= true;
		this.gameTime.timerStop();
		GameLost restartGame= new GameLost(this);
	}//mineHit()


	//returns the height of this game's board
	protected int getN(){
		return this.N;
	}//getN

	
	//returns the length of this game's board
	protected int getM(){
		return this.M;
	}//getM


	//tells if the first move was yet to be made
	protected boolean isFirstMove(){
		return this.isFirstMove;
	}//isFirstMove


	//sets the state of the "isFirstMove" field
	protected void setFirstMove(boolean change){
		this.isFirstMove= change;
	}//setFirstMove(boolean)

	
	//tells if the game ended with the user's win
	protected void isWon(){
		if (this.board.isWon()){
			this.gameTime.timerStop();	//stops the timer
			String[] score= (this.gameTime.getTime()).split(":");	//generates a score from the timer
			for (int i=0; i < score[0].length(); i++)
				if (score[0].charAt(0) == '0')
					score[0]= score[0].substring(1);
			for (int i=0; i < score[1].length(); i++)
				if (score[1].charAt(0) == '0')
					score[1]= score[1].substring(1);
			int numberedMin= Integer.decode(score[0]);
			int numberedSec= Integer.decode(score[1]);
			InsertHighScore scoreBoard= new InsertHighScore(numberedSec + numberedMin*60, this.scoresTable);	//adds the score to the highscores table
			dispose();	//closes the game board
		}
	}//isWon()


	//grays-out a given tile on the game board
	protected void grayOutButton(int x, int y){
		this.board.grayOutButton(x, y);
	}//grayOutButton(int, int)


	//starts a new game with the same values
	public void replayGame(){
		Game play= new Game(this.N, this.M, this.MINES_IN_GAME);
		dispose();	//closes the "old" game
	}//replayGame()


	//saves the current game
	public void saveGame(){
		if (!this.didGameEnd){	//user can only save if the game has not ended
			try {	//saves the game to file
				FileOutputStream fileOutputStream = new FileOutputStream(Game.SAVE_GAME_FILE);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(this);
				objectOutputStream.close();
				fileOutputStream.close();
				JOptionPane.showMessageDialog(this, "Game saved");
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(this, "error while saving the game", "", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			JOptionPane.showMessageDialog(this, "You can not save a game that has ended", "Error while saving game", JOptionPane.ERROR_MESSAGE);
	}//saveGame()


	//loads the game from file
	public void loadGame(){
		try{	//loads the game from file
			File file = new File(Game.SAVE_GAME_FILE);
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Game lastGame= (Game)objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
			lastGame.startTimer();
			lastGame.setVisible(true);
			dispose();
		}	//if the file is not found or is corrupted, informs the user
		catch (Exception e){
			JOptionPane.showMessageDialog(this, "Cannot find save file or save file is corrupted", "Error while loading game", JOptionPane.ERROR_MESSAGE);
		}
	}//loadGame()


	//implements the ActionListener interface
	//acts by the button that was pressed
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.difficultyButton){	//opens the start menu
			StartMenu newGame;
			newGame= new StartMenu(this);
			dispose();
		}
		else if (event.getSource() == this.highscoresButton)	//shows the highscores table
			this.scoresTable.showScores(false);
		else {	//replays the game with the same values
			replayGame();
		}
	}//actionPerformed(ActionEvent)


}//class Game
