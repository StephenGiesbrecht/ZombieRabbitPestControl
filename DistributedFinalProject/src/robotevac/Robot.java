package robotevac;

import static robotevac.EvacPoint.EPSILON;

public class Robot {
	private static final double	SPEED = 0.001;
	private EvacPoint 			location;
	private EvacPoint 			destination;
	private boolean 			exited;
	private double				distance;
	private MoveMode			mode;
	private Direction			direction;
	
	public Robot(double x, double y, Direction d) {
		location = new EvacPoint(x, y);
		destination = new EvacPoint(0, 0);
		exited = false;
		mode = MoveMode.CIRCUMFERENCE;
		direction = d;
	}
	
	public Robot(EvacPoint l, Direction d) {
		location = l;
		destination = new EvacPoint(0, 0);
		exited = false;
		mode = MoveMode.CIRCUMFERENCE;
		direction = d;
	}
	
	public EvacPoint getLocation() {
		return location;
	}
	
	public EvacPoint getDestination() {
		return destination;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public MoveMode getMode() {
		return mode;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void move() {
		switch(mode) {
		case CIRCUMFERENCE:
			double deltaX = destination.getX() - location.getX();
			double deltaY = destination.getY() - location.getY();
			double angle = EvacCircle.getAngle(deltaX, deltaY);
			double newX = location.getX() + Math.sin(angle) * SPEED;
			double newY = location.getY() + Math.cos(angle) * SPEED;
			EvacPoint newLoc = new EvacPoint(newX, newY);
			if (!EvacCircle.isInside(newLoc)) {
				deltaX = newLoc.getX() - destination.getX();
				deltaY = newLoc.getY() - destination.getY(); 
				double d = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
				distance += SPEED - d;
				location = destination;
				mode = MoveMode.ROTATE;
				moveRotate(d);
			}
			else {
				location = newLoc;
				distance += SPEED;
			}
			break;
		case ROTATE:
			angle = EvacCircle.getAngle(location.getX(), location.getY());
			if (direction.equals(Direction.CW)) angle += SPEED;
			else angle -= SPEED;
			if (angle - EPSILON < 0) angle = 2 * Math.PI + angle;
			location = new EvacPoint(Math.sin(angle), Math.cos(angle));
			distance += SPEED;
			break;
		case EXIT:
			deltaX = destination.getX() - location.getX();
			deltaY = destination.getY() - location.getY();
			angle = EvacCircle.getAngle(deltaX, deltaY);
			newX = location.getX() + Math.sin(angle) * SPEED;
			newY = location.getY() + Math.cos(angle) * SPEED;
			newLoc = new EvacPoint(newX, newY);
			if (!EvacCircle.isInside(newLoc)) {
				deltaX = newLoc.getX() - destination.getX();
				deltaY = newLoc.getY() - destination.getY(); 
				double d = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
				distance += SPEED - d;
				exited = true;
			}
			else {
				location = newLoc;
				distance += SPEED;
			}
			break;
		}
	}
	
	public void moveRotate(double d) {
		double angle = EvacCircle.getAngle(location.getX(), location.getY());
		if (direction.equals(Direction.CW)) angle += d;
		else angle -= d;
		if (angle - EPSILON < 0) angle = 2 * Math.PI + angle;
		location = new EvacPoint(Math.sin(angle), Math.cos(angle));
		distance += d;
	}
	
	public void moveStraight(double d) {
		double deltaX = location.getX() - destination.getX();
		double deltaY = location.getY() - destination.getY();
		double angle = EvacCircle.getAngle(deltaX, deltaY);
		double newX = location.getX() + Math.sin(angle) * d;
		double newY = location.getY() + Math.cos(angle) * d;
		location = new EvacPoint(newX, newY);
		distance += d;
	}

	public void reverse(double d) {
		double angle = EvacCircle.getAngle(location.getX(), location.getY());
		if (direction.equals(Direction.CW)) angle -= d;
		else angle += d;
		if (angle - EPSILON < 0) angle = 2 * Math.PI + angle;
		location = new EvacPoint(Math.sin(angle), Math.cos(angle));
		distance -= d;
	}
	
	public void setDestination(EvacPoint p) {
		destination = p;
	}
	
	public void exit() {
		exited = true;
	}
	
	public boolean atExit() {
		return exited;
	}
	
	public void setMode(MoveMode m) {
		mode = m;
	}
}
