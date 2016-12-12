package robotevac;

/**
 * Enum describing the way the robots begin the trial. BOTH_CENTER starts both robots in the
 * center of the circle, ONE_RANDOM starts one in the center and one at a random location
 * within the circle, and BOTH_RANDOM starts both at random locations within the circle.
 *
 */
public enum RobotMode {
	BOTH_CENTER, ONE_RANDOM, BOTH_RANDOM
}
