package display;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import robotevac.SimulationSettings;

/**
 *
 * This class is the main control for all GUI related operations. It acts as a
 * bridge between the Swing classes and the main control for the program that
 * runs the simulations.
 *
 */
public class GUIView {

	/**
	 * Get the simulation settings selected from the options menu. The window
	 * containing the options menu is created and destroyed before this call
	 * returns. This method blocks until the user has made all necessary choices
	 */
	public SimulationSettings getSimulationSettings() {
		MenuWindow menu = new MenuWindow();

		// If menu is closed by user, terminate program
		menu.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Launch options menu on the event thread
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				menu.createAndShow();
			}
		});

		SimulationSettings settings = menu.getSimulationSettings();
		menu.dispose();
		return settings;
	}
}


