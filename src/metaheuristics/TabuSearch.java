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

// TODO: Auto-generated Javadoc
/**
 * The Class TabuSearch.
 */
public class TabuSearch extends LocalSearch{

	/** The tabu list. */
	private TabuList tabuList;

	/**
	 * Instantiates a new tabu search.
	 *
	 * @param inst the inst
	 * @param nh the nh
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
		this.setSolution(Neh.solve(this.getInstance()));
		tabuList = new TabuList(1<<16);
		Solution currentSolution = this.getSolution();

		while(!timer.isFinished()) {
			tabuList.add(currentSolution.clone());

			Solution nextSolution = null;
			for(Solution sol : neighbor.getNeighbors(currentSolution))
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
