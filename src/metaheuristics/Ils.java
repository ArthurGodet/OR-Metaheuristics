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
import util.Random;
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
	 */
	public Ils(Instance inst) {
		super(inst,"ILS");
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		LocalSearch ls = new LocalSearch(this.getInstance(),new Shift(),Greedy.solve(this.getInstance()));
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
		}while(!timer.isFinished());
	}
	
	/**
	 * Large step.
	 *
	 * @param s the s
	 */
	// coupe la solution en 2 et inverse les deux parties
	public void largeStep(Solution s){
		int n = this.getInstance().getSize();
		int pos = Random.randomInteger(1, n-1);
		s.reverse(0, pos);
		s.reverse(pos, n);
		s.reverse(0, n);
		s.evaluate();
	}
}
