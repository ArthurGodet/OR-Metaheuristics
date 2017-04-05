/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package metaheuristics;

import definition.Instance;
import definition.Solution;
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * Classe de base des Solvers.
 */
public abstract class Solver {
	
	/** The solution. */
	private Solution solution;
	
	/** The instance. */
	private Instance instance;
	
	/** The name. */
	protected String name;

	/**
	 * Instantiates a new solver.
	 *
	 * @param inst the inst
	 * @param name the name
	 */
	public Solver(Instance inst, String name){
		this.instance = inst;
		this.solution = new Solution(this.instance);
		this.name = name;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return Solver method name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Sets a new name for the Solver.
	 *
	 * @param name : new name
	 * @return the string
	 */
	public String setName(String name){
		return this.name = name;
	}

	/**
	 * Gets the solution.
	 *
	 * @return the problem Solution
	 */
	public Solution getSolution() {
		return this.solution;
	}

	/**
	 * Gets the single instance of Solver.
	 *
	 * @return problem data
	 */
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
	 * Sets the problem data.
	 *
	 * @param inst : the Instance object which contains the data.
	 */
	public void setInstance(Instance inst) {
		this.instance = inst;
	}

	/**
	 * Run the solver.
	 * @param timer the timer
	 */
	public abstract void solve(Timer timer);

	/**
	 * Run the solver.
	 */
	public void solve() {
		solve(new Timer(Long.MAX_VALUE));
	}
	
	/**
	 * Return a String with the name of the solver and its solution.
	 *
	 * @return the string
	 */
	public String toString(){
		String s = this.getName() + "\n";
		s += this.getSolution().toString();
		return s;
	}
}
