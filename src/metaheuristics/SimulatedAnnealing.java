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
import neighborhoods.Shift;

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
		Solution s = new Solution(this.getInstance());
		if(this.getSolution().getJob(0)==-1){
			Solver solver = new Neh(this.getInstance());
			solver.solve();
			s = solver.getSolution();
			this.setSolution(s);
		}
		else{
			s = this.getSolution().clone();
		}

		// Initialisation des variables
		double temp = this.startTemp;
		int nit = 0;

		// Application de l'algorithme
		int count = 0;
		int L = this.sizefactor*this.getInstance().getNbJobs()*(this.getInstance().getNbMachines()-1);
		while(count<5 && nit<this.nbLoops){
			Solution t = new Solution(this.getInstance());
			boolean change = true;
			Neighborhood neighborhood = new Shift();
			List<Solution> neighbors = new ArrayList<Solution>();
			int nbChange = -1;
			for(int i = 0; i<L; i++){
				if(change){
					neighbors = neighborhood.getNeighborsList(s);
					change = false;
				}
				if(!neighbors.isEmpty())
					t = neighbors.remove((int)(neighbors.size()*Math.random()));
				else{
					s = t.clone();
					change = true;
				}
				if(t.getCmax()-s.getCmax() <= 0 || Math.random()<= Math.exp(-((double)(t.getCmax()-s.getCmax()))/temp)){
					s = t.clone();
					change = true;
					nbChange++;
				}
			}

			LocalSearch ls = new LocalSearch(this.getInstance(),new Shift(), s);
			ls.solve();
			s = ls.getSolution().clone();

			temp *= this.tempfactor;
			nit++;
			if(s.compareTo(this.getSolution())<0){
				this.setSolution(s.clone());
				count = 0;
			}
			else if((((double)(nbChange))/L)<this.minpercent)
				count++;
		}
	}

}
