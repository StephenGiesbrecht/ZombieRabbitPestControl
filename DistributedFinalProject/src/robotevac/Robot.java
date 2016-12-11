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
	
	//move the robot by SPEED
	//movement depends on current movement mode
	//handles transition from circumference to rotate
	//control handles transition from rotate to exit
	public void move() {
		switch(mode) {
		case CIRCUMFERENCE:
			//get the point that is SPEED along our current line
			double deltaX = destination.getX() - location.getX();
			double deltaY = destination.getY() - location.getY();
			double angle = EvacCircle.getAngle(deltaX, deltaY);
			double newX = location.getX() + Math.sin(angle) * SPEED;
			double newY = location.getY() + Math.cos(angle) * SPEED;
			EvacPoint newLoc = new EvacPoint(newX, newY);
			//if it's not inside the circle we overshot our destination
			if (!EvacCircle.isInside(newLoc)) {
				//find out how much we overshot by
				deltaX = newLoc.getX() - destination.getX();
				deltaY = newLoc.getY() - destination.getY(); 
				double d = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
				//increase total distance traveled by the distance between our previous
				//location and our destination
				distance += SPEED - d;
				//set our location to our destination as we have arrived there
				location = destination;
				//set movement mode to rotate for future
				mode = MoveMode.ROTATE;
				//rotate around the circle by the overshoot distance
				moveRotate(d);
			}
			//otherwise if our new location is already on the circumference switch to
			//rotate mode, set our location to destination add SPEED to our total distance
			else if (EvacCircle.isOnCircumference(newLoc)) {
				location = destination;
				mode = MoveMode.ROTATE;
				distance += SPEED;
			}
			else {
				//we haven't hit our destination yet so our location just becomes the new
				//location and we add SPEED to our total distance
				location = newLoc;
				distance += SPEED;
			}
			break;
		case ROTATE:
			//find our current position on the circle in radians
			angle = EvacCircle.getAngle(location.getX(), location.getY());
			//if we're going clockwise we need to add SPEED to our angle
			//otherwise we subtract it from our angle
			if (direction.equals(Direction.CW)) angle += SPEED;
			else angle -= SPEED;
			//adjust our angle if it goes negative
			if (angle - EPSILON < 0) angle = 2 * Math.PI + angle;
			//find our new position based on our angle and add SPEED to total distance
			location = new EvacPoint(Math.sin(angle), Math.cos(angle));
			distance += SPEED;
			//remember that control takes care of exit transition
			break;
		case EXIT:
			//get the point that is SPEED along our current line
			deltaX = destination.getX() - location.getX();
			deltaY = destination.getY() - location.getY();
			angle = EvacCircle.getAngle(deltaX, deltaY);
			newX = location.getX() + Math.sin(angle) * SPEED;
			newY = location.getY() + Math.cos(angle) * SPEED;
			newLoc = new EvacPoint(newX, newY);
			//if it's not inside the circle we overshot our destination
			if (!EvacCircle.isInside(newLoc)) {
				//find out how much we overshot by
				deltaX = newLoc.getX() - destination.getX();
				deltaY = newLoc.getY() - destination.getY(); 
				double d = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
				//set our location to our destination as we have arrived there
				location = destination;
				//increase total distance traveled by the distance between our previous
				//location and our destination
				distance += SPEED - d;
				//set exited to true as we have found the exit
				exited = true;
			}
			else {
				//we haven't hit our destination yet so our location just becomes the new
				//location and we add SPEED to our total distance
				location = newLoc;
				distance += SPEED;
			}
			break;
		}
	}
	
	//rotate along the circumference of the circle by d units
	public void moveRotate(double d) {
		//find our current position on the circle in radians
		double angle = EvacCircle.getAngle(location.getX(), location.getY());
		//if we're going clockwise we need to add d to our angle
		//otherwise we subtract it from our angle
		if (direction.equals(Direction.CW)) angle += d;
		else angle -= d;
		//adjust our angle if it goes negative
		if (angle - EPSILON < 0) angle = 2 * Math.PI + angle;
		//find our new position based on our angle and add d to total distance
		location = new EvacPoint(Math.sin(angle), Math.cos(angle));
		distance += d;
	}
	
	public void moveStraight(double d) {
		//get the point that is d along our current line
		double deltaX = destination.getX() - location.getX();
		double deltaY = destination.getY() - location.getY();
		double angle = EvacCircle.getAngle(deltaX, deltaY);
		double newX = location.getX() + Math.sin(angle) * d;
		double newY = location.getY() + Math.cos(angle) * d;
		//set location to the new location
		location = new EvacPoint(newX, newY);
		//increase total distance traveled by d
		distance += d;
	}

	//move backwards by d units
	//movement is changed depending on movement mode
	public void reverse(double d) {
		switch(mode) {
		//if movement mode is rotate we need to move around the circle
		case ROTATE:
			//find our current position on the circle in radians
			double angle = EvacCircle.getAngle(location.getX(), location.getY());
			//if we're going clockwise we need to subtract d from our angle
			//otherwise we add it to our angle
			if (direction.equals(Direction.CW)) angle -= d;
			else angle += d;
			//adjust our angle if it goes negative
			if (angle - EPSILON < 0) angle = 2 * Math.PI + angle;
			//find our new position based on our angle and subtract d from total distance
			location = new EvacPoint(Math.sin(angle), Math.cos(angle));
			distance -= d;
			break;
		//if movement mode is not rotate we need to move in a straight line
		case CIRCUMFERENCE: case EXIT:
			//get the point that is d behind us on our current line
			double deltaX = location.getX() - destination.getX();
			double deltaY = location.getY() - destination.getY();
			angle = EvacCircle.getAngle(deltaX, deltaY);
			double newX = location.getX() + Math.sin(angle) * d;
			double newY = location.getY() + Math.cos(angle) * d;
			//set location to the new location
			location = new EvacPoint(newX, newY);
			//decrease total distance traveled by d
			distance -= d;
			break;
		}
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
