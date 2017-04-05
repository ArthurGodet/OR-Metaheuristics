/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package definition;

// TODO: Auto-generated Javadoc
/**
 * The crossover interface.
 */
public interface Crossover {

	/**
	 * Crossover.
	 *
	 * @param parent1 the parent 1
	 * @param parent2 the parent 2
	 * @return the solution
	 */
	public Solution crossover(Solution parent1, Solution parent2);
}
