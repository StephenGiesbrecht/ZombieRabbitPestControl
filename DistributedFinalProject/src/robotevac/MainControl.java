package robotevac;

import display.GUIView;

public class MainControl {
	GUIView view = new GUIView();
	SimulationSettings settings;
	private Robot 		robot1;
	private Robot 		robot2;
	private EvacCircle 	circle;
	
	private void initRobots() {
		switch (settings.getRobotMode()) {
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
	}

	private void initCircle() {
		switch (settings.getExitMode()) {
			case RANDOM: {
				circle = new EvacCircle();
				break;
			}
			case WORST_CASE: {
				switch (settings.getRobotMode()) {
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
	}

	private double runAlgorithm() {
		while (!robot1.atExit() && !robot2.atExit()) {
			EvacPoint prev1 = robot1.getLocation();
			EvacPoint prev2 = robot2.getLocation();
			robot1.move();
			robot2.move();
		}
		
		
		
		return Math.max(robot1.getDistance(), robot2.getDistance());
	}
	
	public void run() {
		while (true) {
			settings = new SimulationSettings();
			settings = view.getSimulationSettings();
			switch (settings.getExitMode()) {
			case RANDOM:
				// TODO get number of experiments
				int num = 20;
				double sum = 0;
				for (int i = 0; i < num; i++) {
					// TODO do we draw each experiment?
					initRobots();
					initCircle();
					sum += runAlgorithm();
				}
				// TODO how does view show time?
				break;
				case WORST_CASE:
					initRobots();
					initCircle();
					double time = runAlgorithm();
					// TODO how does view show time and experiment?
					break;
			}
		}

	}

	public static void main(String args[]) {
		MainControl control = new MainControl();
		control.run();
	}
}
