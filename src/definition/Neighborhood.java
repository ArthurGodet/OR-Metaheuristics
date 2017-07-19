/**
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package definition;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for the different types of neighborhood that can be built from a solution.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public abstract class Neighborhood {

	/**
	 * Computes randomly a neighbor of the solution
	 *
	 * @param solution the solution
	 */
	public abstract void assignRandomNeighbor(Solution solution);

	/**
	 * Gets the neighborhood of the solution.
	 *
	 * @param solution the solution
	 * @return an Iterable object on the solutions (that need to be cloned in order to be manipulated)
	 */
	public abstract Iterable<Solution> getNeighbors(Solution solution);

	/**
	 * Gets the best neighbor of the solution.
	 *
	 * @param solution the solution
	 * @return a best solution in the solution's neighborhood
	 */
	public Solution getBestNeighbor(Solution solution) {
		Solution best = solution.clone();
		for(Solution s : getNeighbors(solution))
			if(s.compareTo(best) < 0)
				best = s.clone();
		return best;
	}

	/**
	 * Gets the neighbors list.
	 *
	 * @param solution the solution
	 * @return the list of solution forming the neighborhood of the solution parameter
	 */
	public List<Solution> getNeighborsList(Solution solution) {
		List<Solution> list = new ArrayList<Solution>();
		for(Solution s : getNeighbors(solution))
			list.add(s.clone());
		return list;
	}
}
