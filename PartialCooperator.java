package artificiallife;

import java.util.Random;

public class PartialCooperator extends Organism {
	
	/**
	 * A zero-parameter constructor for a PartialCooperator, instantiates a PartialCooperator.
	 * Cooperates half the time.
	 */
	public PartialCooperator() {
		super();
	}
	
	/**
	 * Gets the type of the Organism, a PartialCooperator.
	 * @return String, the type of the Organism, which is a PartialCooperator
	 */
	@Override
	public String getType() {
		return "PartialCooperator";
	}

	/**
	 * The PartialCooperator reproduces, losing all energy in the process.
	 * @return Organism, a PartialCooperator
	 */
	@Override
	public Organism reproduce() {
		while (getEnergy() > 0) {
			decrementEnergy();
		}
		return new PartialCooperator();
	}

	/**
	 * Gets the cooperation probability, 0.5.
	 * @return double, 0.5.
	 */
	@Override
	public double getCooperationProbability() {
		return 0.5;
	}

	/**
	 * Returns true if the Organism cooperates by randomly generating a boolean.
	 * @return boolean, whether or not the Organism cooperates.
	 */
	@Override
	public boolean cooperates() {
		Random rand = new Random();
		return rand.nextBoolean();
	}
}
