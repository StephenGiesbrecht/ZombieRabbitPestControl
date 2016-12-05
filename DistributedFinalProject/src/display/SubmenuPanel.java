package display;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SubmenuPanel extends JPanel implements MenuActionCommands {
	private JButton random, worst, back, exit;

	public SubmenuPanel() {
		initComponents();
	}

	private void initComponents() {
		random = new JButton("Random exit position");
		random.setActionCommand(RANDOM_EXIT);
		worst = new JButton("Worst case scenario");
		worst.setActionCommand(WORST_CASE);
		back = new JButton("Back");
		back.setActionCommand(BACK);
		exit = new JButton("Exit");
		exit.setActionCommand(EXIT);

		setLayout(new GridLayout(4, 1));
		add(random);
		add(worst);
		add(back);
		add(exit);
	}

	public void addExitListener(ActionListener l) {
		exit.addActionListener(l);
	}

	public void addSelectionListener(ActionListener l) {
		random.addActionListener(l);
		worst.addActionListener(l);
		back.addActionListener(l);
	}
}
