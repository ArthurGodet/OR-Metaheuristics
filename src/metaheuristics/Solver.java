package metaheuristics;

import definition.Instance;
import definition.Solution;

/**
 * Classe de base des Solvers
 */
public abstract class Solver {
	private Solution solution;
	private Instance instance;
	protected String name;

	public Solver(Instance inst, String name){
		this.instance = inst;
		this.solution = new Solution(this.instance);
		this.name = name;
	}
	
	/** @return Solver method name */
	public String getName(){
		return this.name;
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

	/**
	 * Run the solver
	 */
	public abstract void solve();
}
