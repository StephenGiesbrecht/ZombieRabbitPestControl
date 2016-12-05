package display;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel implements MenuActionCommands {

	private JButton bothCenter, oneRandom, bothRandom, exit;

	public MainMenuPanel() {
		initComponents();
	}

	private void initComponents() {
		bothCenter = new JButton("Robots in center");
		bothCenter.setActionCommand(BOTH_CENTER);
		oneRandom = new JButton("One robot randomized");
		oneRandom.setActionCommand(ONE_RANDOM);
		bothRandom = new JButton("Both robots randomized");
		bothRandom.setActionCommand(BOTH_RANDOM);
		exit = new JButton("Exit");
		exit.setActionCommand(EXIT);

		setLayout(new GridLayout(4, 1));
		add(bothCenter);
		add(oneRandom);
		add(bothRandom);
		add(exit);
	}

	public void addExitListener(ActionListener l) {
		exit.addActionListener(l);
	}

	public void addSelectionListener(ActionListener l) {
		bothCenter.addActionListener(l);
		oneRandom.addActionListener(l);
		bothRandom.addActionListener(l);
	}

}
