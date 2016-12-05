package robotevac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings = menu.getSimulationSettings();
				menu.dispose();
				System.out.println("Settings retrieved");
			}
		});

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

		while (settings == null) {
			Thread.sleep(500);
		}
		System.out.println("Test");
	}

	public static void main(String args[]) throws InvocationTargetException, InterruptedException {
		MainControl control = new MainControl();
		control.run();
	}
}
