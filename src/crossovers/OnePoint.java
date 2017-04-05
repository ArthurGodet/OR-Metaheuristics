/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package crossovers;

import definition.Crossover;
import definition.Solution;
import util.Random;

// TODO: Auto-generated Javadoc
/**
 * The one-point crossover.
 */
public class OnePoint implements Crossover {

	/* (non-Javadoc)
	 * @see definition.Crossover#crossover(definition.Solution, definition.Solution)
	 */
	public Solution crossover(Solution parent1, Solution parent2) {
		Solution child = new Solution(parent1.getInstance());
		int coupure = Random.randomInteger(1, child.getInstance().getNbJobs());

		child.copyRange(parent1, 0, coupure);
		child.merge(parent2);

		child.evaluate();
		return child;
	}
}
