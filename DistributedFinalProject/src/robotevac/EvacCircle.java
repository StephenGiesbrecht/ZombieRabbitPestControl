package robotevac;

import static robotevac.EvacPoint.EPSILON;

import java.util.Random;

/**
 * This class represents the circle that the robots are attempting to exit
 * Keeps track of the location of the exit and offers useful functions
 * related to positioning related to the circle
 *
 */
public class EvacCircle {
	private EvacPoint 			exit;
	private static final int 	RADIUS = 1;

	//constructors that give a circle with the exit at the given point
	public EvacCircle(double ex, double ey) {
		exit = new EvacPoint(ex, ey);
	}

	public EvacCircle(EvacPoint e) {
		exit = e;
	}
	
	//default constructor gives a circle with a randomly located exit
	public EvacCircle() {
		exit = randomPointOnCircumference();
	}

	public EvacPoint getExit() {
		return exit;
	}

	public static double getRadius() {
		return RADIUS;
	}

	//returns if the given point is the exit or not
	public boolean isExit(EvacPoint e) {
		return exit.equals(e);
	}

	public boolean isExit(double x, double y) {
		return exit.equals(new EvacPoint(x, y));
	}

	//returns if the given point is inside the circle
	public static boolean isInside(EvacPoint p) {
		return ((p.getX() * p.getX()) + (p.getY() * p.getY()) <= (RADIUS * RADIUS) + EPSILON);
	}

	//returns if the given point is on the circumference of the circle
	public static boolean isOnCircumference(EvacPoint p) {
		double dist = Math.sqrt((p.getX() * p.getX()) + (p.getY() * p.getY()));
		return (dist + EPSILON >= RADIUS && dist - EPSILON <= RADIUS);
	}

	//gives a random point inside the circle
	public static EvacPoint randomPointInside() {
		Random r = new Random();
		double centreDist = Math.sqrt(r.nextDouble()) * RADIUS;
		double rads = 2 * Math.PI * r.nextDouble();
		return new EvacPoint(centreDist * Math.sin(rads), centreDist * -Math.cos(rads));
	}

	//gives a random point on the circumference of the circle
	public static EvacPoint randomPointOnCircumference() {
		Random r = new Random();
		double rads = 2 * Math.PI * r.nextDouble();
		return new EvacPoint(RADIUS * Math.sin(rads), RADIUS * -Math.cos(rads));
	}

	//returns true if the exit is between the two points given while traveling
	//in the given direction
	public boolean exitBetween(EvacPoint p1, EvacPoint p2, Direction d) {
		if (p1.equals(p2)) {
			return p1.equals(exit);
		}
		double angle1 = getAngle(p1.getX(), p1.getY());
		double angle2 = getAngle(p2.getX(), p2.getY());
		double exitAngle = getAngle(exit.getX(), exit.getY());
		if (angle1 - EPSILON <= angle2 && angle1 + EPSILON >= angle2 ) {
			
		}
		if (d.equals(Direction.CCW)) {
			if (angle1 - EPSILON < angle2) {
				angle1 += 2 * Math.PI;
			}
			return angle1 + EPSILON >= exitAngle && angle2 - EPSILON <= exitAngle;
		}
		else {
			if (angle2 - EPSILON < angle1) {
				angle2 += 2 * Math.PI;
			}
			return angle1 - EPSILON <= exitAngle && angle2 + EPSILON >= exitAngle;
		}
	}
	
	//gives the angle representation of the x and y values given
	//allows one to find the angle in radians of a point on the circle
	public static double getAngle(double deltaX, double deltaY) {
		if (deltaY - EPSILON <= 0 && deltaY + EPSILON >= 0) {
			if (deltaX + EPSILON > 0) return Math.PI / 2;
			else return (3 * Math.PI) / 2;
		}
		double angle = Math.atan(deltaX / deltaY);
		if (angle + EPSILON > 0) {
			if (deltaY + EPSILON > 0) return angle;
			else return angle + Math.PI;
		}
		else {
			if (deltaY - EPSILON < 0) return Math.PI + angle;
			else return 2 * Math.PI + angle;
		}
	}

}
