package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;
import definition.TabuList;

public class TabuSearch extends LocalSearch{
	private TabuList tabuList;
	
	public TabuSearch(Instance inst, Neighborhood nh, Solution s) {
		super(inst, nh, s);
		this.setName("Tabu Search");
		this.tabuList = new TabuList(1<<16);
	}

	public void solve() {
		tabuList = new TabuList(1<<16);
		Solution currentSolution = this.getSolution();
		List<Solution> neighbors = this.getSolution().generateNeighbors(this.neighbor);
		
		do{
			tabuList.add(currentSolution.clone());
			Collections.sort(neighbors);
			currentSolution = neighbors.get(0);
			if(currentSolution.compareTo(this.getSolution())<0)
				this.setSolution(currentSolution.clone());
			neighbors = currentSolution.generateNeighbors(this.neighbor);
			for(int i = 0; i<neighbors.size(); i++){
				if(tabuList.contains(neighbors.get(i))){
					neighbors.remove(neighbors.get(i));
					i--;
				}
			}
		}while(neighbors.size() != 0);
	}
}
