package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;

public class TabuSearch extends LocalSearch{

	public TabuSearch(Instance inst, Neighborhood nh, Solution s) {
		super(inst, nh, s);
		this.setName("Tabu Search");
	}

	public void solve() {
		List<Solution> tabuList = new ArrayList<Solution>();
		List<Solution> neighbors = this.getSolution().generateNeighbors(this.neighbor);
		Solution currentSolution = this.getSolution();
		
		do{
			tabuList.add(currentSolution.clone());
			Collections.sort(neighbors);
			currentSolution = neighbors.get(0);
			neighbors = currentSolution.generateNeighbors(this.neighbor);
			for(Solution s : neighbors){
				if(tabuList.contains(s))
					neighbors.remove(s);
			}
		}while(neighbors.size() != 0);
	}

}
