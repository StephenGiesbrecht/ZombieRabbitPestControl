package robotevac;

public class EvacCircle {
	private EvacPoint exit;
	private double radius;
	
	public EvacCircle(double ex, double ey, double r) {
		exit = new EvacPoint(ex, ey);
		radius = r;
	}
	
	public EvacCircle(EvacPoint e, double r) {
		exit = e;
		radius = r;
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
