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

	public boolean isInside(EvacPoint p) {
		return ((p.getX() * p.getX()) + (p.getY() * p.getY()) <= (radius * radius) + EPSILON);
	}

	public boolean isOnCircumference(EvacPoint p) {
		double dist = (p.getX() * p.getX()) + (p.getY() * p.getY());
		return (dist + EPSILON >= radius * radius && dist - EPSILON <= radius * radius);
	}

	public EvacPoint randomPointInside() {
		Random r = new Random();
		double centreDist = r.nextDouble() * radius;
		double rads = Math.toRadians(r.nextInt(360));
		return new EvacPoint(centreDist * Math.sin(rads), centreDist * Math.cos(rads));
	}

}
