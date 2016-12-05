package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import robotevac.EvacCircle;
import robotevac.EvacPoint;

public class InitTests extends EvacTest {

	@Test
	public void testRandomCircumferencePoints() {
		int quarters[] = new int[4];
		EvacCircle c = new EvacCircle();
		EvacPoint p;
		for (int i = 0; i < 300; i++) {
			p = c.randomPointOnCircumference();
			assertTrue("Point (" + p.getX() + "," + p.getY() + ") is not on circumference!", c.isOnCircumference(p));
			if (p.getX() > 0) {
				if (p.getY() > 0) {
					++quarters[0];
				} else {
					++quarters[1];
				}
			}
			else {
				if (p.getY() > 0) {
					++quarters[3];
				} else {
					++quarters[2];
				}
			}
		}
		for (int i = 0; i < quarters.length; ++i) {
			assertTrue("Number of points in Q" + (i + 1) + " (" + quarters[i] + ") falls outside expected variance!",
					quarters[i] < 100 && quarters[i] > 50);
		}
	}

	@Test
	public void testRandomPoints() {
		int quarters[] = new int[4];
		EvacCircle c = new EvacCircle();
		EvacPoint p;
		for (int i = 0; i < 300; i++) {
			p = c.randomPointInside();
			assertTrue("Point (" + p.getX() + "," + p.getY() + ") is not inside circle!", c.isInside(p));
			if (p.getX() > 0) {
				if (p.getY() > 0) {
					++quarters[0];
				} else {
					++quarters[1];
				}
			}
			else {
				if (p.getY() > 0) {
					++quarters[3];
				} else {
					++quarters[2];
				}
			}
		}
		for (int i = 0; i < quarters.length; ++i) {
			assertTrue("Number of points in Q" + (i + 1) + " (" + quarters[i] + ") falls outside expected variance!",
					quarters[i] < 100 && quarters[i] > 50);
		}
	}

}
