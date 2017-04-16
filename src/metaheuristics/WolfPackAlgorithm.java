package metaheuristics;

import java.util.ArrayList;
import java.util.Arrays;

import definition.Instance;
import definition.Solution;
import neighborhoods.Change;
import neighborhoods.Shift;
import neighborhoods.Swap;
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
			this.wolves[i] = Solution.generateSolution(this.getInstance());
	}
	
	private Solution localHunt(Solution s){
		Change change = new Change();
		Shift shift = new Shift();
		Swap swap = new Swap();
		Solution t = new Solution(this.getInstance());
		LocalSearch ls = new LocalSearch(this.getInstance(),shift,s.clone());
		ls.solve();
		t = ls.getSolution().clone();
		ls.setSolution(s.clone());
		ls.setNeighborhood(change);
		ls.solve();
		if(ls.getSolution().compareTo(t)<0)
			t = ls.getSolution().clone();
		ls.setSolution(s.clone());
		ls.setNeighborhood(swap);
		ls.solve();
		if(ls.getSolution().compareTo(t)<0)
			t = ls.getSolution().clone();
		return t.clone();
	}
	
	private Solution communicate(double rate){
		Solution sol = new Solution(this.getInstance());
		ArrayList<Integer> pos = this.pos();
		int n = (int)(((double)this.getInstance().getNbJobs())*rate);
		// We keep information from the best hunter
		for(int i = 0; i<=n; i++){
			int a = pos.remove((int)(pos.size()*Math.random()));
			sol.setOrder(a,this.wolves[0].getIndex(a));
		}
		// We complete in a random hunt
		int b = 0;
		while(sol.getJob(b)!=-1)b++;
		while(!pos.isEmpty()){
			sol.setOrder(pos.remove((int)(pos.size()*Math.random())),b);
			while(b<this.getInstance().getNbJobs() && sol.getJob(b)!=-1)b++;
		}
		sol.evaluate();
		sol = this.localHunt(sol);
		return sol.clone();
	}

	@Override
	public void solve(Timer timer) {
		this.initialisation();
		Arrays.sort(this.wolves);
		this.setSolution(this.wolves[0].clone());
		while(!timer.isFinished()){
			// Pack's hunt
			for(int i = 1; i<this.packSize-1; i++){
				this.wolves[i] = this.communicate(this.communicationRate);
			}
			// Best hunter's local hunt
			this.wolves[0] = this.communicate(this.communicationRate*2);
			// Lonely wolf's hunt
			this.wolves[this.packSize-1] = Solution.generateSolution(this.getInstance());
			this.localHunt(this.wolves[this.packSize-1]);
			
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
	
	private ArrayList<Integer> pos(){
		ArrayList<Integer> p = new ArrayList<Integer>();
		for(int k = 0; k<this.getInstance().getNbJobs(); k++)
			p.add(k);
		return p;
	}

	public double getCommunicationRate() {
		return communicationRate;
	}

	public void setCommunicationRate(double communicationRate) {
		this.communicationRate = communicationRate;
	}
}
