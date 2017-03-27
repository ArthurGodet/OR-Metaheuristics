package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;

public class Grasp extends Solver{
	private int nbBoucles;
	// Greedy Randomize Adaptive Search Procedure
	public Grasp(Instance inst, int nbBoucles) {
		super(inst,"GRASP");
		this.nbBoucles = nbBoucles;
	}

	public void solve() {
		int n = 0;
		do{
			Solution s = new Solution(this.getInstance());
			List<Integer> lj = creerListeJob();
			for(int j = 0; j<this.getInstance().getNbJobs(); j++)
				s.setOrder(lj.remove((int)(Math.random()*(lj.size()/4))),j);
			s.evaluate();
			
			LocalSearch ls = new LocalSearch(this.getInstance(),Neighborhood.SHIFT,s);
			ls.solve();
			if(n == 0)
				this.setSolution(ls.getSolution());
			else if(ls.getSolution().compareTo(this.getSolution())<0)
				this.setSolution(ls.getSolution().clone());
			n++;
		}while(n<this.nbBoucles);
	}

	private List<Integer> creerListeJob(){
		List<Job> l = new ArrayList<Job>();
		for(int id = 0; id<this.getInstance().getNbJobs(); id++)
			l.add(new Job(this.getInstance(),id));
		Collections.sort(l, Collections.reverseOrder());
		
		List<Integer> res = new ArrayList<Integer>();
		for(int i = 0; i<l.size(); i++)
			res.add(l.get(i).getId());
		return res;
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
