/**
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
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

/**
 * Implementation of the Ant Colony Optimization metaheuristic.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
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
	
	/** The number of ants. */
	private int nbAnts;
	
	/** The alpha parameter. */
	private double alpha;
	
	/** The rho parameter. */
	private double rho;
	
	/** The beta parameter. */
	private double beta;
	
	/** The minimum accepted value for tho. */
	private double thoMin;
	
	/** The maximum accepted value for tho. */
	private double thoMax;
	
	/**
	 * Instantiates a new ant colony optimization solver.
	 *
	 * @param inst the instance
	 */
	public AntColonyOptimization(Instance inst) {
		this(inst,NB_ANTS,ALPHA,RHO,BETA,THO_MIN,THO_MAX);
	}
	
	/**
	 * Instantiates a new ant colony optimization solver.
	 *
	 * @param inst the instance
	 * @param nbAnts the number of ants
	 * @param alpha the alpha parameter
	 * @param rho the rho parameter
	 * @param beta the beta parameter
	 * @param thoMin the minimum accepted value for tho
	 * @param thoMax the maximum accepted value for tho
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
	
	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer){
		this.initiation();
		// Loop for improvements of the solution.
		while(!timer.isFinished()){
			for(int k = 0; k<this.nbAnts; k++){
				this.ants[k] = new Solution(this.getInstance());
				int firstJob = (int)(Math.random()*this.getInstance().getSize());
				this.ants[k].insertJob(firstJob,0);
				List<Integer> candidates = generateCandidates(firstJob);
				int pos = 1;
				while(candidates.size() != 0){
					double[] proba = proba(candidates,pos,k);
					int candidateSelected = draw(proba);
					ants[k].setScheduling(candidates.get(candidateSelected),pos);
					candidates.remove(candidateSelected);
					pos++;
				}
				ants[k].evaluate();
			}
			updatePheromoneTrails();
		}
	}
	
	/**
	 * Initializes the pheromone trail.
	 */
	public void initiation(){
		this.ants = new Solution[this.nbAnts];
		
		this.pheromoneTrails = new double[this.getInstance().getSize()][this.getInstance().getSize()];
		for(int i = 0; i<this.getInstance().getSize(); i++)
			for(int j = 0; j<this.getInstance().getSize(); j++)
				this.pheromoneTrails[i][j] = this.thoMax;
		
		this.setSolution(Greedy.solve(this.getInstance()));
	}
	
	/**
	 * Generates the candidates corresponding to the selected job/city.
	 *
	 * @param jobSelected the selected job/city
	 * @return the list of candidates
	 */
	public List<Integer> generateCandidates(int jobSelected){
		List<Integer> candidates = new ArrayList<Integer>();
		for(int j = 0; j<this.getInstance().getSize(); j++)
			if(j != jobSelected)
				candidates.add(j);
		return candidates;
	}
	
	/**
	 * Returns the probabilities associated to each candidate.
	 *
	 * @param candidates the candidates
	 * @param pos the position
	 * @param k the number of the ant
	 * @return the probabilities
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
	 * Gets the pheromone factor.
	 *
	 * @param job the job
	 * @param pos the position
	 * @return the pheromone factor
	 */
	public double pheromoneFactor(int job, int pos){
		return this.pheromoneTrails[job][pos];
	}
	
	/**
	 * Computes the heuristic factor.
	 *
	 * @param job the job
	 * @param pos the position
	 * @param k the number of the ant
	 * @return the heuristic factor
	 */
	public double heuristicFactor(int job, int pos, int k){
		this.ants[k].setScheduling(job, pos);
		this.ants[k].evaluate();
		double Cmax = this.ants[k].getScore();
		this.ants[k].setScheduling(-1,pos);
		this.ants[k].evaluate();
		
		return 1/((double)(Cmax-this.ants[k].getScore()));
	}
	
	/**
	 * Draws a random element, the pheromones and heuristic factors influencing the result.
	 *
	 * @param proba the probabilities
	 * @return the id of the element
	 */
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
		for(int i = 0; i<this.getInstance().getSize(); i++){
			for(int j = 0; j<this.getInstance().getSize(); j++){
				this.pheromoneTrails[i][j] *= (1-this.rho);
				if(ants[ants.length-1].contains(i)&&ants[ants.length-1].getIndex(i)==j)
					this.pheromoneTrails[i][j] += this.rho/((double)(ants[ants.length-1].getScore()));
				if(this.pheromoneTrails[i][j]>this.thoMax)
					this.pheromoneTrails[i][j] = this.thoMax;
				if(this.pheromoneTrails[i][j] < this.thoMin)
					this.pheromoneTrails[i][j] = this.thoMin;
			}
		}
	}

	/**
	 * Gets the number of ants.
	 *
	 * @return the number of ants
	 */
	public int getNbAnts() {
		return nbAnts;
	}

	/**
	 * Sets the number of ants.
	 *
	 * @param nbAnts the new number of ants
	 */
	public void setNbAnts(int nbAnts) {
		this.nbAnts = nbAnts;
	}

	/**
	 * Gets the alpha parameter.
	 *
	 * @return the alpha parameter
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Sets the alpha parameter.
	 *
	 * @param alpha the new alpha parameter
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * Gets the rho parameter.
	 *
	 * @return the rho parameter
	 */
	public double getRho() {
		return rho;
	}

	/**
	 * Sets the rho parameter.
	 *
	 * @param rho the new rho parameter
	 */
	public void setRho(double rho) {
		this.rho = rho;
	}

	/**
	 * Gets the beta parameter.
	 *
	 * @return the beta parameter
	 */
	public double getBeta() {
		return beta;
	}

	/**
	 * Sets the beta parameter.
	 *
	 * @param beta the new beta parameter
	 */
	public void setBeta(double beta) {
		this.beta = beta;
	}

	/**
	 * Gets the minimum accepted value for tho.
	 *
	 * @return the minimum accepted value for tho
	 */
	public double getThoMin() {
		return thoMin;
	}

	/**
	 * Sets the minimum accepted value for tho.
	 *
	 * @param thoMin the new minimum accepted value for tho
	 */
	public void setThoMin(double thoMin) {
		this.thoMin = thoMin;
	}

	/**
	 * Gets the maximum accepted value for tho.
	 *
	 * @return the maximum accepted value for tho
	 */
	public double getThoMax() {
		return thoMax;
	}

	/**
	 * Sets the maximum accepted value for tho.
	 *
	 * @param thoMax the new maximum accepted value for tho
	 */
	public void setThoMax(double thoMax) {
		this.thoMax = thoMax;
	}
}
