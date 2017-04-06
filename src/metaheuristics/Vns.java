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
	 * @param n the n
	 */
	public Vns(Instance inst) {
		super(inst,"VNS");
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		Neighborhood[] tab = new Neighborhood[3];
		tab[0] = new Change();
		tab[1] = new Shift();
		tab[2] = new Swap();
		
		Neh neh = new Neh(this.getInstance());
		neh.solve();
		LocalSearch ls = new LocalSearch(this.getInstance(),tab[0],neh.getSolution());
		Solution s = neh.getSolution();
		
		int k = 0; // ATTENTION : influe l'ordre d'application influe sur le resultat final !
		int n = 0;
		do{
			//*
			if(k==3)
				k=0;
			//*/
			//k = (int)(Math.random()*tab.length);
			
			ls.setNeighborhood(tab[k]);
			ls.solve();
			s = ls.getSolution();
			
			k++;
			n++;
		}while(!timer.isFinished());
		
		this.setSolution(s.clone());
	}

}
