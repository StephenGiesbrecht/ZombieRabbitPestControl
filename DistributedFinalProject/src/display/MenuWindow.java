package display;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.event.EventListenerList;

import robotevac.ExitMode;
import robotevac.RobotMode;
import robotevac.SimulationSettings;

@SuppressWarnings("serial")
public class MenuWindow extends JFrame implements ActionListener, MenuActionCommands {
	private MainMenuPanel mainMenu;
	private SubmenuPanel subMenu;
	private SimulationSettings selectedSettings = new SimulationSettings();
	protected EventListenerList listenerList = new EventListenerList();

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
				((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Submenu");
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
					((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Main Menu");
					return;

				case RANDOM_EXIT:
					selectedSettings.setExitMode(ExitMode.RANDOM);
					break;
				case WORST_CASE:
					selectedSettings.setExitMode(ExitMode.WORST_CASE);
					break;
				}
				fireActionEvent();
			}
		};
		subMenu.addSelectionListener(subListener);
		subMenu.addExitListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	// This is a bit of a hack, using ActionEvent improperly to avoid defining
	// new event and listener classes for this single use
	public void addActionListener(ActionListener l) {
		this.listenerList.add(ActionListener.class, l);
	}

	public void removeActionListener(ActionListener l) {
		this.listenerList.remove(ActionListener.class, l);
	}

	protected void fireActionEvent() {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ActionListener.class) {
				ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, SETTINGS_PICKED);
				((ActionListener) listeners[i + 1]).actionPerformed(e);
			}
		}
	}

	public SimulationSettings getSimulationSettings() {
		return selectedSettings;
	}

	public void createAndShow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container frame = getContentPane();
		frame.setLayout(new CardLayout());
		mainMenu = new MainMenuPanel();
		subMenu = new SubmenuPanel();
		frame.add(mainMenu, "Main Menu");
		frame.add(subMenu, "Submenu");
		initListeners();
		pack();
		setVisible(true);
	}
}
