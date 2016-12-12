package display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import robotevac.EvacCircle;
import robotevac.Robot;

@SuppressWarnings("serial")
public class SimulationWindow extends JFrame implements ActionListener {
	private SimulationCanvas canvas;
	private EvacCircle circle;
	private Robot r1, r2;
	private Timer timer;
	private JLabel timeLabel;
	private JButton finish;

	public SimulationWindow(Robot r1, Robot r2, EvacCircle circle) {
		this.r1 = r1;
		this.r2 = r2;
		this.circle = circle;
	}

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
		timeLabel = new JLabel("Time for evacuation: ");
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		controlPanel.add(timeLabel, BorderLayout.NORTH);
		controlPanel.setBorder(new EmptyBorder(0, 70, 10, 70));

		finish = new JButton("Finish");
		finish.addActionListener(this);
		finish.setEnabled(false);
		finish.setMaximumSize(new Dimension(40, 10));
		controlPanel.add(finish, BorderLayout.SOUTH);
		add(controlPanel, BorderLayout.SOUTH);
		pack();
		canvas.init();
		setLocationRelativeTo(null);

		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.update();

				if (r1.atExit() && r2.atExit()) {
					timeLabel.setText(
							String.format("Time for evacuation: %1$.4f", Math.max(r1.getDistance(), r2.getDistance())));
					finish.setEnabled(true);
				}
			}
		});
		timer.start();

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}

	@Override
	public void dispose() {
		timer.stop();
		super.dispose();
	}
}
