// Author: William Officer

package artificiallife;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * A class used to create a Population to contain Organism's.
 * We can update the population and calculate statistics regarding
 * the population using methods in this class.
 * @author William Officer
 *
 */
public class Population {
	private ArrayList<Organism> population; // This list of organisms is the population.
	private Map<String, Integer> myCounts; // Stores the current number of Cooperators, Defectors, and PartialCooperators.
	
	/**
	 * A Population single parameter constructor that constructs a population dictated by the set of pairs in the Map.
	 * @param counts, a Map that contains Organism types and the number of that type. Types must be Cooperator, Defector, or PartialCooperator.
	 * @throws IllegalArgumentException when a type in the map is not Cooperator, Defector, or PartialCooperator.
	 */
	public Population(Map<String, Integer> counts) throws IllegalArgumentException {
		population = new ArrayList<Organism>();
		myCounts = counts;
		
		Set<String> organismTypes = counts.keySet(); // Gets the types of Organism's that will be in this population.
		for (String type : organismTypes) { // Iterates over the set of types.
			// If a type is not mentioned, it will not be included in the population.
			
			// If the current type is a Cooperator, get the integer associated with Cooperator in the Map.
			// Add that many Cooperator's to the population.
			if (type.compareTo("Cooperator") == 0) {
				for (int i = 0; i < counts.get(type); i++)
					population.add(new Cooperator());
			}
			// If the current type is a Defector, get the integer associated with Defector in the Map.
			// Add that many Defector's to the population.
			else if (type.compareTo("Defector") == 0) {
				for (int i = 0; i < counts.get(type); i++)
					population.add(new Defector());
			}
			// If the current type is a PartialCooperator, get the integer associated with PartialCooperator in the Map.
			// Add that many PartialCooperator's to the population.
			else if (type.compareTo("PartialCooperator") == 0) {
				for (int i = 0; i < counts.get(type); i++)
					population.add(new PartialCooperator());
			}
			// The current type is not a Cooperator, Defector, or PartialCooperator. It is an invalid argument.
			else {
				throw new IllegalArgumentException();
			}
		}
		
		// In this algorithm, we iterate over and update the population in sequential order.
		// At this point, all of the Cooperator's are at the beginning of the population.
		// As a result, all of the Cooperator's will be updated before Defector's and PartialCooperator's.
		// To prevent this from being an unfair advantage for the Cooperators, shuffle the population.
		Collections.shuffle(population);
	}
	
	/**
	 * A public method that iterates over all the organisms in the population, updating them,
	 * checking to see if they cooperate, and checking to see if they reproduce.
	 * 
	 * An Organism reproduces if its energy level is 10 or more, replacing a randomly selected Organism in the population.
	 * When an Organism reproduces, its child cannot replace the parent, unless the population size is 1.
	 * 
	 * An Organism cannot cooperate if the population size is 1.
	 * If an Organism cooperates, it loses one energy unit and gives eight other organisms an energy unit.
	 * If the population size is 8 or less, then the Organism will cooperate with size - 1 Organism's.
	 * An Organism cannot cooperate with itself, nor can it cooperate with the same Organism more than once.
	 */
	public void update() {
		Random rand = new Random();
		Organism current = null; // The Organism we are updating.
		String replacedOrganismType = null; // The type of the Organism we replace, if current reproduces.
		int size = population.size();
		int randomIndex;
		
		for (int i = 0; i < size; i++) { // Iterate over the population.
			current = population.get(i);
			current.update(); // Update current Organism. (Increments their energy level by 1).
			
			// If the Organism cooperates and the population size is greater than 1, have current cooperate.
			if (current.cooperates() &&  size > 1) { 
				current.decrementEnergy();
				
				// If the population size is 8 or less, then the current Organism will cooperate with size - 1 organisms.
				// Otherwise, the current Organism will cooperate with 8 organisms.
				int numberToCooperateWith = size <= 8 ? size - 1 : 8; 
				ArrayList<Integer> indices = new ArrayList<Integer>(); // Stores the indices of the Organisms already cooperated with.
				
				for (int j = 0; j < numberToCooperateWith; j++) { // Cooperate numberToCooperateWith times.
					do {
						randomIndex = rand.nextInt(size);
						// If the Organism at randomIndex has already been cooperated with, generate another random number.
						// If the randomIndex is the same as i, the index the current Organism is at, generate another random number.
						// This ensures that Organism's do not cooperate with the same other Organism multiple times and that
						// they do not cooperate with themselves.
					} while (indices.contains(randomIndex) || randomIndex == i);
					indices.add(randomIndex); // Add this new randomIndex to the indices already cooperated with.
					population.get(randomIndex).incrementEnergy(); // We successfully chose an Organism to cooperate with, give it one energy.
				}
			}

			// Reproduce if the current Organism's energy level is 10 or more.
			if (current.getEnergy() >= 10) {
				randomIndex = i;
				// Ensures that the randomIndex the child is placed at is not the index of the parent.
				// Unless the population size is 1, then replace the parent with the child.
				while (randomIndex == i && size != 1) {
					randomIndex = rand.nextInt(size);
				}
				
				String currentType = current.getType();
				// The parent reproduces and the child is placed at the random index.
				replacedOrganismType = population.set(randomIndex, current.reproduce()).getType();
				// The next two lines simply update the counts Map for Cooperator, Defector, and PartialCooperator counts.
				myCounts.replace(replacedOrganismType, myCounts.get(replacedOrganismType) - 1);
				myCounts.replace(currentType, myCounts.get(currentType) + 1);
			}
		}
	}
	
	/**
	 * Calculates the mean cooperation probability for the population.
	 * @return double, the mean cooperation probability.
	 */
	public double calculateCooperationMean() {
		double sumCooperation = 0;
		int totalOrganisms = population.size();
		
		// Gets the number of cooperators and adds that to the sum of cooperation probabilities.
		if (myCounts.containsKey("Cooperator")) {
			sumCooperation += myCounts.get("Cooperator");
		}
		// Gets the number of partial cooperators and adds that number divided by 2 to the sum of cooperation probabilities.
		if (myCounts.containsKey("PartialCooperator")) {
			sumCooperation += (double)myCounts.get("PartialCooperator") / 2;
		}
		
		// Calculates arithmetic mean.
		return sumCooperation / totalOrganisms;
	}
	
	/**
	 * Returns a Map associating Organism types with the number of each type.
	 * @return Map containing Strings and Integers.
	 */
	public Map<String, Integer> getPopulationCounts() {
		return myCounts;
	}
}
