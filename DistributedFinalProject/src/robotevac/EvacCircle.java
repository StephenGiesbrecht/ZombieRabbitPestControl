package robotevac;

import static robotevac.EvacPoint.EPSILON;

import java.util.Random;

public class EvacCircle {
	private EvacPoint 			exit;
	private static final int 	RADIUS = 1;

	public EvacCircle(double ex, double ey) {
		exit = new EvacPoint(ex, ey);
	}

	public EvacCircle(EvacPoint e) {
		exit = e;
	}

	public EvacCircle() {
		exit = randomPointOnCircumference();
	}

	public EvacPoint getExit() {
		return exit;
	}

	public static double getRadius() {
		return RADIUS;
	}

	public boolean isExit(EvacPoint e) {
		return exit.equals(e);
	}

	public boolean isExit(double x, double y) {
		return exit.equals(new EvacPoint(x, y));
	}

	public static boolean isInside(EvacPoint p) {
		return ((p.getX() * p.getX()) + (p.getY() * p.getY()) <= (RADIUS * RADIUS) + EPSILON);
	}

	public static boolean isOnCircumference(EvacPoint p) {
		double dist = (p.getX() * p.getX()) + (p.getY() * p.getY());
		return (dist + EPSILON >= RADIUS * RADIUS && dist - EPSILON <= RADIUS * RADIUS);
	}

	public static EvacPoint randomPointInside() {
		Random r = new Random();
		double centreDist = Math.sqrt(r.nextDouble()) * RADIUS;
		double rads = 2 * Math.PI * r.nextDouble();
		return new EvacPoint(centreDist * Math.sin(rads), centreDist * -Math.cos(rads));
	}

	public static EvacPoint randomPointOnCircumference() {
		Random r = new Random();
		double rads = 2 * Math.PI * r.nextDouble();
		return new EvacPoint(RADIUS * Math.sin(rads), RADIUS * -Math.cos(rads));
	}

}
