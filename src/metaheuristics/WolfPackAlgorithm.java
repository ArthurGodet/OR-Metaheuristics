package metaheuristics;

import java.util.Arrays;
import java.util.List;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;
import neighborhoods.Change;
import neighborhoods.Shift;
import neighborhoods.Swap;
import util.Random;
import util.Timer;

public class WolfPackAlgorithm extends Solver{
	public static int PACK_SIZE = 10;
	public static double COMMUNICATION_RATE = 0.4; // < 0.5
	
	private int packSize;
	private Solution[] wolves;
	private double communicationRate;

	public WolfPackAlgorithm(Instance inst, int packSize, double communicationRate) {
		super(inst,"Wolf Pack Algorithm");
		this.packSize = packSize;
		this.communicationRate = communicationRate;
	}
	
	public WolfPackAlgorithm(Instance inst) {
		this(inst,PACK_SIZE,COMMUNICATION_RATE);
	}
	
	private void initialisation(){
		this.wolves = new Solution[this.packSize];
		for(int i = 0; i<this.packSize; i++)
			this.wolves[i] = this.localHunt(Solution.generateSolution(this.getInstance()));
	}
	
	private Solution localHunt(Solution s){
		Solution minSol = new Solution(this.getInstance());
		LocalSearch ls = new LocalSearch(this.getInstance(), null, s.clone());
		for(Neighborhood n : new Neighborhood[]{new Shift(), new Change(), new Swap()}) {
			ls.setSolution(s.clone());
			ls.setNeighborhood(n);
			ls.solve();
			if(ls.getSolution().compareTo(minSol) < 0)
				minSol = ls.getSolution().clone();
		}
		return minSol;
	}
	
	private Solution communicate(double rate){
		Solution sol = new Solution(this.getInstance());
		List<Integer> jobs = Random.randomShuffle(0, this.getInstance().getNbJobs());
		int n = (int)(this.getInstance().getNbJobs()*rate);
		// We keep information from the best hunter
		for(int i = 0; i < n; i++)
			sol.setOrder(jobs.get(i), this.wolves[0].getIndex(jobs.get(i)));
		// We complete in a random hunt
		int insert = sol.getIndex(-1);
		for(int i = n; i < this.getInstance().getNbJobs(); i++){
			sol.setOrder(jobs.get(i), insert);
			while(insert < this.getInstance().getNbJobs() && sol.getJob(insert) != -1)
				insert++;
		}
		sol.evaluate();
		sol = this.localHunt(sol);
		return sol;
	}

	@Override
	public void solve(Timer timer) {
		this.setSolution(Neh.solve(this.getInstance()));
		this.initialisation();
		Arrays.sort(this.wolves);
		if(this.getSolution().compareTo(this.wolves[0])<0)
			this.wolves[0] = this.getSolution().clone();
		while(!timer.isFinished()){
			// Pack's hunt
			for(int i = 1; i<this.packSize-1; i++){
				this.wolves[i] = this.communicate(this.communicationRate);
			}
			// Best hunter's local hunt
			this.wolves[0] = this.communicate(this.communicationRate*2);
			// Lonely wolf's hunt
			this.wolves[this.packSize-1] = this.localHunt(Solution.generateSolution(this.getInstance()));
			
			// Change in leadership
			Arrays.sort(this.wolves);
			// Keep the best solution found so far
			if(this.wolves[0].compareTo(this.getSolution())<0)
				this.setSolution(this.wolves[0].clone());
		}
	}

	public int getPackSize() {
		return packSize;
	}

	public void setPackSize(int packSize) {
		this.packSize = packSize;
	}

	public Solution[] getWolves() {
		return wolves;
	}

	public void setWolves(Solution[] wolves) {
		this.wolves = wolves;
	}

	public double getCommunicationRate() {
		return communicationRate;
	}

	public void setCommunicationRate(double communicationRate) {
		this.communicationRate = communicationRate;
	}
}
