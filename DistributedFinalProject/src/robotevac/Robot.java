package robotevac;

public class Robot {
	private static final double	SPEED = 0.1;
	private EvacPoint 			location;
	private EvacPoint 			destination;
	private boolean 			exited;
	private double				distance;
	private MoveMode			mode;
	
	public Robot(double x, double y) {
		location = new EvacPoint(x, y);
		destination = new EvacPoint(0, 0);
		exited = false;
		mode = MoveMode.CIRCUMFERENCE;
	}
	
	public Robot(EvacPoint l) {
		location = l;
		destination = new EvacPoint(0, 0);
		exited = false;
		mode = MoveMode.CIRCUMFERENCE;
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
	
	public void move() {
		switch(mode) {
		case CIRCUMFERENCE:
			double deltaX = Math.abs(location.getX() - destination.getX());
			double deltaY = Math.abs(location.getY() - destination.getY());
			double angle = Math.atan(deltaX / deltaY);
			double newX = location.getX() + Math.sin(angle) * SPEED;
			double newY = location.getY() + Math.cos(angle) * SPEED;
			EvacPoint newLoc = new EvacPoint(newX, newY);
			if (!EvacCircle.isInside(newLoc)) {
				deltaX = Math.abs(newLoc.getX() - destination.getX());
				deltaY = Math.abs(newLoc.getY() - destination.getY()); 
				double d = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
				distance += SPEED - d;
				location = destination;
				mode = MoveMode.ROTATE;
				move(d);
			}
			else {
				location = newLoc;
				distance += SPEED;
			}
			break;
		case ROTATE:
			angle = Math.atan(location.getX() / location.getY());
			angle += SPEED;
			location = new EvacPoint(Math.sin(angle), Math.cos(angle));
			distance += SPEED;
			break;
		case EXIT:
			deltaX = Math.abs(location.getX() - destination.getX());
			deltaY = Math.abs(location.getY() - destination.getY());
			angle = Math.atan(deltaX / deltaY);
			newX = location.getX() + Math.sin(angle) * SPEED;
			newY = location.getY() + Math.cos(angle) * SPEED;
			newLoc = new EvacPoint(newX, newY);
			if (!EvacCircle.isInside(newLoc)) {
				deltaX = Math.abs(newLoc.getX() - destination.getX());
				deltaY = Math.abs(newLoc.getY() - destination.getY()); 
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
	
	public void move(double d) {
		double angle = Math.atan(location.getX() / location.getY());
		angle += d;
		location = new EvacPoint(Math.sin(angle), Math.cos(angle));
		distance += d;
	}

	public void reverse(double d) {
		double angle = Math.atan(location.getX() / location.getY());
		angle -= d;
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
