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

/**
 * This class wraps the top level frame used to display the option menus at the
 * start of the program. It provides several methods that pass through to the
 * underlying frame
 *
 *
 */
public class MenuWindow implements ActionListener, MenuActionCommands {
	private JFrame window = new JFrame("Robot Evacuation Simulation");
	private MainMenuPanel mainMenu;
	private SubmenuPanel subMenu;
	private SimulationSettings selectedSettings = new SimulationSettings();

	public MenuWindow() {

	}

	private void initListeners() {
		// Establish an action listener for buttons on the first menu
		// Store the selection and show the second menu
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

		// Establish an action listener for buttons on the second menu to store
		// the selection
		ActionListener subListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();

				switch (command) {
				// If back is pressed, forget selection from first menu and
				// redisplay
				case BACK:
					selectedSettings.setRobotMode(null);
					((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "Main Menu");
					return;

				// For random trials, use dialog box to query for number of
				// trials to run. Keep generating dialog box until response is a
				// non-negative integer
				case RANDOM_EXIT:
					while (selectedSettings.getNumberOfTests() <= 0) {
						String response = JOptionPane
								.showInputDialog( "How many tests should be run to get an average? \n One trial will be displayed while the set is running and the final average of all trials will be shown");
						if (response == null || response.equals(""))
							return;
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

	// Create listener to shut down GUI if exit is pressed. Use window closing
	// event to distinguish between this and menu closing because all selections
	// are made
	@Override
	public void actionPerformed(ActionEvent e) {
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * @see JFrame#addWindowListener(WindowListener)
	 */
	public void addWindowListener(WindowListener l) {
		window.addWindowListener(l);
	}

	/**
	 * @see JFrame#dispose()
	 */
	public void dispose() {
		window.dispose();
	}

	/**
	 * Get the simulation settings selected from the options menu. This method
	 * blocks until the user has made all necessary choices
	 *
	 * @return The selected options, as a {@link SimulationSettings}
	 */
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

	/**
	 * Populate the menu window and display it
	 */
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
