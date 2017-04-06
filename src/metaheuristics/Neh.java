/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import definition.Instance;
import definition.Solution;
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * Solver utilisant l'heuristique NEH.
 */
public class Neh extends Solver{

	/**
	 * Instantiates a new neh.
	 *
	 * @param inst the inst
	 */
	public Neh(Instance inst) {
		super(inst, "NEH");
	}
	
	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer){
		List<Job> ljNEH = this.creerListeNEH();
		for(int k = 0; k < ljNEH.size(); k++)
			ordonnancerJobNEH(ljNEH.get(k).getId(),k);
	}
	
	public void solve(){
		List<Job> ljNEH = this.creerListeNEH();
		for(int k = 0; k < ljNEH.size(); k++)
			ordonnancerJobNEH(ljNEH.get(k).getId(),k);
	}
	
	/**
	 * Creer liste NEH.
	 *
	 * @return the list
	 */
	private List<Job> creerListeNEH(){
		List<Job> l = new ArrayList<Job>();
		for(int id = 0; id<this.getInstance().getNbJobs(); id++)
			l.add(new Job(this.getInstance(),id));
		Collections.sort(l, Collections.reverseOrder());
		return l;
	}
	
	/**
	 * Ordonnancer job NEH.
	 *
	 * @param j the j
	 * @param k the k
	 */
	private void ordonnancerJobNEH(int j, int k) { 
		this.getSolution().setOrder(j,k);
		this.getSolution().evaluate();

		// On cherche le meilleur emplacement pour le Job j dans l'Ordonnancement actuel
		Solution current = getSolution().clone();
		for(int i = k; i>0; i--){
			current.swap(i-1,i);
			current.evaluate();
			if(current.compareTo(this.getSolution()) <= 0)
				setSolution(current.clone());
		}
	}
	
	/**
	 * The Class Job.
	 */
	public class Job implements Comparable<Job>{
		
		/** The duree. */
		private int duree;
		
		/** The id. */
		private int id;
		
		/**
		 * Instantiates a new job.
		 *
		 * @param inst the inst
		 * @param id the id
		 */
		public Job(Instance inst, int id){
			this.id = id;
			this.duree = 0;
			for(int j = 0; j<inst.getNbMachines(); j++)
				this.duree += inst.getDureeOperation(this.id,j);
		}
		
		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public int getId(){
			return this.id;
		}
		
		/**
		 * Gets the duree.
		 *
		 * @return the duree
		 */
		public int getDuree(){
			return this.duree;
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		public int compareTo(Job o) {
			return Integer.compare(this.getDuree(),o.getDuree());
		}
	}
}
