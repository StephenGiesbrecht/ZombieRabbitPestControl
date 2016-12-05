package robotevac;

import java.util.Scanner;

public class EvacTextView extends EvacView {

	@Override
	public RobotMode getRobotMode() {
		while (true) {
			System.out.println("Welcome to the Robot Evacuation Program! Please enter the number" +
								"of the option you wish to use.");
			System.out.println("1. Both robots start at center");
			System.out.println("2. One robot starts at center, the other in a random location");
			System.out.println("3. Both robots start in random locations");
			System.out.println("4. Exit the program");
			Scanner input = new Scanner(System.in);
			String s = input.nextLine();
			switch (s) {
				case "1": return RobotMode.BOTH_CENTER;
				case "2": return RobotMode.ONE_RANDOM;
				case "3": return RobotMode.BOTH_RANDOM;
				case "4": return RobotMode.EXIT;
				default: System.out.println("That is not a valid input.");
			}
		}
	}

	@Override
	public ExitMode getExitMode(RobotMode r) {
		while (true) {
			System.out.print("You have chosen ");
			switch (r) {
				case BOTH_CENTER: {
					System.out.println("to have both robots start in the center.");
					break;
				}
				case ONE_RANDOM: {
					System.out.println("to have one robot start in the center " +
										"and the other start in a random location.");
					break;
				}
				case BOTH_RANDOM: {
					System.out.println("to have both robots start in " + "a random location");
					break;
				}
			}
			System.out.println("Now choose which type of experiment you wish to perform.");
			System.out.println("1. Find the average time of evacuation with the exit in " +
								"a random location");
			System.out.println("2. Find the worst case time for evacuation");
			System.out.println("3. Return to previous choice");
			System.out.println("4. Exit the program");
			Scanner input = new Scanner(System.in);
			String s = input.nextLine();
			switch (s) {
				case "1": return ExitMode.RANDOM;
				case "2": return ExitMode.WORST_CASE;
				case "3": return ExitMode.BACK;
				case "4": return ExitMode.EXIT;
				default: System.out.println("That is not a valid input.");
			}
		}
	}

	@Override
	public void showEvac() {
		// TODO Auto-generated method stub
		System.out.println("Evac shown!");
	}

	@Override
	public void showTime(double time) {
		// TODO Auto-generated method stub
		System.out.println("Time shown!");
	}

	@Override
	public int getNumOfExperiements() {
		// TODO Auto-generated method stub
		System.out.println("Let's do 5 experiments!");
		return 5;
	}

	@Override
	public void showAvgTime(double avgTime) {
		// TODO Auto-generated method stub
		System.out.println("Average time shown!");
		
	}

}
