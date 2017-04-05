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
 * The position based crossover.
 */
public class PositionBased implements Crossover {

	/* (non-Javadoc)
	 * @see definition.Crossover#crossover(definition.Solution, definition.Solution)
	 */
	public Solution crossover(Solution parent1, Solution parent2) {
		Solution child = new Solution(parent1.getInstance());
		int nbHerites = Random.randomInteger(1, child.getInstance().getNbJobs());

		// ajoute des jobs choisis aléatoirement depuis parent1
		for(Integer i : Random.randomSample(0, child.getInstance().getNbJobs(), nbHerites))
			child.setOrder(parent1.getJob(i),i);

		child.merge(parent2);

		child.evaluate();
		return child;
	}
}
