package robotevac;

public class SimulationSettings {
	private RobotMode rMode;
	private ExitMode eMode;
	private int numTests;

	public SimulationSettings() {
		rMode = null;
		eMode = null;
		numTests = 0;
	}

	public SimulationSettings(RobotMode r, ExitMode e, int count) {
		rMode = r;
		eMode = e;
		numTests = count;
	}

	public ExitMode getExitMode() {
		return eMode;
	}

	public int getNumberOfTests() {
		return numTests;
	}

	public RobotMode getRobotMode() {
		return rMode;
	}

	public boolean isComplete() {
		return (rMode != null && eMode != null);
	}

	public void setExitMode(ExitMode e) {
		eMode = e;
	}

	public void setNumberOfTests(int count) {
		numTests = count;
	}

	public void setRobotMode(RobotMode r) {
		rMode = r;
	}
}
