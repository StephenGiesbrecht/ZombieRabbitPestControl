package display;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * This class contains the secondary options menu where the type of simulation
 * to run (randomized or worst case) can be selected
 *
 */
@SuppressWarnings("serial")
public class SubmenuPanel extends JPanel implements MenuActionCommands {
	private JButton random, worst, back, exit;

	public SubmenuPanel() {
		initComponents();
	}

	// Create and position the components
	private void initComponents() {

		Insets margins = new Insets(5, 18, 5, 18);
		random = new JButton("Average performance with random exits");
		random.setMargin(margins);
		random.setActionCommand(RANDOM_EXIT);
		worst = new JButton("Worst case performance");
		worst.setMargin(margins);
		worst.setActionCommand(WORST_CASE);
		back = new JButton("Back");
		back.setMargin(margins);
		back.setActionCommand(BACK);
		exit = new JButton("Exit");
		exit.setMargin(margins);
		exit.setActionCommand(EXIT);

		JPanel buttonPanel = new JPanel();
		GridLayout layout = new GridLayout(4, 1);
		layout.setVgap(15);
		buttonPanel.setLayout(layout);
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPanel.add(random);
		buttonPanel.add(worst);
		buttonPanel.add(back);
		buttonPanel.add(exit);

		JPanel labelPanel = new JPanel();
		JLabel label = new JLabel("Select an evaluation mode:");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel.setBorder(new EmptyBorder(10, 0, 20, 0));
		labelPanel.add(label);
		setLayout(new BorderLayout());
		add(labelPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);

	}
	/**
	 * Attach a given action listener to the exit button on the menu
	 *
	 * @param l The ActionListener to attach
	 */
	public void addExitListener(ActionListener l) {
		exit.addActionListener(l);
	}

	/**
	 * Attach a given action listener to the three selection buttons on the menu
	 * 
	 * @param l The ActionListener to attach
	 */
	public void addSelectionListener(ActionListener l) {
		random.addActionListener(l);
		worst.addActionListener(l);
		back.addActionListener(l);
	}
}
