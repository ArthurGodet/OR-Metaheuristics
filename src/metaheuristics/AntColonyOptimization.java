/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package metaheuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import definition.Instance;
import definition.Solution;
import neighborhoods.Shift;
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class AntColonyOptimization.
 */
public class AntColonyOptimization extends Solver {
	
	/** The Constant NB_ANTS. */
	public static final int NB_ANTS = 30;
	
	/** The Constant ALPHA. */
	public static final double ALPHA = 5.0;
	
	/** The Constant RHO. */
	public static final double RHO = 0.01;
	
	/** The Constant BETA. */
	public static final double BETA = 0.5;
	
	/** The Constant THO_MIN. */
	public static final double THO_MIN = 0.01;
	
	/** The Constant THO_MAX. */
	public static final double THO_MAX = 6.0;
	
	/** The pheromone trails. */
	private double[][] pheromoneTrails;
	
	/** The ants. */
	private Solution[] ants;
	
	/** The nb ants. */
	private int nbAnts;
	
	/** The alpha. */
	private double alpha;
	
	/** The rho. */
	private double rho;
	
	/** The beta. */
	private double beta;
	
	/** The tho min. */
	private double thoMin;
	
	/** The tho max. */
	private double thoMax;
	
	/**
	 * Instantiates a new ant colony optimization.
	 *
	 * @param inst the inst
	 * @param nbLoops the nb loops
	 */
	public AntColonyOptimization(Instance inst) {
		this(inst,NB_ANTS,ALPHA,RHO,BETA,THO_MIN,THO_MAX);
	}
	
	/**
	 * Instantiates a new ant colony optimization.
	 *
	 * @param inst the inst
	 * @param nbLoops the nb loops
	 * @param nbAnts the nb ants
	 * @param alpha the alpha
	 * @param rho the rho
	 * @param beta the beta
	 * @param thoMin the tho min
	 * @param thoMax the tho max
	 */
	public AntColonyOptimization(Instance inst,int nbAnts, double alpha, double rho, double beta, double thoMin, double thoMax) {
		super(inst, "Ant Colony Optimization");
		this.nbAnts = nbAnts;
		this.alpha = alpha;
		this.rho = rho;
		this.beta = beta;
		this.thoMin = thoMin;
		this.thoMax = thoMax;
	}
	
	/**
	 * Solve.
	 *
	 * @param timer the timer
	 */
	public void solve(Timer timer){

		this.initialisation();
		// Loop for improvements of the solution.
		while(!timer.isFinished()){
			for(int k = 0; k<this.nbAnts; k++){
				this.ants[k] = new Solution(this.getInstance());
				int firstJob = (int)(Math.random()*this.getInstance().getNbJobs());
				this.ants[k].insertJob(firstJob,0);
				List<Integer> candidates = generateCandidates(firstJob);
				int pos = 1;
				while(candidates.size() != 0){
					double[] proba = proba(candidates,pos,k);
					int candidateSelected = draw(proba);
					ants[k].setOrder(candidates.get(candidateSelected),pos);
					candidates.remove(candidateSelected);
					pos++;
				}
			}
			updatePheromoneTrails();
		}
	}
	
	/**
	 * Initialize member variables.
	 */
	public void initialisation(){
		this.ants = new Solution[this.nbAnts];
		
		this.pheromoneTrails = new double[this.getInstance().getNbJobs()][this.getInstance().getNbJobs()];
		for(int i = 0; i<this.getInstance().getNbJobs(); i++)
			for(int j = 0; j<this.getInstance().getNbJobs(); j++)
				this.pheromoneTrails[i][j] = this.thoMax;
		
		this.setSolution(Neh.solve(this.getInstance()));
	}
	
	/**
	 * Generate a list of item indexes that can be added after item j.
	 *
	 * @param jobSelected the job selected
	 * @return the list
	 */
	public List<Integer> generateCandidates(int jobSelected){
		List<Integer> candidates = new ArrayList<Integer>();
		for(int j = 0; j<this.getInstance().getNbJobs(); j++)
			if(j != jobSelected)
				candidates.add(j);
		return candidates;
	}
	
	/**
	 * Return an array of probabilities for each candidate based on pheromone.
	 *
	 * @param candidates the candidates
	 * @param pos the pos
	 * @param k the k
	 * @return the double[]
	 */
	public double[] proba(List<Integer> candidates, int pos, int k){
		double[] proba = new double[candidates.size()];
		double somme = 0.0;
		for(int m = 0; m<candidates.size(); m++){
			proba[m] = Math.pow(pheromoneFactor(candidates.get(m),pos),this.alpha)*Math.pow(heuristicFactor(candidates.get(m),pos,k),this.beta);
			somme += proba[m];
		}
		for(int m = 0; m<candidates.size(); m++)
			proba[m] /= somme;
		return proba;
	}
	
	/**
	 * Pheromone factor.
	 *
	 * @param job the job
	 * @param pos the pos
	 * @return the double
	 */
	public double pheromoneFactor(int job, int pos){
		return this.pheromoneTrails[job][pos];
	}
	
	/**
	 * Heuristic factor.
	 *
	 * @param job the job
	 * @param pos the pos
	 * @param k the k
	 * @return the double
	 */
	public double heuristicFactor(int job, int pos, int k){
		this.ants[k].setOrder(job, pos);
		this.ants[k].evaluate();
		int Cmax = this.ants[k].getCmax();
		this.ants[k].setOrder(-1,pos);
		this.ants[k].evaluate();
		
		return 1/((double)(Cmax-this.ants[k].getCmax()));
	}
	
	/**
	 * Draw.
	 *
	 * @param proba the proba
	 * @return the int
	 */
	// Tire un élément au hasard en prenant en compte les proba des pheromones/heuristic
	public int draw(double[] proba){
		double prob = Math.random();
		int num = 0;
		double pro = 0.0;
		while((pro+proba[num])<prob){
			pro += proba[num];
			num++;
		}
		return (num == proba.length ? num-1 : num);
	}
	
	/**
	 * Update pheromone trails.
	 */
	public void updatePheromoneTrails(){
		LocalSearch ls = new LocalSearch(this.getInstance(),new Shift(),this.getSolution());
		for(int k = 0; k<this.nbAnts; k++){
			ls.setSolution(this.ants[k]);
			ls.solve();
			this.ants[k] = ls.getSolution().clone();
		}
		Arrays.sort(ants,Collections.reverseOrder());
		if(ants[ants.length-1].compareTo(this.getSolution())<0){
			this.setSolution(ants[ants.length-1].clone());
		}
		for(int i = 0; i<this.getInstance().getNbJobs(); i++){
			for(int j = 0; j<this.getInstance().getNbJobs(); j++){
				this.pheromoneTrails[i][j] *= (1-this.rho);
				if(ants[ants.length-1].contains(i)&&ants[ants.length-1].getIndex(i)==j)
					this.pheromoneTrails[i][j] += this.rho/((double)(ants[ants.length-1].getCmax()));
				if(this.pheromoneTrails[i][j]>this.thoMax)
					this.pheromoneTrails[i][j] = this.thoMax;
				if(this.pheromoneTrails[i][j] < this.thoMin)
					this.pheromoneTrails[i][j] = this.thoMin;
			}
		}
	}

	/**
	 * Gets the nb ants.
	 *
	 * @return the nb ants
	 */
	public int getNbAnts() {
		return nbAnts;
	}

	/**
	 * Sets the nb ants.
	 *
	 * @param nbAnts the new nb ants
	 */
	public void setNbAnts(int nbAnts) {
		this.nbAnts = nbAnts;
	}

	/**
	 * Gets the alpha.
	 *
	 * @return the alpha
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Sets the alpha.
	 *
	 * @param alpha the new alpha
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * Gets the rho.
	 *
	 * @return the rho
	 */
	public double getRho() {
		return rho;
	}

	/**
	 * Sets the rho.
	 *
	 * @param rho the new rho
	 */
	public void setRho(double rho) {
		this.rho = rho;
	}

	/**
	 * Gets the beta.
	 *
	 * @return the beta
	 */
	public double getBeta() {
		return beta;
	}

	/**
	 * Sets the beta.
	 *
	 * @param beta the new beta
	 */
	public void setBeta(double beta) {
		this.beta = beta;
	}

	/**
	 * Gets the tho min.
	 *
	 * @return the tho min
	 */
	public double getThoMin() {
		return thoMin;
	}

	/**
	 * Sets the tho min.
	 *
	 * @param thoMin the new tho min
	 */
	public void setThoMin(double thoMin) {
		this.thoMin = thoMin;
	}

	/**
	 * Gets the tho max.
	 *
	 * @return the tho max
	 */
	public double getThoMax() {
		return thoMax;
	}

	/**
	 * Sets the tho max.
	 *
	 * @param thoMax the new tho max
	 */
	public void setThoMax(double thoMax) {
		this.thoMax = thoMax;
	}
}
