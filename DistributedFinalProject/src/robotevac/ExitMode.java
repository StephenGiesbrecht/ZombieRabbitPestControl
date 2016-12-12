package robotevac;

/**
 * Enum describing the exit mode used by the program for a given trial
 * RANDOM has the exit located in a random place, while WORST_CASE places the exit
 * at the worst possible place for the robot locations
 *
 */
public enum ExitMode {
	RANDOM, WORST_CASE
}
