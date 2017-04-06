/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package metaheuristics;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;
import neighborhoods.Shift;
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class Ils.
 */
public class Ils extends Solver{
	
	/**
	 * Instantiates a new ils.
	 *
	 * @param inst the inst
	 * @param nbBoucles the nb boucles
	 */
	public Ils(Instance inst) {
		super(inst,"ILS");
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		int n = 0;
		Solver solver = new Neh(this.getInstance());
		solver.solve();
		LocalSearch ls = new LocalSearch(this.getInstance(),new Shift(),solver.getSolution());
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
		}while(!timer.isFinished());
	}
	
	/**
	 * Large step.
	 *
	 * @param s the s
	 */
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
