package display;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import robotevac.EvacCircle;
import robotevac.ExitMode;
import robotevac.Robot;

/**
 * This class is the top level frame that displays the active evacuation
 * simulation to the user
 *
 */
@SuppressWarnings("serial")
public class SimulationWindow extends JFrame implements ActionListener {
	private EvacCircle circle;
	private Robot r1, r2;
	private ExitMode mode;
	private SimulationCanvas canvas;
	private Timer timer;
	private JLabel timeLabel, avgTimeLabel;
	private JButton finish;
	private volatile double resultSum = 0;
	private volatile int resultCount = 0;

	public SimulationWindow(Robot r1, Robot r2, EvacCircle circle, ExitMode mode) {
		this.r1 = r1;
		this.r2 = r2;
		this.circle = circle;
		this.mode = mode;
	}

	// This listener is attached to the finish button and closes the window when
	// the user is done viewing the simulation
	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}

	/**
	 * Populate the simulation window and display it
	 */
	public void createAndShow() {
		setTitle("Robot Evacuation");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel borderPanel = new JPanel();
		borderPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		canvas = new SimulationCanvas(r1, r2, circle);
		borderPanel.add(canvas);
		setLayout(new BorderLayout(0, 10));
		add(borderPanel, BorderLayout.NORTH);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout(0, 10));
		JPanel labelPanel = new JPanel();
		if (mode == ExitMode.WORST_CASE) {
			labelPanel.setLayout(new BorderLayout());
			labelPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
			timeLabel = new JLabel("Time required for this evacuation: ");
			labelPanel.add(timeLabel, BorderLayout.WEST);
		}
		else {
			labelPanel.setLayout(new GridLayout(2, 1, 0, 10));
			labelPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
			timeLabel = new JLabel("Time required for this evacuation: ");
			avgTimeLabel = new JLabel("Average time required for evacuation: ");
			labelPanel.add(timeLabel);
			labelPanel.add(avgTimeLabel);
		}

		controlPanel.add(labelPanel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(0, 40, 0, 40));
		finish = new JButton("Finish");
		finish.addActionListener(this);
		finish.setEnabled(false);
		buttonPanel.add(finish);

		controlPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(controlPanel, BorderLayout.SOUTH);
		pack();
		canvas.init();
		setLocationRelativeTo(null);

		// Create a timer responsible for updating the rendering of the
		// simulation
		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Redraw current positions on canvas
				canvas.repaint();

				// Show total time taken once simulation has concluded
				if (r1.atExit() && r2.atExit()) {
						timeLabel.setText(
								String.format("Time required for this evacuation: %1$.4f",
										Math.max(r1.getDistance(), r2.getDistance())));

					// For only one trial, stop updating immediately
					if (mode == ExitMode.WORST_CASE) {
						timer.stop();
						finish.setEnabled(true);
					}

					// If doing set of random trials, wait for average result
					// and display that before stopping updates
					else {
						if (resultCount != 0) {
							double avgTime = (resultSum + Math.max(r1.getDistance(), r2.getDistance()))
									/ (resultCount + 1);
							avgTimeLabel
									.setText(String.format("Average time required for evacuation: %1$.4f", avgTime));
							timer.stop();
							finish.setEnabled(true);
						}
					}
				}
			}
		});
		timer.start();
		setVisible(true);
	}

	/**
	 * Set the result of background randomized tests so that they can be
	 * displayed to the user along with results of active simulation
	 * 
	 * @param sum The sum of the results from all the background tests
	 * @param count The number of background tests that were run
	 */
	public void setResultsFromBackgroundTests(double sum, int count) {
		resultSum = sum;
		resultCount = count;
	}
}
