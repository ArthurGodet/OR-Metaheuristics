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

import definition.Instance;
import definition.Solution;
import neighborhoods.Change;
import neighborhoods.Shift;
import neighborhoods.Swap;
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class BeesAlgorithm.
 */
public class BeesAlgorithm extends Solver{
	
	/** The colony size. */
	public static int COLONY_SIZE = 50;
	
	/** The nb foragers. */
	public static int NB_FORAGERS = 20; // < COLONY_SIZE/2
	
	/** The colony size. */
	private int colonySize;
	
	/** The nb foragers. */
	private int nbForagers;
	
	/** The bees. */
	private Solution[] bees;
	
	/** The abandonned sites. */
	private ArrayList<Solution> abandonnedSites;
	
	/**
	 * Instantiates a new bees algorithm.
	 *
	 * @param inst the inst
	 * @param colonySize the colony size
	 * @param nbForagers the nb foragers
	 */
	public BeesAlgorithm(Instance inst, int colonySize, int nbForagers) {
		super(inst,"Bees algorithm");
		this.colonySize = colonySize;
		this.nbForagers = nbForagers;
	}
	
	/**
	 * Instantiates a new bees algorithm.
	 *
	 * @param inst the inst
	 */
	public BeesAlgorithm(Instance inst){
		this(inst,COLONY_SIZE,NB_FORAGERS);
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	@Override
	public void solve(Timer timer) {
		this.setSolution(Neh.solve(this.getInstance()));
		this.initialisation();
		Change change = new Change();
		Shift shift = new Shift();
		Swap swap = new Swap();
		Solution s;
		Arrays.sort(this.bees);
		while(!timer.isFinished()){
			for(int i = 0; i<this.colonySize; i++){
				if(i<this.nbForagers){
					// Select the best neighbor wherever the neighborhood
					s = change.getBestNeighbor(this.bees[i]);
					if(!this.abandonnedSites.contains(s))
						this.abandonnedSites.add(s.clone());
					Solution t = shift.getBestNeighbor(this.bees[i]);
					if(!this.abandonnedSites.contains(t))
						this.abandonnedSites.add(t.clone());
					if(t.compareTo(s)<0)
						s = t.clone();
					t = swap.getBestNeighbor(this.bees[i]);
					if(!this.abandonnedSites.contains(t))
						this.abandonnedSites.add(t.clone());
					if(t.compareTo(s)<0)
						s = t.clone();
					
					// Check if the site should be abandonned
					if(!this.abandonnedSites.contains(this.bees[i]))
						this.abandonnedSites.add(this.bees[i]);
					
					if(s.compareTo(this.bees[i])<0)
						this.bees[i] = s.clone();
					else
						this.bees[i] = Solution.generateSolution(this.getInstance());
				}
				else{
					do{
						this.bees[i] = Solution.generateSolution(this.getInstance());
					}while(this.abandonnedSites.contains(this.bees[i]));
				}
			}
			Arrays.sort(this.bees);
			if(this.bees[0].compareTo(this.getSolution())<0)
				this.setSolution(this.bees[0].clone());
		}
	}
	
	/**
	 * Initialisation.
	 */
	private void initialisation(){
		this.abandonnedSites = new ArrayList<Solution>();
		this.bees = new Solution[this.colonySize];
		for(int i = 0; i<this.colonySize; i++){
			this.bees[i] = Solution.generateSolution(this.getInstance());
		}
	}

	/**
	 * Gets the colony size.
	 *
	 * @return the colony size
	 */
	public int getColonySize() {
		return colonySize;
	}

	/**
	 * Sets the colony size.
	 *
	 * @param colonySize the new colony size
	 */
	public void setColonySize(int colonySize) {
		this.colonySize = colonySize;
	}

	/**
	 * Gets the nb foragers.
	 *
	 * @return the nb foragers
	 */
	public int getNbForagers() {
		return nbForagers;
	}

	/**
	 * Sets the nb foragers.
	 *
	 * @param nbForagers the new nb foragers
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
