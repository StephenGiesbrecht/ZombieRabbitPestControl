package robotevac;

import display.GUIView;

public class MainControl {
	private GUIView view = new GUIView();
	private SimulationSettings settings;
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
		case RANDOM:
			circle = new EvacCircle();
			break;
		case WORST_CASE:
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

	private double runAlgorithm() {
		switch (settings.getRobotMode()) {
		case BOTH_CENTER:
			robot1.setDestination(new EvacPoint(0,1));
			robot2.setDestination(new EvacPoint(0,1));
			break;
		case ONE_RANDOM:
			double angle = Math.atan(robot2.getLocation().getX() / robot2.getLocation().getY());
			robot1.setDestination(new EvacPoint(Math.sin(angle), Math.cos(angle)));
			robot2.setDestination(new EvacPoint(Math.sin(angle), Math.cos(angle)));
			break;
		case BOTH_RANDOM:
			// TODO figure out destination
			break;
		}
		while (!robot1.atExit() && !robot2.atExit()) {
			EvacPoint prevLoc1 = robot1.getLocation();
			EvacPoint prevLoc2 = robot2.getLocation();
			EvacPoint prevDest1 = robot1.getDestination();
			EvacPoint prevDest2 = robot2.getDestination();
			MoveMode prevMode1 = robot1.getMode();
			MoveMode prevMode2 = robot2.getMode();
			if (!robot1.atExit()) robot1.move();
			if (!robot2.atExit()) robot2.move();
			if (prevMode1.equals(MoveMode.CIRCUMFERENCE)
					&& robot1.getMode().equals(MoveMode.ROTATE)) {
				if (circle.exitBetween(prevDest1, robot1.getLocation())) {
					double d = Math.abs(Math.atan(circle.getExit().getX()
							/ circle.getExit().getY())
							- Math.atan(robot1.getLocation().getX()
									/ robot1.getLocation().getY()));
					robot1.reverse(d);
					robot1.exit();
					robot2.reverse(d);
					robot2.setDestination(circle.getExit());
					robot2.setMode(MoveMode.EXIT);
					robot2.move(d);
				}
			}
			else if (prevMode2.equals(MoveMode.CIRCUMFERENCE)
					&& robot2.getMode().equals(MoveMode.ROTATE)) {
				if (circle.exitBetween(prevDest2, robot1.getLocation())) {
					double d = Math.abs(Math.atan(circle.getExit().getX()
							/ circle.getExit().getY())
							- Math.atan(robot2.getLocation().getX()
									/ robot2.getLocation().getY()));
					robot2.reverse(d);
					robot2.exit();
					robot1.reverse(d);
					robot1.setDestination(circle.getExit());
					robot1.setMode(MoveMode.EXIT);
					robot1.move(d);
				}
			}
			if (robot1.getMode().equals(MoveMode.ROTATE)
					&& circle.exitBetween(prevLoc1, robot1.getLocation())) {
				double d = Math.abs(Math.atan(circle.getExit().getX()
						/ circle.getExit().getY())
						- Math.atan(robot1.getLocation().getX()
								/ robot1.getLocation().getY()));
				robot1.reverse(d);
				robot1.exit();
				robot2.reverse(d);
				robot2.setDestination(circle.getExit());
				robot2.setMode(MoveMode.EXIT);
				robot2.move(d);
			}
			else if (robot2.getMode().equals(MoveMode.ROTATE)
					&& circle.exitBetween(prevLoc2, robot2.getLocation())) {
				double d = Math.abs(Math.atan(circle.getExit().getX()
						/ circle.getExit().getY())
						- Math.atan(robot2.getLocation().getX()
								/ robot2.getLocation().getY()));
				robot2.reverse(d);
				robot2.exit();
				robot1.reverse(d);
				robot1.setDestination(circle.getExit());
				robot1.setMode(MoveMode.EXIT);
				robot1.move(d);
			}
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
