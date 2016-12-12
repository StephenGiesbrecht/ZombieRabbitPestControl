package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import robotevac.EvacCircle;
import robotevac.Robot;

@SuppressWarnings("serial")
public class SimulationCanvas extends JPanel {

	private static final int SCALE = 150;
	private static final int CENTERX = SCALE + 8;
	private static final int CENTERY = SCALE + 8;
	private static final Dimension size = new Dimension(SCALE * 2 + 16, SCALE * 2 + 16);

	private EvacCircle circle;
	private Robot r1, r2;

	private Graphics bufferGraphics;
	private Image buffer;

	public SimulationCanvas(Robot r1, Robot r2, EvacCircle circle) {
		this.r1 = r1;
		this.r2 = r2;
		this.circle = circle;
		setPreferredSize(size);
	}

	public void init() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		buffer = createImage(size.width, size.height);
		bufferGraphics = buffer.getGraphics();
	}

	private void drawExit(EvacCircle circle, Color c, Graphics g) {
		g.setColor(c);
		g.fillRect(scaleXCoordinate(circle.getExit().getX()) - 5, scaleYCoordinate(circle.getExit().getY()) - 5, 10,
				10);
		g.setColor(Color.BLACK);
		g.drawRect(scaleXCoordinate(circle.getExit().getX()) - 5, scaleYCoordinate(circle.getExit().getY()) - 5, 10,
				10);
	}

	private void drawRobot(Robot r, Color c, Graphics g) {
		if (r.atExit())
			return;
		g.setColor(c);
		g.fillOval(scaleXCoordinate(r.getLocation().getX()) - 5, scaleYCoordinate(r.getLocation().getY()) - 5, 10,
				10);
		g.setColor(Color.BLACK);
		g.drawOval(scaleXCoordinate(r.getLocation().getX()) - 5, scaleYCoordinate(r.getLocation().getY()) - 5, 10,
				10);
	}

	@Override
	public void paint(Graphics g) {
		bufferGraphics.clearRect(0, 0, size.width, size.height);
		bufferGraphics.drawOval(8, 8, SCALE * 2, SCALE * 2);
		drawExit(circle, Color.GREEN, bufferGraphics);
		drawRobot(r1, Color.RED, bufferGraphics);
		drawRobot(r2, Color.BLUE, bufferGraphics);
		g.drawImage(buffer, 0, 0, this);
	}

	private int scaleXCoordinate(double coord) {
		return (int) Math.round(CENTERX + coord * SCALE);
	}

	private int scaleYCoordinate(double coord) {
		return (int) Math.round(CENTERY - coord * SCALE);
	}

	public void update() {
		paint(getGraphics());
	}
}
