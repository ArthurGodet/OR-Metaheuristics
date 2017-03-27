package metaheuristics;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;

public class LocalSearch extends Solver{
	private Neighborhood neighbor;
	
	public LocalSearch(Instance inst, Neighborhood n, Solution s) {
		super(inst,"Local Search");
		this.neighbor = n;
		this.setSolution(s);
	}

	public void solve() {
		int cmax = this.getSolution().getCmax();
		do{
			cmax = this.getSolution().getCmax();
			this.setSolution(this.getSolution().generateBestNeighbor(this.neighbor));
		}while(cmax>this.getSolution().getCmax());
	}

}
