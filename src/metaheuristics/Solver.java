package metaheuristics;

import definition.Instance;
import definition.Solution;

public class Solver {
	private Solution solution;
	private Instance instance;
	protected String nameOfMethod;

	public Solver(Instance inst){
		this.instance = inst;
		this.solution = new Solution(this.instance);
		this.nameOfMethod = "Order";
	}
	
	public String getNameOfMethod(){
		return this.nameOfMethod;
	}

	/** @return the problem Solution */
	public Solution getSolution() {
		return this.solution;
	}

	/** @return problem data */
	public Instance getInstance() {
		return this.instance;
	}

	/**
	 * Initializes the problem solution with a new Solution object (the old one
	 * will be deleted).
	 * 
	 * @param sol : new solution
	 */
	public void setSolution(Solution sol) {
		this.solution = sol;
	}

	/**
	 * Sets the problem data
	 * 
	 * @param inst : the Instance object which contains the data.
	 */
	public void setInstance(Instance inst) {
		this.instance = inst;
	}

	// Met les jobs dans l'ordre, par defaut
	public void solve(){
		for(int j = 0; j<this.instance.getNbJobs(); j++)
			this.solution.setOrder(j,j);
		this.solution.evaluate();
	}
}
