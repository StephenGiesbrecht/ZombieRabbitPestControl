package robotevac;

public class EvacProgram {
	public static enum RobotModes { BOTH_CENTER, ONE_RANDOM, BOTH_RANDOM, EXIT };
	public static enum ExitModes { RANDOM, WORST_CASE, BACK, EXIT };
	private Robot 		robot1;
	private Robot 		robot2;
	private EvacCircle 	circle;
	private EvacView 	view;
	private RobotModes 	currRobotMode;
	private ExitModes 	currExitMode;
	
	public EvacProgram() {
		// TODO initialize view here
	}
	
	private void initBots(RobotModes r) {
		currRobotMode = r;
		// TODO make sure random robots are within circle
		switch (r) {
			case BOTH_CENTER: {
				robot1 = new Robot(0, 0);
				robot2 = new Robot(0, 0);
			}
			case ONE_RANDOM: {
				robot1 = new Robot(0, 0);
				robot2 = new Robot(Math.random(), Math.random());
			}
			case BOTH_RANDOM: {
				robot1 = new Robot(Math.random(), Math.random());
				robot2 = new Robot(Math.random(), Math.random());
			}
		}
	}

	private void initCircle(ExitModes e) {
		currExitMode = e;
		switch (e) {
			case RANDOM: {
				circle = new EvacCircle();
			}
			case WORST_CASE: {
				// TODO make sure exit is on circumference and is worst case
				circle = new EvacCircle(Math.random(), Math.random());
			}
		}
	}

	private double runAlgorithm() {
		// TODO Auto-generated method stub
		return 0;
		
	}
	
	public void run() {
		boolean exitMain = false;
		while (!exitMain) {
			RobotModes robotMode = view.getRobotMode();
			if (robotMode != RobotModes.EXIT) {
				initBots(robotMode);
			}
			else {
				break;
			}
			boolean exitSub = false;
			while (!exitSub) {
				ExitModes exitMode = view.getExitMode();
				switch (exitMode) {
					case RANDOM: {
						int num = view.getNumOfExperiements();
						double sum = 0;
						for (int i = 0; i < num; i++) {
							// TODO do we draw each experiment?
							// TODO do we make new random robots each time?
							initCircle(exitMode);
							sum += runAlgorithm();
						}
						view.showAvgTime(sum / num);
					}
					case WORST_CASE: {
						initCircle(exitMode);
						double time = runAlgorithm();
						view.showEvac();
						view.showTime(time);
					}
					case BACK: {
						exitSub = true;
					}
					case EXIT: {
						exitSub = true;
						exitMain = true;
					}
				}
				if (exitSub) break;
			}
		}
		
	}

	public static void main(String[] args) {
		EvacProgram p = new EvacProgram();
		p.run();

	}

}
