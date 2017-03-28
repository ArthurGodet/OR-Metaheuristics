/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package definition;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Classe de base pour explorer les voisinages de solutions.
 */
public abstract class Neighborhood {
	
	/**
	 * Gets the random neighbor.
	 *
	 * @param sol the sol
	 * @return un solution aléatoire dans le voisinage de sol
	 */
	public abstract Solution getRandomNeighbor(Solution sol);

	/**
	 * Gets the neighbors.
	 *
	 * @param sol the sol
	 * @return un iterable sur les solutions (à cloner pour manipuler) dans le voisinage de sol
	 */
	public abstract Iterable<Solution> getNeighbors(Solution sol);

	/**
	 * Gets the best neighbor.
	 *
	 * @param sol the sol
	 * @return une meilleure solution dans le voisinage de sol
	 */
	public Solution getBestNeighbor(Solution sol) {
		Solution best = sol.clone();
		for(Solution s : getNeighbors(sol))
			if(s.compareTo(best) < 0)
				best = s.clone();
		return best;
	}

	/**
	 * Gets the neighbors list.
	 *
	 * @param sol the sol
	 * @return la liste des solutions dans le voisinage de sol
	 */
	public List<Solution> getNeighborsList(Solution sol) {
		List<Solution> list = new ArrayList<Solution>();
		for(Solution s : getNeighbors(sol))
			list.add(s.clone());
		return list;
	}
}
