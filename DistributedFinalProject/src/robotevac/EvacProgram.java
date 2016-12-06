package robotevac;

public class EvacProgram {
	private Robot 		robot1;
	private Robot 		robot2;
	private EvacCircle 	circle;
	private EvacView 	view;
	private RobotMode 	currRobotMode;
	private ExitMode 	currExitMode;

	public EvacProgram() {
		view = new EvacTextView();
	}

	private void initRobots(RobotMode r) {
		currRobotMode = r;
		switch (r) {
			case BOTH_CENTER:
				robot1 = new Robot(0, 0);
				robot2 = new Robot(0, 0);
				break;

			case ONE_RANDOM:
				robot1 = new Robot(0, 0);
				robot2 = new Robot(EvacCircle.randomPointInside());
				break;

			case BOTH_RANDOM:
				robot1 = new Robot(EvacCircle.randomPointInside());
				robot2 = new Robot(EvacCircle.randomPointInside());
				break;
		}
		view.initRobots(robot1, robot2);
	}

	private void initCircle(ExitMode e) {
		currExitMode = e;
		switch (e) {
			case RANDOM: {
				circle = new EvacCircle();
				break;
			}
			case WORST_CASE: {
				switch (currRobotMode) {
				case BOTH_CENTER:
					circle = new EvacCircle(new EvacPoint(Math.sin((2*Math.PI)*3),
							Math.cos(Math.sin((2*Math.PI)*3))));
					break;

				case ONE_RANDOM:
					// TODO find worst case
					circle = new EvacCircle(new EvacPoint(Math.sin((2*Math.PI)*3),
							Math.cos(Math.sin((2*Math.PI)*3))));
					break;

				case BOTH_RANDOM:
					// TODO find worst case
					circle = new EvacCircle(new EvacPoint(Math.sin((2*Math.PI)*3),
							Math.cos(Math.sin((2*Math.PI)*3))));
					break;
			}
				break;
			}
		}
		view.initCircle(circle);
	}

	private double runAlgorithm() {
		return 0;
	}

	public void run() {
		boolean exitMain = false;
		while (!exitMain) {
			currRobotMode = view.getRobotMode();
			if (currRobotMode == RobotMode.EXIT) {
				break;
			}
			boolean exitSub = false;
			while (!exitSub) {
				ExitMode exitMode = view.getExitMode(currRobotMode);
				switch (exitMode) {
					case RANDOM: {
						int num = view.getNumOfExperiements();
						double sum = 0;
						for (int i = 0; i < num; i++) {
							// TODO do we draw each experiment?
							initRobots(currRobotMode);
							initCircle(exitMode);
							sum += runAlgorithm();
						}
						view.showAvgTime(sum / num);
						break;
					}
					case WORST_CASE: {
						initRobots(currRobotMode);
						initCircle(exitMode);
						double time = runAlgorithm();
						view.showEvac();
						view.showTime(time);
						break;
					}
					case BACK: {
						exitSub = true;
						break;
					}
					case EXIT: {
						exitSub = true;
						exitMain = true;
						break;
					}
				}
				if (exitSub) {
					break;
				}
			}
		}

	}

	public static void main(String[] args) {
		EvacProgram p = new EvacProgram();
		p.run();

	}

}
