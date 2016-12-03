package robotevac;

public class EvacCircle {
	private EvacPoint 	exit;
	private int 		radius = 1;
	
	public EvacCircle(double ex, double ey) {
		exit = new EvacPoint(ex, ey);
	}
	
	public EvacCircle(EvacPoint e) {
		exit = e;
	}
	
	public EvacCircle() {
		// TODO make random exit on circumference
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
		return ((p.getX() * p.getX()) + (p.getY() * p.getY()) <= radius * radius);
	}
	
	public boolean isOnCircumference(EvacPoint p) {
		return ((p.getX() * p.getX()) + (p.getY() * p.getY()) == radius * radius);
	}

}
