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
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class SimulatedAnnealing.
 */
public class SimulatedAnnealing extends Solver{
	public static double START_TEMP = 25.0;
	public static double TEMPFACTOR = 0.99;  // doit être proche de 1
	public static int SIZEFACTOR = 1;  // n'apporte pas grand chose
	public static double MIN_PERCENT = 0.1; // doit être petit
	
	/** The start temp. */
	private double startTemp;

	/** The sizefactor. */
	private int sizefactor;

	/** The tempfactor. */
	private double tempfactor;

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
	public SimulatedAnnealing(Instance inst, double startTemp, double tempfactor, int sizefactor, double minpercent) {
		super(inst,"Simulated Annealing");
		this.startTemp = startTemp;
		this.tempfactor = tempfactor;
		this.sizefactor = sizefactor;
		this.minpercent = minpercent;
	}
	
	/**
	 * Instantiates a new simulated annealing.
	 *
	 * @param inst the inst
	 * @param nbLoops the nb loops
	 */
	public SimulatedAnnealing(Instance inst) {
		this(inst,START_TEMP,TEMPFACTOR,SIZEFACTOR,MIN_PERCENT);
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		this.setSolution(Neh.solve(this.getInstance()).clone());
		// Initialiser avec une solution
		Solution s = Neh.solve(this.getInstance());

		// Initialisation des variables
		double temp = this.startTemp;

		// Application de l'algorithme
		int count = 0;
		int L = this.sizefactor*this.getInstance().getNbJobs()*(this.getInstance().getNbMachines()-1);
		while(count<5 && !timer.isFinished()){
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
			if(s.compareTo(this.getSolution())<0){
				this.setSolution(s.clone());
				count = 0;
			}
			else if((((double)(nbChange))/L)<this.minpercent)
				count++;
		}
	}

}
