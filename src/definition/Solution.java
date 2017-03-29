package definition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.Random;

// TODO: Auto-generated Javadoc
/**
 * Représentation d'une solution du problème de flowshop de permutation.
 */
public class Solution implements Comparable<Solution>, Cloneable{
	
	/** The order. */
	private int[] order;
	
	/** The cmax. */
	private int cmax;
	
	/** The instance. */
	private Instance instance;

	/**
	 *  Constructeur.
	 *
	 * @param i the i
	 */
	public Solution(Instance i){
		this.instance = i;
		this.order = new int[i.getNbJobs()];
		for(int j = 0; j<this.order.length; j++)
			this.order[j] = -1;
		this.cmax = Integer.MAX_VALUE;
	}

	/**
	 * Gets the job.
	 *
	 * @param pos the pos
	 * @return le job d'indice pos dans l'ordonnancement
	 */
	public int getJob(int pos){
		return this.order[pos];
	}

	/**
	 *  Assigne une copie de l'ordonnacement o.
	 *
	 * @param o the new order
	 */
	public void setOrder(int[] o){
		this.order = o.clone();
	}

	/**
	 *  Assigne le job à l'indice pos dans l'ordonnancement.
	 *
	 * @param job the job
	 * @param pos the pos
	 */
	public void setOrder(int job, int pos){
		this.order[pos] = job;
	}

	/**
	 * Gets the index.
	 *
	 * @param job the job
	 * @return l'indice du job dans l'ordonnancement s'il y est présent, sinon -1
	 */
	public int getIndex(int job){
		for(int i = 0; i<this.order.length; i++)
			if(this.order[i] == job)
				return i;
		return -1;
	}

	/**
	 * Gets the cmax.
	 *
	 * @return la durée totale de l'ordonnancement
	 */
	public int getCmax() {
		return cmax;
	}

	/**
	 * Gets the single instance of Solution.
	 *
	 * @return l'instance du problème
	 */
	public Instance getInstance(){
		return this.instance;
	}

	/**
	 *  recalcule la durée totale de l'ordonnancement.
	 */
	public void evaluate(){
		int[] dateDisponibilite = new int[this.instance.getNbMachines()];
		for(int i = 0; i<this.order.length && this.order[i] != -1; i++){
			dateDisponibilite[0] += this.instance.getDureeOperation(this.order[i],0);
			for(int j = 1; j<this.instance.getNbMachines(); j++){
				dateDisponibilite[j] = Math.max(dateDisponibilite[j],dateDisponibilite[j-1]) + this.instance.getDureeOperation(this.order[i],j);
			}
		}
		this.cmax = dateDisponibilite[this.instance.getNbMachines()-1];
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Solution s){
		return Integer.compare(this.getCmax(),s.getCmax());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Solution clone() {
		Solution c = new Solution(getInstance());
		c.cmax = this.cmax;
		c.order = this.order.clone();
		return c;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String s = "Cmax = " + this.cmax + "  <---  (";
		for(int i = 0; i<this.order.length; i++){
			s += this.order[i] + (i == this.order.length-1 ? "": " ");
		}
		s += ")";
		return s;
	}

	/**
	 *  Insère le job à l'indice pos dans l'ordonnancement.
	 *
	 * @param job the job
	 * @param pos the pos
	 */
	public void insererJob(int job, int pos){
		for(int k = this.order.length-1; k>pos; k--)
			this.order[k] = this.order[k-1];
		this.order[pos] = job;
	}

	/**
	 *  Retire le job à l'indice pos dans l'ordonnancement.
	 *
	 * @param pos the pos
	 */
	public void retirerJob(int pos){
		for(int k = pos; k<this.order.length-1; k++)
			this.order[k] = this.order[k+1];
		this.order[this.order.length-1] = -1;
	}

	/**
	 * Contains.
	 *
	 * @param job the job
	 * @return true ssi le job est présent dans l'ordonnancement
	 */
	public boolean contains(int job){
		return this.getIndex(job) != -1;
	}

	/**
	 *  Echange les jobs aux indices pos1 et pos2.
	 *
	 * @param pos1 the pos 1
	 * @param pos2 the pos 2
	 */
	public void swap(int pos1, int pos2){
		int job = this.order[pos1];
		this.order[pos1] = this.order[pos2];
		this.order[pos2] = job;
	}

	/**
	 * Effectue une permutation circulaire des jobs aux positions pos1, pos2, pos3.
	 * 
	 * Le job précédemment en pos1 va en pos2, celui en pos2 va en pos3 et celui en pos3 va en pos1.
	 *
	 * @param pos1 the pos 1
	 * @param pos2 the pos 2
	 * @param pos3 the pos 3
	 */
	public void change(int pos1, int pos2, int pos3){
		int job2 = this.order[pos2];

		this.order[pos2] = this.order[pos1];
		this.order[pos1] = this.order[pos3];
		this.order[pos3] = job2;
	}

	/**
	 * Effectue un décalage vers la droite d'une chaîne de jobs.
	 * 
	 * Le job en position posEnd va en posBegin,
	 * celui en position posBegin va en posBegin+1,
	 * ...,
	 * celui en position jobEnd-1 va en jobEnd.
	 *
	 * @param posBegin the pos begin
	 * @param posEnd the pos end
	 */
	public void rightShift(int posBegin, int posEnd){
		int job = this.order[posEnd];
		for(int i = posEnd; i>posBegin; i--)
			this.order[i] = this.order[i-1];
		this.order[posBegin] = job;
	}

	/**
	 * Effectue un décalage vers la gauche d'une chaîne de jobs.
	 * 
	 * Le job en position posBegin va en posEnd,
	 * celui en position posEnd va en posEnd-1,
	 * ...,
	 * celui en position posBegin+1 va en posBegin.
	 *
	 * @param posBegin the pos begin
	 * @param posEnd the pos end
	 */
	public void leftShift(int posBegin, int posEnd){
		int job = this.order[posBegin];
		for(int i = posBegin; i<posEnd; i++)
			this.order[i] = this.order[i+1];
		this.order[posEnd] = job;
	}

	/**
	 * Generate best neighbor.
	 *
	 * @param nh the nh
	 * @return le meilleur voisin pour le voisinage nh
	 */
	public Solution generateBestNeighbor(Neighborhood nh){
		Solution neighbor;
		switch(nh){
			case SWAP :
				neighbor = generateBestNeighborSwap(); break;
			case CHANGE :
				neighbor = generateBestNeighborChange(); break;
			default :
				neighbor = generateBestNeighborShift(); break;
		}
		return neighbor;
	}

	/**
	 * Generate best neighbor swap.
	 *
	 * @return le meilleur voisin dans le voisinage des swaps
	 */
	private Solution generateBestNeighborSwap(){
		Solution currentNeighbor = this.clone();
		Solution bestNeighbor = this.clone();
		for(int i = 0; i<this.order.length; i++){
			for(int j = i+1; j<this.order.length; j++){
				currentNeighbor.swap(i,j);
				currentNeighbor.evaluate();
				if(currentNeighbor.compareTo(bestNeighbor)<0)
					bestNeighbor = currentNeighbor.clone();
				currentNeighbor.swap(j,i);
			}
		}
		return bestNeighbor;
	}

	/**
	 * Generate best neighbor change.
	 *
	 * @return le meilleur voisin dans le voisinage des permutations circulaires de trois jobs
	 */
	private Solution generateBestNeighborChange(){
		Solution currentNeighbor = this.clone();
		Solution bestNeighbor = this.clone();
		for(int i = 0; i<this.order.length; i++){
			for(int j = i+1; j<this.order.length; j++){
				for(int k = j+1; k<this.order.length; k++){
					currentNeighbor.change(i,j,k);
					currentNeighbor.evaluate();
					if(currentNeighbor.compareTo(bestNeighbor)<0)
						bestNeighbor = currentNeighbor.clone();
					currentNeighbor.change(k,j,i);
				}
			}
		}
		return bestNeighbor;
	}

	/**
	 * Generate best neighbor shift.
	 *
	 * @return le meilleur voisin dans le voisinage des décalages à droite
	 */
	private Solution generateBestNeighborShift(){
		Solution currentNeighbor = this.clone();
		Solution bestNeighbor = this.clone();
		for(int i = 0; i<this.order.length; i++){
			for(int j = i+1; j<this.order.length; j++){
				currentNeighbor.rightShift(i,j);
				currentNeighbor.evaluate();
				if(currentNeighbor.compareTo(bestNeighbor)<0)
					bestNeighbor = currentNeighbor.clone();
				currentNeighbor.leftShift(i,j);
			}
		}
		return bestNeighbor;
	}

	/**
	 * Generate neighbors.
	 *
	 * @param nh the nh
	 * @return le meilleur voisin pour le voisinage nh
	 */
	public List<Solution> generateNeighbors(Neighborhood nh){
		List<Solution> neighbors;
		switch(nh){
			case SWAP :
				neighbors = generateNeighborsSwap(); break;
			case CHANGE :
				neighbors = generateNeighborsChange(); break;
			default :
				neighbors = generateNeighborsShift(); break;
		}
		return neighbors;
	}

	/**
	 * Generate neighbors swap.
	 *
	 * @return la listes des voisins pour le voisinage des swaps
	 */
	private List<Solution> generateNeighborsSwap(){
		Solution currentNeighbor = this.clone();
		List<Solution> neighbors = new ArrayList<Solution>();
		for(int i = 0; i<this.order.length; i++){
			for(int j = i+1; j<this.order.length; j++){
				currentNeighbor.swap(i,j);
				currentNeighbor.evaluate();
				neighbors.add(currentNeighbor.clone());
				currentNeighbor.swap(j,i);
			}
		}
		return neighbors;
	}

	/**
	 * Generate neighbors change.
	 *
	 * @return la liste des voisins pour le voisinage des permutations circulaires de trois jobs
	 */
	private List<Solution> generateNeighborsChange(){
		Solution currentNeighbor = this.clone();
		List<Solution> neighbors = new ArrayList<Solution>();
		for(int i = 0; i<this.order.length; i++){
			for(int j = i+1; j<this.order.length; j++){
				for(int k = j+1; k<this.order.length; k++){
					currentNeighbor.change(i,j,k);
					currentNeighbor.evaluate();
					neighbors.add(currentNeighbor.clone());
					currentNeighbor.change(k,j,i);
				}
			}
		}
		return neighbors;
	}

	/**
	 * Generate neighbors shift.
	 *
	 * @return la liste des voisins pour le voisinage des décalages à droite
	 */
	private List<Solution> generateNeighborsShift(){
		Solution currentNeighbor = this.clone();
		List<Solution> neighbors = new ArrayList<Solution>();
		for(int i = 0; i<this.order.length; i++){
			for(int j = i+1; j<this.order.length; j++){
				currentNeighbor.rightShift(i,j);
				currentNeighbor.evaluate();
				neighbors.add(currentNeighbor.clone());
				currentNeighbor.leftShift(i,j);
			}
		}
		return neighbors;
	}

	/**
	 *  Génère une solution aléatoire.
	 *
	 * @param inst the inst
	 * @return the solution
	 */
	public static Solution generateSolution(Instance inst){
		List<Integer> jobs = Random.randomSample(0, inst.getNbJobs(), inst.getNbJobs());

		Solution s = new Solution(inst);
		for(int i = 0; i<inst.getNbJobs(); i++)
			s.setOrder(jobs.get(i), i);
		s.evaluate();

		return s;
	}

	/**
	 * Equals.
	 *
	 * @param s the s
	 * @return true, if successful
	 */
	public boolean equals(Solution s){
		return Arrays.equals(this.order, s.order);
	}
}
