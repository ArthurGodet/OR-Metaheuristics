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
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * Solver par recherche de minimum local pour un certain voisinage.
 */
public class LocalSearch extends Solver{
	
	/** The neighbor. */
	protected Neighborhood neighbor;
	
	/**
	 * Constructeur.
	 *
	 * @param inst instance du problème
	 * @param n voisinage à explorer
	 * @param s solution de départ
	 */
	public LocalSearch(Instance inst, Neighborhood n, Solution s) {
		super(inst,"Local Search");
		this.neighbor = n;
		this.setSolution(s);
	}
	
	/**
	 * Sets the neighborhood.
	 *
	 * @param nh the new neighborhood
	 */
	public void setNeighborhood(Neighborhood nh){
		this.neighbor = nh;
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		int cmax = this.getSolution().getScore();
		// la recherche dans le voisinage est faite de sorte à ne pas dégrader
		// la solution lorsqu'une cuvette est atteinte, donc la boucle suivante est correcte.
		do{
			cmax = this.getSolution().getScore();
			this.setSolution(this.neighbor.getBestNeighbor(this.getSolution()));
		}while(cmax>this.getSolution().getScore() && !timer.isFinished());
	}
}
