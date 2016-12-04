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
		double x = 0;
		double y = 0;
		while (true) {
			x = 2 * Math.random() - 1;
			y = 2 * Math.random() - 1;
			EvacPoint e = new EvacPoint(x, y);
			if (isInside(e)) {
				exit = e;
				break;
			}
		}
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
		return ((p.getX() * p.getX()) + (p.getY() * p.getY()) <= 1);
	}
	
	public static boolean isOnCircumference(EvacPoint p) {
		return ((p.getX() * p.getX()) + (p.getY() * p.getY()) == 1);
	}

}
