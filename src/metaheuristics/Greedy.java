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
import definition.InstanceFlowshop;
import definition.InstanceTSP;
import definition.Solution;
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * Solver utilisant l'heuristique NEH.
 */
public class Greedy extends Solver{

	/**
	 * Instantiates a new neh.
	 *
	 * @param inst the inst
	 */
	public Greedy(Instance inst) {
		super(inst, (inst instanceof InstanceFlowshop ? "NEH" : "NearestNeighbor"));
	}
	
	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer){
		if(this.getInstance() instanceof InstanceFlowshop){
			List<Job> ljNEH = this.creerListeNEH();
			for(int k = 0; k < ljNEH.size(); k++)
				ordonnancerJobNEH(ljNEH.get(k).getId(),k);
		}
		else{
			InstanceTSP inst = (InstanceTSP)this.getInstance();
			List<Integer> cities = new ArrayList<Integer>();
			for(int i = 1; i<this.getInstance().getSize(); i++)
				cities.add(i);
			Solution sol = new Solution(this.getInstance());
			sol.setScheduling(0,0);
			for(int k = 1; k<this.getInstance().getSize(); k++){
				int city = cities.get(0);
				double dist = inst.getDistance(sol.getJob(k-1),city);
				for(int j = 1; j<cities.size(); j++){
					if(inst.getDistance(sol.getJob(k-1),cities.get(j))<dist){
						city = cities.get(j);
						dist = inst.getDistance(sol.getJob(k-1),cities.get(j));
					}
				}
				cities.remove(new Integer(city));
				sol.setScheduling(city,k);
			}
			sol.evaluate();
			this.setSolution(sol);
		}
	}

	/**
	 * Solve.
	 *
	 * @param instance the instance
	 * @return the solution
	 */
	public static Solution solve(Instance instance) {
		Greedy greedy = new Greedy(instance);
		greedy.solve();
		return greedy.getSolution();
	}
	
	/**
	 * Creer liste NEH.
	 *
	 * @return the list
	 */
	private List<Job> creerListeNEH(){
		List<Job> l = new ArrayList<Job>();
		for(int id = 0; id<this.getInstance().getSize(); id++)
			l.add(new Job(((InstanceFlowshop)this.getInstance()),id));
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
		this.getSolution().setScheduling(j,k);
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
		public Job(InstanceFlowshop inst, int id){
			this.id = id;
			this.duree = 0;
			for(int j = 0; j<inst.getNbMachines(); j++)
				this.duree += inst.getDuration(this.id,j);
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
