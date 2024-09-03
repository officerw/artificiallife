
package artificiallife;

import java.util.HashMap;

public class ALifeSim {
	/**
	 * A main method used to simulate a Population for x iterations.
	 * Command-line arguments should follow the format <iterations> <cooperators> <defectors> <partial cooperators>
	 * @param args
	 */
	public static void main(String [] args) {
		int iterations = 0;
		int numCooperators = 0;
		int numDefectors = 0;
		int numPartials = 0;
		
		HashMap<String, Integer> organismCounts = null; // HashMap used to initialize the Population class.
		try {
			if (args.length != 4) { // Check number of command line arguments. Must be 4.
				System.out.println("Incorrect number of command line arguments.");
				System.out.println("Command-line arguments should follow the format <iterations> <cooperators> <defectors> <partial cooperators>.");
				System.exit(1);
			}
			
			// Take command line arguments and store them to instantiate the HashMap.
			iterations = Integer.parseInt(args[0]);
			numCooperators = Integer.parseInt(args[1]);
			numDefectors = Integer.parseInt(args[2]);
			numPartials = Integer.parseInt(args[3]);
			
			// There must be at least 1 iteration. Also, the number of each Organism cannot be below 0.
			if (iterations < 1 || numCooperators < 0 || numDefectors < 0 || numPartials < 0) {
				System.out.println("Number of iterations must be 1 or greater.");
				System.out.println("Number of Cooperators, Defectors, and Partial Cooperators must be 0 or greater.");
				System.exit(1);
			}
			
			// There must be at least 1 Organism in the population.
			if (numCooperators + numDefectors + numPartials < 1) {
				System.out.println("Simulation must have at least one organism.");
				System.exit(1);
			}
			
			// Instantiates HashMap.
			organismCounts = new HashMap<String, Integer>();
			
			// Using the command line arguments, adds pairs of Strings and Integers.
			if (numCooperators != 0) {
				organismCounts.put("Cooperator", numCooperators);
			}
			if (numDefectors != 0) {
				organismCounts.put("Defector", numDefectors);
			}
			if (numPartials != 0) {
				organismCounts.put("PartialCooperator", numPartials);
			}
			
			// Instantiates a Population using the previously created HashMap.
			Population population = new Population(organismCounts);
			for (int i = 0; i < iterations; i++) { // Updates the Population iterations number of times.
				population.update();
			}
			// Updates the organismCounts HashMap after the Population has been simulated.
			organismCounts = (HashMap<String, Integer>)population.getPopulationCounts();
			
			// Resets numCooperators, numDefectors, and numPartials for output purposes.
			numCooperators = 0;
			numDefectors = 0;
			numPartials = 0;
			
			// Updates values of numCooperators, numDefectors, and numPartials based on the updated HashMap.
			if (organismCounts.containsKey("Cooperator")) {
				numCooperators = organismCounts.get("Cooperator");
			}
			if (organismCounts.containsKey("Defector")) {
				numDefectors = organismCounts.get("Defector");
			}
			if (organismCounts.containsKey("PartialCooperator")) {
				numPartials = organismCounts.get("PartialCooperator");
			}
			
			// Gives the user output, summarizing the simulation of the population.
			System.out.println("After " + iterations + " ticks");
			System.out.printf("%-11s = %d\n", "Cooperators", numCooperators);
			System.out.printf("%-11s = %d\n", "Defectors", numDefectors);
			System.out.printf("%-11s = %d\n", "Partial", numPartials);
			System.out.println("\nMean Cooperation Probability = " + population.calculateCooperationMean());
		}
		catch (NumberFormatException e) { // The user did not give the correct type of arguments.
			System.out.println("All command line arguments must be integers.");
			System.exit(1);
		}
		catch (IllegalArgumentException e) { // The user gave an Organism type other than Cooperator, Defector, and PartialCooperator.
			System.out.println("Available organism types are Cooperator, Defector, and PartialCooperator.");
			System.exit(1);
		}
		catch (Exception e) { // Catches all other exceptions.
			System.out.println(e);
			System.exit(1);
		}
	}
}
