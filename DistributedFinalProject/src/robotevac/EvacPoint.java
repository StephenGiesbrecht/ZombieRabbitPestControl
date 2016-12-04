package robotevac;

public class EvacPoint {
	public static final double EPSILON = 0.000001;
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
		return x + EPSILON >= p.getX() && x - EPSILON <= p.getX()
				&&  y + EPSILON >= p.getY() && y - EPSILON <= p.getY();
	}
}
