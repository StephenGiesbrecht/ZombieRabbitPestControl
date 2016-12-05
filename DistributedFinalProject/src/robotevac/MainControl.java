package robotevac;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import display.MenuWindow;

public class MainControl {

	private SimulationSettings settings;
	private MenuWindow menu;

	public void run() throws InvocationTargetException, InterruptedException {
		menu = new MenuWindow();

		// If menu is closed by user, terminate program
		menu.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
					System.exit(0);
			}
		});

		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				menu.createAndShow();
			}
		});

		settings = menu.getSimulationSettings();
		System.out.println(settings.getRobotMode());
		System.out.println(settings.getExitMode());
		menu.dispose();
	}

	public static void main(String args[]) throws InvocationTargetException, InterruptedException {
		MainControl control = new MainControl();
		control.run();
	}
}
