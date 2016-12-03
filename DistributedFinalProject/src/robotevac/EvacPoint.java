package robotevac;

public class EvacPoint {
	private double x;
	private double y;
	
	public EvacPoint(double xLoc, double yLoc) {
		x = xLoc;
		y = yLoc;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean equals(EvacPoint p) {
		return x == p.getX() && y == p.getY();
	}
}
