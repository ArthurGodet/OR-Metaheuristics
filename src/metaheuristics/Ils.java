/**
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package metaheuristics;

import definition.Instance;
import definition.Solution;
import neighborhoods.Shift;
import util.Random;
import util.Timer;

/**
 * Implementation of the ILS (Iterated Local Search) metaheuristic.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class Ils extends Solver{
	
	/**
	 * Instantiates a new ILS solver.
	 *
	 * @param inst the instance
	 */
	public Ils(Instance inst) {
		super(inst,"ILS");
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		LocalSearch ls = new LocalSearch(this.getInstance(),new Shift(),Greedy.solve(this.getInstance()));
		ls.solve();
		Solution s = ls.getSolution();
		this.setSolution(s.clone());
		// until the timer is finished, does a large step and applies a local search
		do{
			largeStep(s);
			
			ls.setSolution(s);
			ls.solve();
			s = ls.getSolution().clone();
			
			if(s.compareTo(this.getSolution())<0)
				this.setSolution(s.clone()); // keeps the best solution found so far
		}while(!timer.isFinished());
	}
	
	/**
	 * Does a large step. Here, cuts the solution's scheduling in two parts and reverses them.
	 *
	 * @param s the solution
	 */
	public void largeStep(Solution s){
		int n = this.getInstance().getSize();
		int pos = Random.randomInteger(1, n-1);
		s.reverse(0, pos);
		s.reverse(pos, n);
		s.reverse(0, n);
		s.evaluate();
	}
}
