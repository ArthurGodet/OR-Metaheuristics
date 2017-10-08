/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package definition;

/**
 * The crossover interface.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
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
