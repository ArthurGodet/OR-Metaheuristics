/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package metaheuristics;

import java.util.Collections;
import java.util.List;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;
import definition.TabuList;
import util.Timer;

/**
 * Implementation of the Tabu Search metaheuristic.
 */
public class TabuSearch extends LocalSearch{

	/** The tabu list. */
	private TabuList tabuList;

	/**
	 * Instantiates a new Tabu Search solver.
	 *
	 * @param inst the instance
	 * @param nh the type of neighborhood
	 * @param s the s
	 */
	public TabuSearch(Instance inst, Neighborhood nh, Solution s) {
		super(inst, nh, s);
		this.setName("Tabu Search");
		this.tabuList = new TabuList(1<<16);
	}

	/* (non-Javadoc)
	 * @see metaheuristics.LocalSearch#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		this.setSolution(Greedy.solve(this.getInstance()));
		tabuList = new TabuList(1<<16);
		Solution currentSolution = this.getSolution();

		while(!timer.isFinished()) {
			tabuList.add(currentSolution.clone());

			Solution nextSolution = null;
			for(Solution sol : neighborhood.getNeighbors(currentSolution))
				if(!tabuList.contains(sol) && (nextSolution == null || sol.compareTo(nextSolution) < 0))
					nextSolution = sol.clone();

			if(nextSolution == null)
				break;
			else
				currentSolution = nextSolution;

			if(currentSolution.compareTo(this.getSolution())<0)
				this.setSolution(currentSolution.clone());
		}
	}
}
