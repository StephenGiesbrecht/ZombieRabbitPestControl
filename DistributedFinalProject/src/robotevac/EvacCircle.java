package robotevac;

import static robotevac.EvacPoint.EPSILON;

import java.util.Random;

public class EvacCircle {
	private EvacPoint 	exit;
	private int radius = 1;

	public EvacCircle(double ex, double ey) {
		exit = new EvacPoint(ex, ey);
	}

	public EvacCircle(EvacPoint e) {
		exit = e;
	}

	public EvacCircle() {
		Random r = new Random();
		double rads = Math.toRadians(r.nextInt(360));
		exit = new EvacPoint(radius * Math.sin(rads), radius * Math.cos(rads));
	}

	public EvacPoint getExit() {
		return exit;
	}

	public double getRadius() {
		return radius;
	}

	public boolean isExit(EvacPoint e) {
		return exit.equals(e);
	}

	public boolean isExit(double x, double y) {
		return exit.equals(new EvacPoint(x, y));
	}

	public static boolean isInside(EvacPoint p) {
		return ((p.getX() * p.getX()) + (p.getY() * p.getY()) <= 1 + EPSILON);
	}

	public static boolean isOnCircumference(EvacPoint p) {
		double dist = (p.getX() * p.getX()) + (p.getY() * p.getY());
		return (dist + EPSILON >= 1 && dist - EPSILON <= 1);
	}

}
