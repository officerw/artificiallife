

package artificiallife;


public abstract class Organism {
	private int energy; // Energy level of this organism.
	
	/**
	 * Zero parameter constructor that initializes the energy level to 0.
	 */
	public Organism() {
		energy = 0;
	}
	
	/**
	 * By default, an Organism receives one energy point when it is updated.
	 */
	public void update() {
		incrementEnergy();
	}
	
	/**
	 * @return int, the energy level for the Organism
	 */
	public int getEnergy() {
		return energy;
	}
	
	/**
	 * Increments the energy of an Organism by 1.
	 */
	public void incrementEnergy() {
		energy += 1;
	}
	
	/**
	 * Decrements the energy of an Organism by 1, as long as its energy is not 0.
	 */
	public void decrementEnergy() {
		if (energy != 0) {
			energy--;
		}
	}
	
	public abstract String getType(); // Returns type of the Organism as a String.
	public abstract Organism reproduce(); // Creates an Organism and returns it.
	public abstract double getCooperationProbability(); // Gets cooperation probability for an Organism.
	public abstract boolean cooperates(); // Returns whether or not the Organism cooperates.
}
