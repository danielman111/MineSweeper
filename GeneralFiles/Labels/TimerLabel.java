package GeneralFiles.Labels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/*Name: TimerLabel
propose: This class represents the timer that shows the game time
author: Gal Luvton and Daniel Sinaniev
Date Created: 27/5/2013
Last modification: 09/6/2013
*/

public class TimerLabel extends JLabel implements ActionListener{

	/*Fields*/
	//sec- holds the seconds
	private int sec;
	//min- holds the minutes
	private int min;
	//t- a Timer object, for updating this label
	private Timer t;

	/*Behavior*/
	/*Constructors*/
	//creates a new timer label with the time 00:00
	public TimerLabel(){
		super("00:00");
		this.sec= 0;
		this.min= 0;
		this.t= new Timer(1000, this);
	}//TimerLabel()


	//returns the current time
	public String getTime(){
		return String.format("%02d", this.min) + ":" + String.format("%02d", this.sec);
	}//getTimer()


	//sets this label's time with the given arguments
	public void setTime(int min, int sec){
		this.min= min;
		this.sec= sec;
		this.setText(String.format("%02d", this.min) + ":" + String.format("%02d", this.sec));
	}//setTime(int, int)

	
	//starts the timer
	public void timerStart(){
		this.t.start();
	}// timerStart()


	//stops the timer
	public void timerStop(){
		this.t.stop();
	}//timerStop()

	
	//implements the ActionListener interface
	//updates the text on the label to match the time
	public void actionPerformed(ActionEvent e) {
		if ((this.sec + this.min*60) <= 999){	//will stop counting after 999 seconds
			this.sec++;
			if (this.sec == 60){
				this.sec= 0;
				this.min++;
			}
		}
		this.setText(String.format("%02d", this.min) + ":" + String.format("%02d", this.sec));
	}//actionPerformed(ActionEvent)


}//class TimerLabel
