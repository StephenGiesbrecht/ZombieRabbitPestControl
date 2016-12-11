package display;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import robotevac.Direction;
import robotevac.EvacCircle;
import robotevac.Robot;

@SuppressWarnings("serial")
public class SimulationWindow extends JFrame {
	private SimulationCanvas canvas;
	private EvacCircle circle;
	private Robot r1, r2;

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
		add(borderPanel);
		pack();
		canvas.init();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void dispose() {
		canvas.endSimulation();
		super.dispose();
	}

	public static void main(String args[]) {
		SimulationWindow w = new SimulationWindow(new Robot(1.0, 0, Direction.CW), new Robot(0, -1.0, Direction.CCW),
				new EvacCircle());
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				w.createAndShow();
			}
		});
	}
}
