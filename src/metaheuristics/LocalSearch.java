package metaheuristics;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;

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
	 * @see metaheuristics.Solver#solve()
	 */
	public void solve() {
		int cmax = this.getSolution().getCmax();
		// la recherche dans le voisinage est faite de sorte à ne pas dégrader
		// la solution lorsqu'une cuvette est atteinte, donc la boucle suivante est correcte.
		do{
			cmax = this.getSolution().getCmax();
			this.setSolution(this.getSolution().generateBestNeighbor(this.neighbor));
		}while(cmax>this.getSolution().getCmax());
	}

}
