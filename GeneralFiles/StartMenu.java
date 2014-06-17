package GeneralFiles;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/*Name: StartMenu
propose: This class represents the start manu. This class holds the Main function
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class StartMenu extends JFrame implements ActionListener{

	/*Fields*/
	//nSize- a spinner that holds the height of the board on "custom" difficulty 
	private JSpinner nSize;
	//mSize- a spinner that holds the length of the board on "custom" difficulty
	private JSpinner mSize;
	//minesCount- a spinner that holds the number of mines on the board on "custom" difficulty
	private JSpinner minesCount;
	//lastGame- holds a link to the last game, in order to close it once a new game begins
	private Game lastGame;
	//OK- confirmation button
	private final JButton OK= new JButton("ok");
	//BEGGINER_BUTTON- selects the "begginer" difficulty- 9*9 board with 10 mines
	private final JRadioButton BEGGINER_BUTTON= new JRadioButton("Begginer");
	//INTERMEDIATE_BUTTON- selects the "intermediate" difficulty- 16*16 board with 40 mines
	private final JRadioButton INTERMEDIATE_BUTTON= new JRadioButton("Intermediate");
	//EXPERT_BUTTON- selects the "expert" difficulty- 16*30 board with 99 mines
	private final JRadioButton EXPERT_BUTTON= new JRadioButton("Expert");
	//CUSTOM_BUTTON- allows the user to choose his own values
	private final JRadioButton CUSTOM_BUTTON= new JRadioButton("Custom");
	

	/*Behavior*/
	/*Constructors*/
	//this constructor creates this menu
	public StartMenu(Game lastGame){
		super("Select difficulty");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.lastGame= lastGame;

		JPanel cont= new JPanel(new BorderLayout());
		this.BEGGINER_BUTTON.setSelected(true);	//sets the "BEGGINER_BUTTON" as the default choice 

		ButtonGroup group = new ButtonGroup();	//groups the spinners together
		group.add(this.BEGGINER_BUTTON);
		group.add(this.INTERMEDIATE_BUTTON);
		group.add(this.EXPERT_BUTTON);
		group.add(this.CUSTOM_BUTTON);
		
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));	//groups all the radio buttons together
		radioPanel.add(this.BEGGINER_BUTTON);
		radioPanel.add(this.INTERMEDIATE_BUTTON);
		radioPanel.add(this.EXPERT_BUTTON);
		radioPanel.add(this.CUSTOM_BUTTON);
		
		this.nSize= new JSpinner(new SpinnerNumberModel(9, 9, 24, 1));	//sets the spinner's default values and margins
		this.mSize= new JSpinner(new SpinnerNumberModel(9, 9, 30, 1));
		this.minesCount= new JSpinner(new SpinnerNumberModel(10, 10, 668, 1));
		this.nSize.setEnabled(false);
		this.mSize.setEnabled(false);
		this.minesCount.setEnabled(false);

		CustomLevel customTextArea= new CustomLevel(this.nSize, this.mSize, this.minesCount);	//creates a new panel to keep the spinners grayed-out when needed
		this.OK.addActionListener(this);

		this.BEGGINER_BUTTON.addActionListener(this);	//sets the listeners to the radio buttons
		this.INTERMEDIATE_BUTTON.addActionListener(this);
		this.EXPERT_BUTTON.addActionListener(this);
		this.CUSTOM_BUTTON.addActionListener(customTextArea);

		cont.add(radioPanel, BorderLayout.PAGE_START);	//adds the buttons to the frame
		cont.add(customTextArea, BorderLayout.CENTER);
		cont.add(this.OK, BorderLayout.PAGE_END);
		cont.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		add(cont);

		Dimension size = getToolkit().getScreenSize();	//sets the size of the menu and sets it to open mid-screen
		setLocation(size.width/2-getWidth()/2 - 150, size.height/2 - getHeight()/2 - 150);
		setSize(200, 300);
		setResizable(false);
		setVisible(true);
	}//StartMenu(Game)


	//implements the ActionListener interface
	//starts the game when the "ok" button is pressed
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.OK){
			Game play;
			if (this.BEGGINER_BUTTON.isSelected()){	//starts the game on "begginer" mode
				play= new Game(9, 9, 10);
				if (this.lastGame != null)
					this.lastGame.dispose();
				dispose();
			}
			else if (this.INTERMEDIATE_BUTTON.isSelected()){	//starts the game on "intermediate" mode
				play= new Game(16, 16, 40);
				if (this.lastGame != null)
					this.lastGame.dispose();
				dispose();
			}
			else if (this.EXPERT_BUTTON.isSelected()){	//starts the game on "expert" mode
				play= new Game(16, 30, 99);
				if (this.lastGame != null)
					this.lastGame.dispose();
				dispose();
			}
			else{	//starts the game on "custom" mode
				int n= (Integer)(this.nSize.getValue());
				int m= (Integer)(this.mSize.getValue());
				int mines= (Integer)(this.minesCount.getValue());
				if (checkValues(n, m, mines)){	//checks the values entered are valid
					play= new Game(n, m, mines);	//starts a new game
					if (this.lastGame != null)
						this.lastGame.dispose();	//closes the last game
					dispose();	//closes this frame
				}
			}
		}
		else {	//if the action did not come from the "ok" button- it came from the radio buttons
			this.nSize.setEnabled(false);
			this.mSize.setEnabled(false);
			this.minesCount.setEnabled(false);
		}
	}//actionPerformed(ActionEvent)


	//checks the values entered are valid, also checks that there are less mines than board tiles
	private boolean checkValues(int n, int m, int mines){
		boolean ans= ((n >= 9) && (n <= 24));
		ans= ans && ((m >= 9) && (m <= 30));
		ans= ans && ((mines >= 10) && (mines <= 668) && (mines < n*m));
		if (!ans)	//if the values entered are invalid, brings a message to screen to alert the user
			JOptionPane.showMessageDialog(this, "Please enter valid values", "Incorrect values entered", JOptionPane.ERROR_MESSAGE);
		return ans;
	}//checkValues(int, int, int)


	/**
	 * the this function runs the StartMenu for the first time
	 */
	public static void main(String[] args) {
		StartMenu frame= new StartMenu(null);
	}


}//class StartMenu