package display;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import robotevac.ExitMode;
import robotevac.RobotMode;

@SuppressWarnings("serial")
public class MenuWindow extends JFrame implements ActionListener, MenuActionCommands {
	private MainMenuPanel mainMenu;
	private JPanel subMenu;
	private RobotMode rMode = null;
	private ExitMode eMode = null;

	public MenuWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container frame = getContentPane();
		frame.setLayout(new CardLayout());
		mainMenu = new MainMenuPanel();
		subMenu = new JPanel();
		frame.add(mainMenu, "Main Menu");
		frame.add(subMenu, "Submenu");
		mainMenu.addActionListener(this);
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals(EXIT)) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}

		if (command.equals(BOTH_CENTER)) {
			rMode = RobotMode.BOTH_CENTER;
		} else if (command.equals(ONE_RANDOM)) {
			rMode = RobotMode.ONE_RANDOM;
		} else if (command.equals(BOTH_RANDOM)) {
			rMode = RobotMode.BOTH_RANDOM;
		}
		((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Submenu");
	}

	public static void main(String[] args) {
		MenuWindow w = new MenuWindow();
	}

}
