package robotevac;

public class SimulationSettings {
	private RobotMode rMode;
	private ExitMode eMode;

	public SimulationSettings() {
		rMode = null;
		eMode = null;
	}

	public SimulationSettings(RobotMode r, ExitMode e) {
		rMode = r;
		eMode = e;
	}

	public ExitMode getExitMode() {
		return eMode;
	}

	public RobotMode getRobotMode() {
		return rMode;
	}

	public void setExitMode(ExitMode e) {
		eMode = e;
	}

	public void setRobotMode(RobotMode r) {
		rMode = r;
	}
}
