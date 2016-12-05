package display;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import robotevac.SimulationSettings;

public class GUIView {

	public SimulationSettings getSimulationSettings() {
		MenuWindow menu = new MenuWindow();

		// If menu is closed by user, terminate program
		menu.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

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


