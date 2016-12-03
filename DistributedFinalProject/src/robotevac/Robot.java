package robotevac;

public class Robot {
	private EvacPoint location;
	private boolean exited;
	
	public Robot(double x, double y) {
		location = new EvacPoint(x, y);
		exited = false;
	}
	
	public Robot(EvacPoint l) {
		location = l;
		exited = false;
	}
	
	public EvacPoint getLocation() {
		return location;
	}
	
	public void moveTo(double newX, double newY) {
		location = new EvacPoint(newX, newY);
	}
	
	public void moveTo(EvacPoint l) {
		location = l;
	}
	
	public void exit() {
		exited = true;
	}
	
	public boolean atExit() {
		return exited;
	}
}
