package display;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import robotevac.ExitMode;
import robotevac.RobotMode;
import robotevac.SimulationSettings;

public class MenuWindow implements ActionListener, MenuActionCommands {
	private JFrame window = new JFrame("Robot Evacuation Simulation");
	private MainMenuPanel mainMenu;
	private SubmenuPanel subMenu;
	private SimulationSettings selectedSettings = new SimulationSettings();

	public MenuWindow() {

	}

	private void initListeners() {
		ActionListener mainListener = new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				String command = e.getActionCommand();

				if (command.equals(BOTH_CENTER)) {
					selectedSettings.setRobotMode(RobotMode.BOTH_CENTER);
				} else if (command.equals(ONE_RANDOM)) {
					selectedSettings.setRobotMode(RobotMode.ONE_RANDOM);
				} else if (command.equals(BOTH_RANDOM)) {
					selectedSettings.setRobotMode(RobotMode.BOTH_RANDOM);
				}
				((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "Submenu");
			}
		};
		mainMenu.addSelectionListener(mainListener);
		mainMenu.addExitListener(this);

		ActionListener subListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();

				switch (command) {
				case BACK:
					selectedSettings.setRobotMode(null);
					((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "Main Menu");
					return;

				case RANDOM_EXIT:
					while (selectedSettings.getNumberOfTests() <= 0) {
						String response = JOptionPane
								.showInputDialog("How many tests should be run to get an average?");
						try {
							selectedSettings.setNumberOfTests(Integer.parseInt(response));
						} catch (NumberFormatException ex) {
						}
					}
					selectedSettings.setExitMode(ExitMode.RANDOM);
					break;
				case WORST_CASE:
					selectedSettings.setExitMode(ExitMode.WORST_CASE);
					break;
				}
			}
		};
		subMenu.addSelectionListener(subListener);
		subMenu.addExitListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}

	public void addWindowListener(WindowListener l) {
		window.addWindowListener(l);
	}

	public void dispose() {
		window.dispose();
	}

	public SimulationSettings getSimulationSettings() {
		selectedSettings = new SimulationSettings();
		while (!selectedSettings.isComplete()) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
		}
		return selectedSettings;
	}

	public void createAndShow() {
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container frame = window.getContentPane();
		frame.setLayout(new CardLayout());
		mainMenu = new MainMenuPanel();
		subMenu = new SubmenuPanel();
		frame.add(mainMenu, "Main Menu");
		frame.add(subMenu, "Submenu");
		initListeners();
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
