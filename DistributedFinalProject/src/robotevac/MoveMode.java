package robotevac;

/**
 * Enum describing the type of movement a robot is currently performing.
 * CIRCUMFERENCE is used when the robot is moving from its starting location within the circle
 * to the circumference of the circle. ROTATE is used when the robot is moving around the
 * circumference of the circle looking for the exit. EXIT is used when the other robot has
 * found the exit and this robot is now crossing the circle to reach the exit.
 *
 */
public enum MoveMode {
	CIRCUMFERENCE, ROTATE, EXIT
}
