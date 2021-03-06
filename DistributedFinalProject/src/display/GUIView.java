package display;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import robotevac.EvacCircle;
import robotevac.ExitMode;
import robotevac.Robot;
import robotevac.SimulationSettings;

/**
 *
 * This class is the main control for all GUI related operations. It acts as a
 * bridge between the Swing classes and the main control for the program that
 * runs the simulations.
 *
 */
public class GUIView {
	private SimulationWindow simWindow;
	private volatile boolean simRunning;

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

	/**
	 * Ensure that there are no simulations currently being displayed. This call
	 * blocks until all open simulations have been closed
	 */
	public void endSimulation() {
		if (simWindow != null) {
			while (simRunning) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
			simWindow = null;
		}
	}

	/**
	 * @see SimulationWindow#setResultsFromBackgroundTests(double, int)
	 */
	public void setResultsFromBackgroundTests(double sum, int count) {
		simWindow.setResultsFromBackgroundTests(sum, count);
	}

	/**
	 * Start a visual evacuation simulation to display to the user
	 *
	 * @param r1 The first robot in the evacuation
	 * @param r2 The second robot in the evacuation
	 * @param circle The circle being evacuated from
	 * @param mode The type of simulation being run,
	 * either {@link ExitMode#RANDOM} or {@link ExitMode#WORST_CASE}
	 */
	public void startSimulation(Robot r1, Robot r2, EvacCircle circle, ExitMode mode) {
		simRunning = true;
		simWindow = new SimulationWindow(r1, r2, circle, mode);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				simWindow.createAndShow();
			}
		});

		simWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				simRunning = false;
			}
		});
	}
}