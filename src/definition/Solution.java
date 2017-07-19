/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package definition;

import java.util.Arrays;
import java.util.List;

import util.Random;

/**
 * Representation of a solution of an schedulinging problem such as the TSP or the permutation flow shop
 * problem.
 */
public class Solution implements Comparable<Solution>, Cloneable{
	
	/** The scheduling of jobs or cities. */
	private int[] scheduling;
	
	/** The score. */
	private double score;
	
	/** The instance. */
	private Instance instance;

	/**
	 *  Builds an empty solution for a specific instance.
	 *
	 * @param i the instance
	 */
	public Solution(Instance instance){
		this.instance = instance;
		this.scheduling = new int[instance.getSize()];
		for(int j = 0; j<this.scheduling.length; j++)
			this.scheduling[j] = -1;
		this.score = Integer.MAX_VALUE;
	}

	/**
	 * Gets the job or city at the specific position.
	 *
	 * @param pos the position
	 * @return the job or city at the position in the scheduling
	 */
	public int getJob(int pos){
		return this.scheduling[pos];
	}

	/**
	 *  Assigns a copy of the scheduling o to this scheduling.
	 *
	 * @param o the new scheduling
	 */
	public void setScheduling(int[] o){
		this.scheduling = o.clone();
	}

	/**
	 *  Assigns the job or city at the specific position in the scheduling.
	 *
	 * @param job the job
	 * @param pos the position
	 */
	public void setScheduling(int job, int pos){
		this.scheduling[pos] = job;
	}

	/**
	 * Gets the index of the specific job in the scheduling. If the job is not in the scheduling,
	 * it returns -1.
	 *
	 * @param job the job
	 * @return the index of the job in the scheduling
	 */
	public int getIndex(int job){
		for(int i = 0; i<this.scheduling.length; i++)
			if(this.scheduling[i] == job)
				return i;
		return -1;
	}

	/**
	 * Gets the score of the scheduling.
	 *
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Gets the instance on which this solution is built.
	 *
	 * @return the instance
	 */
	public Instance getInstance(){
		return this.instance;
	}

	/**
	 *  Computes the score of the scheduling, depending on the type of the instance.
	 */
	public void evaluate(){
		if(this.getInstance() instanceof InstanceFlowshop){
			InstanceFlowshop inst = (InstanceFlowshop)this.getInstance();
			int[] dateDisponibilite = new int[inst.getNbMachines()];
			for(int i = 0; i<this.scheduling.length && this.scheduling[i] != -1; i++){
				dateDisponibilite[0] += inst.getDuration(this.scheduling[i],0);
				for(int j = 1; j<inst.getNbMachines(); j++){
					dateDisponibilite[j] = Math.max(dateDisponibilite[j],dateDisponibilite[j-1]) + inst.getDuration(this.scheduling[i],j);
				}
			}
			this.score = dateDisponibilite[inst.getNbMachines()-1];
		}
		else{
			InstanceTSP inst = (InstanceTSP)this.getInstance();
			this.score = 0;
			for(int i = 0; i<this.scheduling.length && this.scheduling[((i+1)%this.scheduling.length)] != -1; i++)
				this.score += inst.getDistance(this.scheduling[i],this.scheduling[((i+1)%this.scheduling.length)]);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Solution s){
		return Double.compare(this.getScore(),s.getScore());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Solution clone() {
		Solution c = new Solution(getInstance());
		c.score = this.score;
		c.scheduling = this.scheduling.clone();
		return c;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String s = "score = " + this.score + "  <---  (";
		for(int i = 0; i<this.scheduling.length; i++){
			s += (this.scheduling[i]+1) + (i == this.scheduling.length-1 ? "": " ");
		}
		s += ")";
		return s;
	}

	/**
	 *  Inserts the job at the specified position in the scheduling. The sequence of previous jobs 
	 *  beginning at the position is first moved to the right.
	 *
	 * @param job the job
	 * @param pos the position
	 */
	public void insertJob(int job, int pos){
		for(int k = this.scheduling.length-1; k>pos; k--)
			this.scheduling[k] = this.scheduling[k-1];
		this.scheduling[pos] = job;
	}

	/**
	 *  Removes the job at the specified position in the scheduling. The jobs that were after in 
	 *  the scheduling are moved such that the scheduling does not have a hole.
	 *
	 * @param pos the position
	 */
	public void removeJob(int pos){
		for(int k = pos; k<this.scheduling.length-1; k++)
			this.scheduling[k] = this.scheduling[k+1];
		this.scheduling[this.scheduling.length-1] = -1;
	}

	/**
	 * Checks if the scheduling contains the specific job or city.
	 *
	 * @param job the job
	 * @return true only if the job or city is in the scheduling
	 */
	public boolean contains(int job){
		return this.getIndex(job) != -1;
	}

	/**
	 *  Exchanges the jobs or cities at positions pos1 and pos2.
	 *
	 * @param pos1 the first position
	 * @param pos2 the second position
	 */
	public void swap(int pos1, int pos2){
		int job = this.scheduling[pos1];
		this.scheduling[pos1] = this.scheduling[pos2];
		this.scheduling[pos2] = job;
	}

	/**
	 * Does a circular permutation of the jobs or cities at positions pos1, pos2 and pos3.
	 * The job/city in pos1 goes in pos2, the one in pos2 goes in pos3 and the one in pos3 goes in
	 * pos1.
	 * 
	 * @param pos1 the first position
	 * @param pos2 the second position
	 * @param pos3 the third position
	 */
	public void change(int pos1, int pos2, int pos3){
		int job2 = this.scheduling[pos2];

		this.scheduling[pos2] = this.scheduling[pos1];
		this.scheduling[pos1] = this.scheduling[pos3];
		this.scheduling[pos3] = job2;
	}

	/**
	 * Moves to the right every jobs/cities between beginning position and ending position, with
	 * the job that was in posEnd going in posBegin.
	 *
	 * @param posBegin the beginning position
	 * @param posEnd the ending position
	 */
	public void rightShift(int posBegin, int posEnd){
		int job = this.scheduling[posEnd];
		for(int i = posEnd; i>posBegin; i--)
			this.scheduling[i] = this.scheduling[i-1];
		this.scheduling[posBegin] = job;
	}

	/**
	 * Moves to the left every jobs/cities between beginning position and ending position, with
	 * the job that was in posBegin going in posEnd.
	 *
	 * @param posBegin the beginning position
	 * @param posEnd the ending position
	 */
	public void leftShift(int posBegin, int posEnd){
		int job = this.scheduling[posBegin];
		for(int i = posBegin; i<posEnd; i++)
			this.scheduling[i] = this.scheduling[i+1];
		this.scheduling[posEnd] = job;
	}

	/**
	 * Reverse jobs/cities with position between from (inclusive) and to (exclusive).
	 *
	 * @param from the beginning position
	 * @param to the ending position
	 */
	public void reverse(int from, int to) {
		for(int i = from; i < to+from-i-1; i++)
			swap(i, to+from-i-1);
	}

	/**
	 * Adds the jobs/cities that are not in this solution, completing this scheduling in the same
	 * order than the one they appear in the other's scheduling.
	 *
	 * @param other the other solution
	 */
	public void merge(Solution other) {
		int insertion = 0;
		for(int i=0; i<other.getInstance().getSize(); i++)
			if(!this.contains(other.getJob(i))) {
				while(this.getJob(insertion) != -1)
					insertion++;
				this.setScheduling(other.getJob(i), insertion);
			}
	}

	/**
	 * Copy jobs/cities with positions between from and to.
	 * The solution should be empty in the given range when using this method.
	 *
	 * @param other the other solution
	 * @param from the beginning position
	 * @param to the ending position
	 */
	public void copyRange(Solution other, int from, int to) {
		for(int i = from; i < to; i++)
			this.setScheduling(other.getJob(i), i);
	}

	/**
	 *  Generates a random solution of the problem represented by the instance.
	 *
	 * @param inst the instance
	 * @return the solution
	 */
	public static Solution generateSolution(Instance inst){
		List<Integer> jobs = Random.randomSample(0, inst.getSize(), inst.getSize());

		Solution s = new Solution(inst);
		for(int i = 0; i<inst.getSize(); i++)
			s.setScheduling(jobs.get(i), i);
		s.evaluate();

		return s;
	}

	/**
	 * Checks if the two solutions are the same.
	 *
	 * @param s the solution
	 * @return true, if successful
	 */
	public boolean equals(Solution s){
		return Arrays.equals(this.scheduling, s.scheduling);
	}
}
