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
 * The position based crossover technique. The first nbInherited jobs of the child are randomly
 * selected from the first parent and put at the same place that the one they have in the first
 * parent's sequence. Then the child's sequence is completed with jobs of the second parent, 
 * respecting the order of appearance in the sequence.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class PositionBased implements Crossover {

	/* (non-Javadoc)
	 * @see definition.Crossover#crossover(definition.Solution, definition.Solution)
	 */
	public Solution crossover(Solution parent1, Solution parent2) {
		Solution child = new Solution(parent1.getInstance());
		int nbInherited = Random.randomInteger(1, child.getInstance().getSize());

		// add jobs randomly selected from parent1
		for(Integer i : Random.randomSample(0, child.getInstance().getSize(), nbInherited))
			child.setScheduling(parent1.getJob(i),i);

		child.merge(parent2);

		child.evaluate();
		return child;
	}
}
