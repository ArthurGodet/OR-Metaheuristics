/**
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package crossovers;

import definition.Crossover;
import definition.Solution;
import util.Random;

/**
 * The one-point crossover technique. After having randomly picked a cutting point, a child is
 * created taking the first part of the first parent in respect to the cutting point. Then the
 * child sequence (the DNA) is completed with jobs from the second parent in the same order as
 * they appear in the second parent's sequence.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class OnePoint implements Crossover {

	/* (non-Javadoc)
	 * @see definition.Crossover#crossover(definition.Solution, definition.Solution)
	 */
	public Solution crossover(Solution parent1, Solution parent2) {
		Solution child = new Solution(parent1.getInstance());
		int cut = Random.randomInteger(1, child.getInstance().getSize());

		child.copyRange(parent1, 0, cut);
		child.merge(parent2);

		child.evaluate();
		return child;
	}
}
