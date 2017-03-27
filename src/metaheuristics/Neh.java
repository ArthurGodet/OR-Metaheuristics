package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import definition.Instance;
import definition.Solution;

public class Neh extends Solver{

	public Neh(Instance inst) {
		super(inst);
		this.nameOfMethod = "NEH";
	}
	
	public void solve(){
		List<Job> ljNEH = this.creerListeNEH();
		for(int k = 0; k < ljNEH.size(); k++)
			ordonnancerJobNEH(ljNEH.get(k).getId(),k);
	}
	
	private List<Job> creerListeNEH(){
		List<Job> l = new ArrayList<Job>();
		for(int id = 0; id<this.getInstance().getNbJobs(); id++)
			l.add(new Job(this.getInstance(),id));
		Collections.sort(l, Collections.reverseOrder());
		return l;
	}
	
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
