package robotevac;

import display.GUIView;

public class MainControl {
	GUIView view = new GUIView();
	SimulationSettings settings;

	public void run() {
		settings = view.getSimulationSettings();
		System.out.println(settings.getRobotMode());
		System.out.println(settings.getExitMode());
	}

	public static void main(String args[]) {
		MainControl control = new MainControl();
		control.run();
	}
}
