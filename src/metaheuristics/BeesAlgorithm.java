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

public class BeesAlgorithm extends Solver{
	public static int COLONY_SIZE = 50;
	public static int NB_FORAGERS = 20; // < COLONY_SIZE/2
	
	private int nbLoops;
	private int colonySize;
	private int nbForagers;
	private Solution[] bees;
	private ArrayList<Solution> abandonnedSites;
	
	public BeesAlgorithm(Instance inst, int colonySize, int nbForagers, int nbLoops) {
		super(inst,"Bees algorithm");
		this.colonySize = colonySize;
		this.nbForagers = nbForagers;
		this.nbLoops = nbLoops;
	}
	
	public BeesAlgorithm(Instance inst, int nbLoops){
		this(inst,COLONY_SIZE,NB_FORAGERS,nbLoops);
	}

	@Override
	public void solve(Timer timer) {
		this.initialisation();
		Change change = new Change();
		Shift shift = new Shift();
		Swap swap = new Swap();
		Solution s;
		Arrays.sort(this.bees);
		for(int k = 0; k<this.nbLoops && !timer.isFinished(); k++){
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
	
	private void initialisation(){
		this.abandonnedSites = new ArrayList<Solution>();
		this.bees = new Solution[this.colonySize];
		for(int i = 0; i<this.colonySize; i++){
			this.bees[i] = Solution.generateSolution(this.getInstance());
		}
	}

	public int getNbLoops() {
		return nbLoops;
	}

	public void setNbLoops(int nbLoops) {
		this.nbLoops = nbLoops;
	}

	public int getColonySize() {
		return colonySize;
	}

	public void setColonySize(int colonySize) {
		this.colonySize = colonySize;
	}

	public int getNbForagers() {
		return nbForagers;
	}

	public void setNbForagers(int nbForagers) {
		this.nbForagers = nbForagers;
	}

	public Solution[] getBees() {
		return bees;
	}

	public void setBees(Solution[] bees) {
		this.bees = bees;
	}
}
