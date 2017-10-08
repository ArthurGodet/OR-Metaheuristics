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
import neighborhoods.Change;
import neighborhoods.Shift;
import neighborhoods.Swap;
import util.Timer;

/**
 * Implementation of the VNS (Variable Neighborhood Search) metaheuristic.
 */
public class Vns extends Solver{
	
	/**
	 * Instantiates a new VNS solver.
	 *
	 * @param inst the instance
	 */
	public Vns(Instance inst) {
		super(inst,"VNS");
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		this.setSolution(Greedy.solve(this.getInstance()));
		Neighborhood[] tab = new Neighborhood[] {new Change(), new Shift(), new Swap()};
		
		Solution s = Greedy.solve(this.getInstance());
		LocalSearch ls = new LocalSearch(this.getInstance(),tab[0],s);
		
		int k = 0; // ATTENTION : the order of application affects the final result !
		do{
			if(k==3)
				k=0;
			
			ls.setNeighborhood(tab[k]);
			ls.solve(timer);
			s = ls.getSolution();
			
			k++;
		}while(!timer.isFinished());
		
		this.setSolution(s.clone());
	}

}
