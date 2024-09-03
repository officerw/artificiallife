

package artificiallife;
public class Defector extends Organism {
	
	/**
	 * A zero-parameter constructor used to instantiate a Defector.
	 * Defectors never cooperate.
	 */
	public Defector() {
		super();
	}

	/**
	 * Returns the type of the Organism, which is a Defector.
	 * @return String, "Defector"
	 */
	@Override
	public String getType() {
		return "Defector";
	}

	/**
	 * The Defector reproduces, losing all of its energy in the process.
	 * @return Organism, a Defector offspring.
	 */
	@Override
	public Organism reproduce() {
		while (getEnergy() > 0) {
			decrementEnergy();
		}
		return new Defector();
	}

	/**
	 * Returns the cooperation probability, which is 0 in this case.
	 * @return double, 0.
	 */
	@Override
	public double getCooperationProbability() {
		return 0;
	}

	/**
	 * Returns whether or not the Defector will cooperate, which is never.
	 * @return boolean, false.
	 */
	@Override
	public boolean cooperates() {
		return false;
	}
}
