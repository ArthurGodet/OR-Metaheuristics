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
import neighborhoods.Change;
import neighborhoods.Shift;
import neighborhoods.Swap;
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class Vns.
 */
public class Vns extends Solver{
	
	/**
	 * Instantiates a new vns.
	 *
	 * @param inst the inst
	 */
	public Vns(Instance inst) {
		super(inst,"VNS");
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		this.setSolution(Neh.solve(this.getInstance()));
		Neighborhood[] tab = new Neighborhood[] {new Change(), new Shift(), new Swap()};
		
		Solution s = Neh.solve(this.getInstance());
		LocalSearch ls = new LocalSearch(this.getInstance(),tab[0],s);
		
		int k = 0; // ATTENTION : influe l'ordre d'application influe sur le resultat final !
		do{
			if(k==3)
				k=0;
			
			ls.setNeighborhood(tab[k]);
			ls.solve(timer);
			s = ls.getSolution();
			
			k++;
		}while(!timer.isFinished());
		
		this.setSolution(s.clone());
	}

}
