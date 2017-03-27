package metaheuristics;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;

public class Ils extends Solver{
	private int nbBoucles;
	
	public Ils(Instance inst, int nbBoucles) {
		super(inst,"ILS");
		this.nbBoucles = nbBoucles;
	}

	public void solve() {
		int n = 0;
		Solver solver = new Neh(this.getInstance());
		solver.solve();
		LocalSearch ls = new LocalSearch(this.getInstance(),Neighborhood.SHIFT,solver.getSolution());
		ls.solve();
		Solution s = ls.getSolution();
		this.setSolution(s.clone());
		do{
			largeStep(s);
			
			ls.setSolution(s);
			ls.solve();
			s = ls.getSolution().clone();
			
			if(s.compareTo(this.getSolution())<0)
				this.setSolution(s.clone());
			
			n++;
		}while(n<this.nbBoucles);
	}
	
	// coupe la solution en 2 et inverse les deux parties
	public void largeStep(Solution s){
		int pos = (int)(Math.random()*this.getInstance().getNbJobs());
		int[] schedule = new int[this.getInstance().getNbJobs()];
		int k = 0;
		for(int i = pos; i<schedule.length; i++){
			schedule[k] = s.getJob(i);
			k++;
		}
		for(int i = 0; i<pos; i++){
			schedule[k] = s.getJob(i);
			k++;
		}
		s.setOrder(schedule);
		s.evaluate();
	}

}
