package robotevac;

import robotevac.EvacProgram.ExitModes;
import robotevac.EvacProgram.RobotModes;

public abstract class EvacView {
	private Robot 		r1;
	private Robot 		r2;
	private EvacCircle 	circle;
	
	public EvacView(Robot one, Robot two, EvacCircle c) {
		r1 = one;
		r2 = two;
		circle = c;
	}
	
	//should ask the user which of the three problems they wish to tackle, as well as give 
	//the option to exit
	//returns a EvacProgram RobotModes enum declaring which of the three options they
	//want to do, or declaring that they want to exit
	public abstract RobotModes getRobotMode();

	//should ask the user whether they want to get the average time or the worst case
	//for the given robot set-up, or whether they want to go back to the previous choice,
	//or whether they want to exit the program entirely
	//returns a EvacProgram ExitModes enum declaring whether they want average time, worst case,
	//back to previous menu or exit
	public abstract ExitModes getExitMode();
	
	//should display the robots moving to the exit according to the algorithm being run
	public abstract void showEvac();
	
	//should display the time taken by the worst case algorithm
	public abstract void showTime(double time);

	//should ask for and return the number of times to run the experiments to get the average
	public abstract int getNumOfExperiements();
	
	//should display the average time for all experiments once they are finished
	public abstract void showAvgTime(double avgTime);
}
