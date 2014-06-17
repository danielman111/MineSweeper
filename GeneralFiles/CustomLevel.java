package GeneralFiles;
import static java.awt.GridBagConstraints.EAST;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.WEST;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/*Name: CustomLevel
propose: This class represents the selection part for the "custom" difficulty
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class CustomLevel extends JPanel implements ActionListener{

	/*Fields*/
	//nSize- holds the height of the new game's board
	private JSpinner nSize;
	//mSize- holds the length of the new game's board
	private JSpinner mSize;
	//mineCount- holds the number of mines on the new game's board
	private JSpinner minesCount;


	/*Behavior*/
	/*Constructors*/
	//creates a new panel with these spinners
	public CustomLevel(JSpinner nSize, JSpinner mSize, JSpinner minesCount){
		super(new GridBagLayout());
		this.nSize= nSize;
		this.mSize= mSize;
		this.minesCount= minesCount;

		GridBagConstraints tProto = new GridBagConstraints();
		tProto.insets = new Insets(5,2,5,2);	//sets all components on this panel

		GridBagConstraints tLabelConst = (GridBagConstraints)tProto.clone();
		tLabelConst.anchor = EAST;
		tLabelConst.fill = NONE;
		tLabelConst.weightx = 0.0;

		GridBagConstraints tTextConst = (GridBagConstraints)tProto.clone();
		tTextConst.anchor = WEST;
		tTextConst.fill = HORIZONTAL;
		tTextConst.weightx = 1.0;

		GridBagConstraints tSelConst = (GridBagConstraints)tProto.clone();
		tSelConst.anchor = WEST;
		tSelConst.fill = NONE;
		tSelConst.weightx = 0.0;

		GridBagConstraints tConst = (GridBagConstraints)tLabelConst.clone();
		tConst.gridx = 0; tConst.gridy = 0;
		add(new JLabel("Heigth (9-24)"), tConst);

		tConst = (GridBagConstraints)tTextConst.clone();
		tConst.gridx = 1; tConst.gridy = 0;
		add(this.nSize, tConst);

		tConst = (GridBagConstraints)tLabelConst.clone();
		tConst.gridx = 0; tConst.gridy = 1;
		add(new JLabel("Width (9-30)"), tConst);

		tConst = (GridBagConstraints)tTextConst.clone();
		tConst.gridx = 1; tConst.gridy = 1;
		add(mSize, tConst);

		tConst = (GridBagConstraints)tLabelConst.clone();
		tConst.gridx = 0; tConst.gridy = 2;
		add(new JLabel("Mines (10-668)"), tConst);

		tConst = (GridBagConstraints)tTextConst.clone();
		tConst.gridx = 1; tConst.gridy = 2;
		add(this.minesCount, tConst);
	}//CustomLevel(JSpinner, JSpinner, JSpinner)


	//implements the ActionListener interface
	//if the "custom" difficulty was selected, allows use of the spinners
	public void actionPerformed(ActionEvent event) {
		this.nSize.setEnabled(true);
		this.mSize.setEnabled(true);
		this.minesCount.setEnabled(true);
	}//actionPerformed(ActionEvent)

	
}//class CustomLevel