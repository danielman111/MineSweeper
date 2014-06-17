package GeneralFiles;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.Serializable;
import javax.swing.JFrame;

import GeneralFiles.Popups.*;

/*Name: FocusListener
propose: This class represents window listener
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class FocusListener implements WindowFocusListener, Serializable{
	
	/*Fields*/
	//serialVersionUID- the object's ID, for saving purposes
	private static final long serialVersionUID = 5720908210277268929L;
	//parent- the object that this class listens to
	private JFrame parent;
	
	
	/*Behavior*/
	/*Constructors*/
	//creates a new window listener
	public FocusListener(JFrame dad){
		this.parent= dad;
	}//FocusListener
	
	
	//implements the WindowFocusListener interface
	public void windowGainedFocus(WindowEvent we){}

	
	//implements the WindowFocusListener interface
	//when the frame losses focus, closes it
	public void windowLostFocus(WindowEvent we){
		if (this.parent instanceof HighScores)
			((HighScores)this.parent).closeThis();
		else if (this.parent instanceof GameLost)
			((GameLost)this.parent).closeThis();
	}//windowLostFocus
	
	
}//class FocusListener