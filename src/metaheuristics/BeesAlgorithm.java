/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package metaheuristics;

import java.util.ArrayList;
import java.util.Arrays;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;
import neighborhoods.Change;
import neighborhoods.Shift;
import neighborhoods.Swap;
import util.Timer;

/**
 * Implementation of the Bees Colony metaheuristic.
 */
public class BeesAlgorithm extends Solver{
	
	/** The Constant COLONY_SIZE. */
	public static int COLONY_SIZE = 50;
	
	/** The Constant NB_FORAGERS. */
	public static int NB_FORAGERS = 20; // < COLONY_SIZE/2
	
	/** The colony's size. */
	private int colonySize;
	
	/** The number of foragers. */
	private int nbForagers;
	
	/** The bees. */
	private Solution[] bees;
	
	/** The abandoned sites. */
	private ArrayList<Solution> abandonedSites;
	
	/**
	 * Instantiates a new bees algorithm.
	 *
	 * @param inst the instance
	 * @param colonySize the colony size
	 * @param nbForagers the number of foragers
	 */
	public BeesAlgorithm(Instance inst, int colonySize, int nbForagers) {
		super(inst,"Bees algorithm");
		this.colonySize = colonySize;
		this.nbForagers = nbForagers;
	}
	
	/**
	 * Instantiates a new bees algorithm.
	 *
	 * @param inst the instance
	 */
	public BeesAlgorithm(Instance inst){
		this(inst,COLONY_SIZE,NB_FORAGERS);
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	@Override
	public void solve(Timer timer) {
		this.setSolution(Greedy.solve(this.getInstance()));
		// Initiates the colony of bees
		this.initiation();
		Neighborhood[] neighborhoods = new Neighborhood[]{new Change(),new Shift(),new Swap()};
		Arrays.sort(this.bees);
		// Work of the colony : exploration and local foraging
		while(!timer.isFinished()){
			for(int i = 0; i<this.colonySize; i++){
				Solution s = this.bees[i].clone();
				if(i<this.nbForagers){
					// Foraging : selects the best neighbor whatever the neighborhood
					for(Neighborhood n : neighborhoods){
						Solution t = n.getBestNeighbor(this.bees[i]);
						if(!this.abandonedSites.contains(t))
							this.abandonedSites.add(t.clone());
						if(t.compareTo(s)<0)
							s = t.clone();
					}
					
					// Marks the site to avoid future foraging here
					if(!this.abandonedSites.contains(this.bees[i]))
						this.abandonedSites.add(this.bees[i]);
					
					if(s.compareTo(this.bees[i])<0)
						this.bees[i] = s.clone();
					else
						this.bees[i] = Solution.generateSolution(this.getInstance()); // exploration
				}
				else{
					// exploration for all the non-forager bees
					do{
						this.bees[i] = Solution.generateSolution(this.getInstance());
					}while(this.abandonedSites.contains(this.bees[i]));
				}
			}
			Arrays.sort(this.bees);
			if(this.bees[0].compareTo(this.getSolution())<0)
				this.setSolution(this.bees[0].clone()); // keeps the best solution found so far
		}
	}
	
	/**
	 * Initiation.
	 */
	private void initiation(){
		this.abandonedSites = new ArrayList<Solution>();
		this.bees = new Solution[this.colonySize];
		for(int i = 0; i<this.colonySize; i++){
			this.bees[i] = Solution.generateSolution(this.getInstance());
		}
	}

	/**
	 * Gets the colony's size.
	 *
	 * @return the colony's size
	 */
	public int getColonySize() {
		return colonySize;
	}

	/**
	 * Sets the colony's size.
	 *
	 * @param colonySize the new size of the colony
	 */
	public void setColonySize(int colonySize) {
		this.colonySize = colonySize;
	}

	/**
	 * Gets the number of foragers.
	 *
	 * @return the number of foragers
	 */
	public int getNbForagers() {
		return nbForagers;
	}

	/**
	 * Sets the number of foragers.
	 *
	 * @param nbForagers the new number of foragers
	 */
	public void setNbForagers(int nbForagers) {
		this.nbForagers = nbForagers;
	}

	/**
	 * Gets the bees.
	 *
	 * @return the bees
	 */
	public Solution[] getBees() {
		return bees;
	}

	/**
	 * Sets the bees.
	 *
	 * @param bees the new bees
	 */
	public void setBees(Solution[] bees) {
		this.bees = bees;
	}
}
