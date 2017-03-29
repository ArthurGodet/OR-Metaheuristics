/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package metaheuristics;

import java.util.ArrayList;
import java.util.List;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Class SimulatedAnnealing.
 */
public class SimulatedAnnealing extends Solver{
	
	/** The start temp. */
	private double startTemp;
	
	/** The sizefactor. */
	private int sizefactor;
	
	/** The tempfactor. */
	private double tempfactor;
	
	/** The nb loops. */
	private int nbLoops; // better with a time limit here !
	
	/** The minpercent. */
	private double minpercent;
	
	/**
	 * Instantiates a new simulated annealing.
	 *
	 * @param inst the inst
	 * @param startTemp the start temp
	 * @param tempfactor the tempfactor
	 * @param sizefactor the sizefactor
	 * @param minpercent the minpercent
	 * @param nbLoops the nb loops
	 */
	public SimulatedAnnealing(Instance inst, double startTemp, double tempfactor, int sizefactor, double minpercent, int nbLoops) {
		super(inst,"Simulated Annealing");
		this.startTemp = startTemp;
		this.tempfactor = tempfactor;
		this.sizefactor = sizefactor;
		this.minpercent = minpercent;
		this.nbLoops = nbLoops;
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve()
	 */
	public void solve() {
		// Initialiser avec une solution
		//Solver solver = new Neh(this.getInstance());
		//solver.solve();
		//Solution s = solver.getSolution();
		Solution s = Solution.generateSolution(this.getInstance());
		this.setSolution(s);
		//this.setSolution(solver.getSolution());
		
		// Initialisation des variables
		double temp = this.startTemp;
		int L = this.sizefactor*this.getInstance().getNbJobs()*(this.getInstance().getNbMachines()-1);
		int nit = 0;
		int count = 0;
		
		// Application de l'algorithme
		while(count<5 && nit<this.nbLoops){
			Solution t = new Solution(this.getInstance());
			boolean change = true;
			List<Solution> neighbors = new ArrayList<Solution>();
			int nbChange = -1;
			for(int i = 0; i<L; i++){
				if(change){
					neighbors = s.generateNeighbors(Neighborhood.SHIFT);
					change = false;
				}
				if(!neighbors.isEmpty())
					t = neighbors.remove((int)(neighbors.size()*Math.random()));
				if(t.getCmax()-s.getCmax() <= 0 || Math.random()<= Math.exp(((double)(t.getCmax()-s.getCmax()))/temp)){
					s = t.clone();
					change = true;
					nbChange++;
				}
			}
			
			temp *= this.tempfactor;
			nit++;
			System.out.println(this.getSolution().getCmax());
			if(s.compareTo(this.getSolution())<0){
				this.setSolution(s.clone());
				count = 0;
			}
			else if((((double)(nbChange))/L)<this.minpercent)
				count++;
		}
	}

}
