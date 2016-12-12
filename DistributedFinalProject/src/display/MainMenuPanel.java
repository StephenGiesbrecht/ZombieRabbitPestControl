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
 * This class contains the main options menu where the robot starting locations
 * can be selected
 *
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel implements MenuActionCommands {

	private JButton bothCenter, oneRandom, bothRandom, exit;

	public MainMenuPanel() {
		initComponents();
	}

	// Create and position the components
	private void initComponents() {

		Insets margins = new Insets(5, 10, 5, 10);
		bothCenter = new JButton("Robots in center");
		bothCenter.setMargin(margins);
		bothCenter.setActionCommand(BOTH_CENTER);
		oneRandom = new JButton("One robot randomized");
		oneRandom.setMargin(margins);
		oneRandom.setActionCommand(ONE_RANDOM);
		bothRandom = new JButton("Both robots randomized");
		bothRandom.setMargin(margins);
		bothRandom.setActionCommand(BOTH_RANDOM);
		exit = new JButton("Exit");
		exit.setMargin(margins);
		exit.setActionCommand(EXIT);

		JPanel buttonPanel = new JPanel();
		GridLayout layout = new GridLayout(4, 1);
		layout.setVgap(15);
		buttonPanel.setLayout(layout);
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPanel.add(bothCenter);
		buttonPanel.add(oneRandom);
		buttonPanel.add(bothRandom);
		buttonPanel.add(exit);

		JPanel labelPanel = new JPanel();
		JLabel label = new JLabel("Select a placement scenario:");
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
	 * Attach a given action listener to the three mode selection buttons on the menu
	 * 
	 * @param l The ActionListener to attach
	 */
	public void addSelectionListener(ActionListener l) {
		bothCenter.addActionListener(l);
		oneRandom.addActionListener(l);
		bothRandom.addActionListener(l);
	}

}
