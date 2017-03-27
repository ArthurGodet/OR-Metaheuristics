package metaheuristics;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;

public class LocalSearch extends Solver{
	private Neighborhood neighbor;
	
	public LocalSearch(Instance inst, Neighborhood n, Solution s) {
		super(inst);
		this.neighbor = n;
		this.setSolution(s);
	}

	public void solve() {
		Solution s = this.getSolution().clone();
	}

}
