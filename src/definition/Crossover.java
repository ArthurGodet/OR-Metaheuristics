/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package definition;

/**
 * The crossover interface.
 */
public interface Crossover {

	/**
	 * Do a type of crossover technique to create a child from the two parents.
	 *
	 * @param parent1 the parent 1
	 * @param parent2 the parent 2
	 * @return the solution from the genetic crossover
	 */
	public Solution crossover(Solution parent1, Solution parent2);
}
