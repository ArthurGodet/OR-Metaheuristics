/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class WolfPackAlgorithm.
 */
public class WolfPackAlgorithm extends Solver{
	
	/** The pack size. */
	public static int PACK_SIZE = 10;
	
	/** The communication rate. */
	public static double COMMUNICATION_RATE = 0.4; // < 0.5
	
	/** The pack size. */
	private int packSize;
	
	/** The wolves. */
	private Solution[] wolves;
	
	/** The communication rate. */
	private double communicationRate;

	/**
	 * Instantiates a new wolf pack algorithm.
	 *
	 * @param inst the inst
	 * @param packSize the pack size
	 * @param communicationRate the communication rate
	 */
	public WolfPackAlgorithm(Instance inst, int packSize, double communicationRate) {
		super(inst,"Wolf Pack Algorithm");
		this.packSize = packSize;
		this.communicationRate = communicationRate;
	}
	
	/**
	 * Instantiates a new wolf pack algorithm.
	 *
	 * @param inst the inst
	 */
	public WolfPackAlgorithm(Instance inst) {
		this(inst,PACK_SIZE,COMMUNICATION_RATE);
	}
	
	/**
	 * Initialisation.
	 */
	private void initialisation(){
		this.wolves = new Solution[this.packSize];
		for(int i = 0; i<this.packSize; i++)
			this.wolves[i] = this.localHunt(Solution.generateSolution(this.getInstance()));
	}
	
	/**
	 * Local hunt.
	 *
	 * @param s the s
	 * @return the solution
	 */
	private Solution localHunt(Solution s){
		Solution minSol = new Solution(this.getInstance());
		LocalSearch ls = new LocalSearch(this.getInstance(), new Shift(), s.clone());
		//*
		Neighborhood[] nh = new Neighborhood[]{new Shift(), new Swap()};
		for(Neighborhood n : nh) {
			ls.setSolution(s.clone());
			ls.setNeighborhood(n);
			ls.solve();
			if(ls.getSolution().compareTo(minSol) < 0)
				minSol = ls.getSolution().clone();
		}
		//*/
		ls.solve();
		minSol = ls.getSolution();
		return minSol;
	}
	
	/**
	 * Communicate.
	 *
	 * @param rate the rate
	 * @return the solution
	 */
	private Solution communicate(double rate){
		Solution sol = new Solution(this.getInstance());
		List<Integer> jobs = Random.randomShuffle(0, this.getInstance().getSize());
		int n = (int)(this.getInstance().getSize()*rate);
		// We keep information from the best hunter
		for(int i = 0; i < n; i++)
			sol.setOrder(jobs.get(i), this.wolves[0].getIndex(jobs.get(i)));
		// We complete in a random hunt
		int insert = sol.getIndex(-1);
		for(int i = n; i < this.getInstance().getSize(); i++){
			sol.setOrder(jobs.get(i), insert);
			while(insert < this.getInstance().getSize() && sol.getJob(insert) != -1)
				insert++;
		}
		sol.evaluate();
		sol = this.localHunt(sol);
		return sol;
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
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

	/**
	 * Gets the pack size.
	 *
	 * @return the pack size
	 */
	public int getPackSize() {
		return packSize;
	}

	/**
	 * Sets the pack size.
	 *
	 * @param packSize the new pack size
	 */
	public void setPackSize(int packSize) {
		this.packSize = packSize;
	}

	/**
	 * Gets the wolves.
	 *
	 * @return the wolves
	 */
	public Solution[] getWolves() {
		return wolves;
	}

	/**
	 * Sets the wolves.
	 *
	 * @param wolves the new wolves
	 */
	public void setWolves(Solution[] wolves) {
		this.wolves = wolves;
	}

	/**
	 * Gets the communication rate.
	 *
	 * @return the communication rate
	 */
	public double getCommunicationRate() {
		return communicationRate;
	}

	/**
	 * Sets the communication rate.
	 *
	 * @param communicationRate the new communication rate
	 */
	public void setCommunicationRate(double communicationRate) {
		this.communicationRate = communicationRate;
	}
}
