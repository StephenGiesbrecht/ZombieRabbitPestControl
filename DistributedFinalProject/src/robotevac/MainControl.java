package robotevac;

import static robotevac.EvacPoint.EPSILON;

import display.GUIView;

public class MainControl {
	private GUIView view = new GUIView();
	private SimulationSettings settings;
	private Robot 		robot1;
	private Robot 		robot2;
	private EvacCircle 	circle;

	//initialize the robots based on robot mode chosen by user
	//robot1 always goes clockwise while robot2 always goes counter-clockwise
	private void initRobots() {
		switch (settings.getRobotMode()) {
		//both start in the center
		case BOTH_CENTER:
			robot1 = new Robot(0, 0, Direction.CW);
			robot2 = new Robot(0, 0, Direction.CCW);
			break;
		//robot1 starts in the center, robot2 starts at a random spot in the circle
		case ONE_RANDOM:
			robot1 = new Robot(0, 0, Direction.CW);
			robot2 = new Robot(EvacCircle.randomPointInside(), Direction.CCW);
			break;
		//both robots start at random spots in the circle
		case BOTH_RANDOM:
			robot1 = new Robot(EvacCircle.randomPointInside(), Direction.CW);
			robot2 = new Robot(EvacCircle.randomPointInside(), Direction.CCW);
			break;
		}
	}

	//initialize the circle based on the user's choice for exit
	private void initCircle() {
		switch (settings.getExitMode()) {
		//default constructor will give a circle with a random exit, so use that
		case RANDOM:
			circle = new EvacCircle();
			break;
		//worst case changes based on where robots start
		case WORST_CASE:
			switch (settings.getRobotMode()) {
			//if both start at center worst case is fixed
			case BOTH_CENTER:
				circle = new EvacCircle(new EvacPoint(Math.sin((2*Math.PI)/3),
						Math.cos((2*Math.PI)/3)));
				break;
			//if one robot is random need to find worst case
			case ONE_RANDOM:
				double x = robot2.getLocation().getX();
				double y = robot2.getLocation().getY();
				double d = Math.sqrt(x * x + y * y);
				double angle1 = Math.acos(-0.5) - (d / 2);
				double angle2 = Math.acos(-0.5) + (d / 2);
				double total1 = 1 + angle1 + 2 * Math.sin(angle1 + (d / 2));
				double total2 = 1 + angle1 + 2 * Math.sin(angle1 - (d / 2));
				if (total1 > total2) {
					circle = new EvacCircle(new EvacPoint(Math.sin(angle1),
						Math.cos(angle1)));
					System.out.println("Exit x: " + Math.sin(angle1));
					System.out.println("Exit y: " + Math.cos(angle1));
					System.out.println("Worst case time: " + total1);
				}
				else {
					circle = new EvacCircle(new EvacPoint(Math.sin(2 * Math.PI - angle2),
							Math.cos(2 * Math.PI - angle2)));
					System.out.println("Exit x: " + Math.sin(angle2));
					System.out.println("Exit y: " + Math.cos(angle2));
					System.out.println("Worst case time: " + total2);
				}
				break;
			//if both robots are random also need to find worst case
			case BOTH_RANDOM:
				// TODO find worst case
				circle = new EvacCircle(new EvacPoint(Math.sin((2*Math.PI)/3),
						Math.cos(Math.sin((2*Math.PI)/3))));
				break;
			}
			break;
		}
	}

	//the main loop that makes the robots move along the path to the exit
	private double runAlgorithm() {
		//figure out where the robots need to go to on the circumference
		switch (settings.getRobotMode()) {
		case BOTH_CENTER:
			//if they're both in the center any point will do
			robot1.setDestination(new EvacPoint(0,1));
			robot2.setDestination(new EvacPoint(0,1));
			break;
		case ONE_RANDOM:
			//if one is random must find the point on the circumference along the line
			//that the two robots are on
			double angle = EvacCircle.getAngle(robot2.getLocation().getX(), robot2.getLocation().getY());
			robot1.setDestination(new EvacPoint(Math.sin(angle), Math.cos(angle)));
			robot2.setDestination(new EvacPoint(Math.sin(angle), Math.cos(angle)));
			break;
		case BOTH_RANDOM:
			//if both are random must find the point on the circumference such that the maximum
			//of the two robots' distances to said point is minimized
			// TODO figure out destination
			break;
		}
		//keep moving the robots until both have exited
		while (!robot1.atExit() || !robot2.atExit()) {
			EvacPoint prevLoc1 = robot1.getLocation();
			EvacPoint prevLoc2 = robot2.getLocation();
			EvacPoint prevDest1 = robot1.getDestination();
			EvacPoint prevDest2 = robot2.getDestination();
			MoveMode prevMode1 = robot1.getMode();
			MoveMode prevMode2 = robot2.getMode();
			//move the robots
			if (!robot1.atExit()) robot1.move();
			if (!robot2.atExit()) robot2.move();
			//check if the first robot hit the exit while transitioning between moving to the
			//circumference and rotating around the circle
			if (prevMode1.equals(MoveMode.CIRCUMFERENCE)
					&& robot1.getMode().equals(MoveMode.ROTATE)) {
				if (circle.exitBetween(
						prevDest1, robot1.getLocation(), robot1.getDirection())) {
					//find the distance that the first robot overshot the exit by
					double angle1 = EvacCircle.getAngle(circle.getExit().getX(),
							circle.getExit().getY());
					double angle2 = EvacCircle.getAngle(robot1.getLocation().getX(),
							robot1.getLocation().getY());
					double d = Math.abs(angle1 - angle2);
					//reverse the first robot by the overshoot distance and set it to exited
					robot1.reverse(d);
					robot1.exit();
					//also set it to exit mode so it doesn't trigger any of these cases
					robot1.setMode(MoveMode.EXIT);
					//reverse the second robot by the overshoot distance, set it to exit mode,
					//give it the exit as a destination and move it towards the exit by the
					//overshoot distance
					robot2.reverse(d);
					robot2.setDestination(circle.getExit());
					robot2.setMode(MoveMode.EXIT);
					robot2.moveStraight(d);
				}
			}
			//check if the second robot hit the exit while transitioning between moving to the
			//circumference and rotating around the circle
			else if (prevMode2.equals(MoveMode.CIRCUMFERENCE)
					&& robot2.getMode().equals(MoveMode.ROTATE)) {
				if (circle.exitBetween(
						prevDest2, robot2.getLocation(), robot2.getDirection())) {
					//find the distance that the second robot overshot the exit by
					double angle1 = EvacCircle.getAngle(circle.getExit().getX(),
							circle.getExit().getY());
					double angle2 = EvacCircle.getAngle(robot2.getLocation().getX(),
							robot2.getLocation().getY());
					double d = Math.abs(angle1 - angle2);
					//reverse the second robot by the overshoot distance and set it to exited
					robot2.reverse(d);
					robot2.exit();
					//also set it to exit mode so it doesn't trigger any of these cases
					robot2.setMode(MoveMode.EXIT);
					//reverse the first robot by the overshoot distance, set it to exit mode,
					//give it the exit as a destination and move it towards the exit by the
					//overshoot distance
					robot1.reverse(d);
					robot1.setDestination(circle.getExit());
					robot1.setMode(MoveMode.EXIT);
					robot1.moveStraight(d);
				}
			}
			//check if the first robot hit the exit while rotating around the circle
			if (robot1.getMode().equals(MoveMode.ROTATE)
					&& circle.exitBetween(
							prevLoc1, robot1.getLocation(), robot1.getDirection())) {
				//find the distance that the first robot overshot the exit by
				double angle1 = EvacCircle.getAngle(circle.getExit().getX(),
						circle.getExit().getY());
				double angle2 = EvacCircle.getAngle(robot1.getLocation().getX(),
						robot1.getLocation().getY());
				double d = Math.abs(angle1 - angle2);
				//reverse the first robot by the overshoot distance and set it to exited
				robot1.reverse(d);
				robot1.exit();
				//also set it to exit mode so it doesn't trigger any of these cases
				robot1.setMode(MoveMode.EXIT);
				//reverse the second robot by the overshoot distance, set it to exit mode,
				//give it the exit as a destination and move it towards the exit by the
				//overshoot distance
				robot2.reverse(d);
				robot2.setDestination(circle.getExit());
				robot2.setMode(MoveMode.EXIT);
				robot2.moveStraight(d);
			}
			//check if the second robot hit the exit while rotating around the circle
			else if (robot2.getMode().equals(MoveMode.ROTATE)
					&& circle.exitBetween(
							prevLoc2, robot2.getLocation(), robot2.getDirection())) {
				//find the distance that the first robot overshot the exit by
				double angle1 = EvacCircle.getAngle(circle.getExit().getX(),
						circle.getExit().getY());
				double angle2 = EvacCircle.getAngle(robot2.getLocation().getX(),
						robot2.getLocation().getY());
				double d = Math.abs(angle1 - angle2);
				//reverse the second robot by the overshoot distance and set it to exited
				robot2.reverse(d);
				robot2.exit();
				//also set it to exit mode so it doesn't trigger any of these cases
				robot2.setMode(MoveMode.EXIT);
				//reverse the first robot by the overshoot distance, set it to exit mode,
				//give it the exit as a destination and move it towards the exit by the
				//overshoot distance
				robot1.reverse(d);
				robot1.setDestination(circle.getExit());
				robot1.setMode(MoveMode.EXIT);
				robot1.moveStraight(d);
			}
		}
		return Math.max(robot1.getDistance(), robot2.getDistance());
	}

	//main program, tells the view to get the parameters for the test and then uses the
	//parameters to initialize the robots and circles, then run the algorithm accordingly
	public void run() {
		while (true) {
			//get the settings from the user
			settings = new SimulationSettings();
			settings = view.getSimulationSettings();
			switch (settings.getExitMode()) {
			case RANDOM:
				//get number of tests given by user, run that many tests
				//reinitialize robots and circle each time, then give average time
				int num = settings.getNumberOfTests();
				double sum = 0;
				for (int i = 0; i < num; i++) {
					// TODO do we draw each experiment?
					initRobots();
					initCircle();
					sum += runAlgorithm();
				}
				// TODO how does view show time?
				System.out.println("Average time: " + sum / num);
				break;
			case WORST_CASE:
				//initialize the robots and circle just once, then run
				//one test and give that worst case time
				initRobots();
				initCircle();
				double time = runAlgorithm();
				// TODO how does view show time?
				System.out.println("Robot's time for worst case: " + time);
				break;
			}
		}

	}

	public static void main(String args[]) {
		MainControl control = new MainControl();
		control.run();
		//System.out.println(Math.sin(1));
		//System.out.println(Math.sin(-1));
	}
}
