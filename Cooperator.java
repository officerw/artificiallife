// Author: William Officer

package artificiallife;

/**
 * A class that extends Organism. A Cooperator, a type of Organism that always cooperates.
 * @author William Officer
 */
public class Cooperator extends Organism {
	
	/**
	 * A zero-parameter constructor that instantiates a Cooperator.
	 * Cooperators always cooperate.
	 */
	public Cooperator() {
		super();
	}

	/**
	 * Gives the type of the Organism. In this case, it always returns "Cooperator".
	 * @return String, the type of the Organism
	 */
	@Override
	public String getType() {
		return "Cooperator";
	}

	/**
	 * The Cooperator reproduces, losing all their energy in the process.
	 * @return Organism, returns a Cooperator.
	 */
	@Override
	public Organism reproduce() {
		while (getEnergy() > 0) {
			decrementEnergy();
		}
		return new Cooperator();
	}

	/**
	 * Returns the cooperation probability, which is always 1.
	 * @return double, 1.
	 */
	@Override
	public double getCooperationProbability() {
		return 1;
	}

	/**
	 * Given that this is a Cooperator, it always cooperates, returns true.
	 * @return boolean, whether or not the organism cooperates.
	 */
	@Override
	public boolean cooperates() {
		return true;
	}
}
