/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package metaheuristics;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;
import util.Timer;

/**
 * Implementation of a Local Search algorithm.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class LocalSearch extends Solver{
	
	/** The type of neighborhood. */
	protected Neighborhood neighborhood;
	
	/**
	 * Instantiates a new Local Search solver based on a type of neighborhood and an initial solution.
	 *
	 * @param inst the instance
	 * @param n the neighborhood to explore
	 * @param s the initial solution
	 */
	public LocalSearch(Instance inst, Neighborhood n, Solution s) {
		super(inst,"Local Search");
		this.neighborhood = n;
		this.setSolution(s);
	}
	
	/**
	 * Sets the neighborhood.
	 *
	 * @param nh the new neighborhood
	 */
	public void setNeighborhood(Neighborhood nh){
		this.neighborhood = nh;
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		double cmax = this.getSolution().getScore();
		do{
			cmax = this.getSolution().getScore();
			this.setSolution(this.neighborhood.getBestNeighbor(this.getSolution()));
		}while(cmax>this.getSolution().getScore() && !timer.isFinished());
	}
}
