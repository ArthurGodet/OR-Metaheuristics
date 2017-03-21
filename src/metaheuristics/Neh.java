package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import definition.Instance;

public class Neh extends Solver{

	public Neh(Instance inst) {
		super(inst);
	}
	
	public void solve(){
		int[] ljNEH = this.creerListeNEH();
		for(int k = 0; k<ljNEH.length; k++)
			ordonnancerJobNEH(ljNEH[k],k);
	}
	
	private int[] creerListeNEH(){
		int[] res = new int[this.getInstance().getNbJobs()];
		List<Job> l = new ArrayList<Job>();
		for(int id = 0; id<this.getInstance().getNbJobs(); id++)
			l.add(new Job(this.getInstance(),id));
		Collections.sort(l, Collections.reverseOrder());
		for(int i = 0; i<res.length; i++)
			res[i] = l.get(i).getId();
		return res;
	}
	
	private void ordonnancerJobNEH(int j, int k) { 
		this.getSolution().setOrder(j,k);
		this.getSolution().evaluate();
		int[] bestList = this.getSolution().getOrder().clone();
		int bestValue = this.getSolution().getCmax();
		this.getSolution().retirerJob(k);
		
		// On cherche le meilleur emplacement pour le Job j dans l'Ordonnancement actuel
		for(int i = 0; i<k; i++){
			this.getSolution().insererJob(j,i);
			this.getSolution().evaluate();
			if(this.getSolution().getCmax()<bestValue){
				bestList = this.getSolution().getOrder().clone();
				bestValue = this.getSolution().getCmax();
			}
			this.getSolution().retirerJob(i);
		}
		
		// on retient la meilleure solution trouvee et on met a jour les dates de disponibilite
		this.getSolution().setOrder(bestList); 
		this.getSolution().evaluate();
	}
	
	public class Job implements Comparable<Job>{
		private int duree;
		private int id;
		
		public Job(Instance inst, int id){
			this.id = id;
			this.duree = 0;
			for(int j = 0; j<inst.getNbMachines(); j++)
				this.duree += inst.getDureeOperation(this.id,j);
		}
		
		public int getId(){
			return this.id;
		}
		
		public int getDuree(){
			return this.duree;
		}

		public int compareTo(Job o) {
			return Integer.compare(this.getDuree(),o.getDuree());
		}
	}
}
