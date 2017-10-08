/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package metaheuristics;

import java.util.ArrayList;
import java.util.List;

import definition.Instance;
import definition.InstanceFlowshop;
import definition.Neighborhood;
import definition.Solution;
import neighborhoods.Shift;
import util.Timer;

/**
 * Implementation of the Simulated Annealing metaheuristic.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class SimulatedAnnealing extends Solver{
	
	/** The Constant START_TEMP. */
	public static double START_TEMP = 25.0;
	
	/** The Constant TEMPFACTOR. */
	public static double TEMPFACTOR = 0.99;
	
	/** The Constant SIZEFACTOR. */
	public static int SIZEFACTOR = 1;
	
	/** The Constant MIN_PERCENT. */
	public static double MIN_PERCENT = 0.1;
	
	/** The starting temperature. */
	private double startTemp;

	/** The size factor. */
	private int sizefactor;

	/** The temperature factor. */
	private double tempfactor;

	/** The minimum accepted percent. */
	private double minpercent;

	/**
	 * Instantiates a new Simulated Annealing solver.
	 *
	 * @param inst the instance
	 * @param startTemp the starting temperature
	 * @param tempfactor the temperature factor
	 * @param sizefactor the size factor
	 * @param minpercent the minimum accepted percent
	 */
	public SimulatedAnnealing(Instance inst, double startTemp, double tempfactor, int sizefactor, double minpercent) {
		super(inst,"Simulated Annealing");
		this.startTemp = startTemp;
		this.tempfactor = tempfactor;
		this.sizefactor = sizefactor;
		this.minpercent = minpercent;
	}
	
	/**
	 * Instantiates a new Simulated Annealing solver.
	 *
	 * @param inst the instance
	 */
	public SimulatedAnnealing(Instance inst) {
		this(inst,START_TEMP,TEMPFACTOR,SIZEFACTOR,MIN_PERCENT);
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		this.setSolution(Greedy.solve(this.getInstance()).clone());
		// Initiates a new solution
		Solution s = Greedy.solve(this.getInstance());

		// Initiates the variables
		double temp = this.startTemp;

		// Applies the Simulated Annealing core algorithm
		int count = 0;
		int a = (this.getInstance() instanceof InstanceFlowshop ? ((InstanceFlowshop)this.getInstance()).getNbMachines() : this.getInstance().getSize());
		int L = this.sizefactor*this.getInstance().getSize()*(a-1);
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
				if(t.getScore()-s.getScore() <= 0 || Math.random()<= Math.exp(-((double)(t.getScore()-s.getScore()))/temp)){
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
