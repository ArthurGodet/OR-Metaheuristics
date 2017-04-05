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
	 * @see metaheuristics.LocalSearch#solve()
	 */
	public void solve() {
		tabuList = new TabuList(1<<16);
		Solution currentSolution = this.getSolution();
		List<Solution> neighbors = this.neighbor.getNeighborsList(currentSolution);
		
		do{
			tabuList.add(currentSolution.clone());
			Collections.sort(neighbors);
			currentSolution = neighbors.get(0);
			if(currentSolution.compareTo(this.getSolution())<0)
				this.setSolution(currentSolution.clone());
			neighbors = this.neighbor.getNeighborsList(currentSolution);
			for(int i = 0; i<neighbors.size(); i++){
				if(tabuList.contains(neighbors.get(i))){
					neighbors.remove(neighbors.get(i));
					i--;
				}
			}
		}while(neighbors.size() != 0);
	}
}
