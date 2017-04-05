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
	
	/** The nb boucles. */
	private int nbBoucles;
	
	/**
	 * Instantiates a new vns.
	 *
	 * @param inst the inst
	 * @param n the n
	 */
	public Vns(Instance inst,int n) {
		super(inst,"VNS");
		this.nbBoucles = n;
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		Neighborhood[] tab = new Neighborhood[3];
		tab[0] = new Change();
		tab[1] = new Shift();
		tab[2] = new Swap();
		
		Solver solver = new Neh(this.getInstance());
		solver.solve();
		LocalSearch ls = new LocalSearch(this.getInstance(),tab[0],solver.getSolution());
		Solution s = solver.getSolution();
		
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
		}while(n<this.nbBoucles && !timer.isFinished());
		
		this.setSolution(s.clone());
	}

}
