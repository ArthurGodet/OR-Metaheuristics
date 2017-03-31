/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package neighborhoods;

import java.util.Iterator;

import definition.Neighborhood;
import definition.Solution;
import util.Random;

// TODO: Auto-generated Javadoc
/**
 * Voisinage basé sur la permutation de deux jobs.
 */
public class Swap extends Neighborhood {

	/**
	 * Iterateur sur le voisinage.
	 */
	public static class SwapIterator implements Iterator<Solution> {

		/** The sol. */
		private Solution sol;

		/** The j. */
		private int i, j;

		/**
		 * Instantiates a new swap iterator.
		 *
		 * @param sol the sol
		 */
		public SwapIterator(Solution sol) {
			this.sol = sol.clone();
			i = 0;
			j = 0;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return i != sol.getInstance().getNbJobs()-2 || j != sol.getInstance().getNbJobs()-1;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Solution next() {
			// restaurer la solution de départ sauf la première fois
			if(j != 0)
				sol.swap(i, j);

			// calcul des indices suivants
			j++;
			if(j == sol.getInstance().getNbJobs()) {
				i++;
				j = i+1;
			}

			// calcul du voisin
			sol.swap(i, j);
			sol.evaluate();

			return sol;
		}
	}

	/* (non-Javadoc)
	 * @see definition.Neighborhood#assignRandomNeighbor(definition.Solution)
	 */
	@Override
	public void assignRandomNeighbor(Solution sol) {
		int[] couple = Random.randomCouple(0, sol.getInstance().getNbJobs());
		sol.swap(couple[0], couple[1]);
		sol.evaluate();
	}

	/* (non-Javadoc)
	 * @see definition.Neighborhood#getNeighbors(definition.Solution)
	 */
	@Override
	public Iterable<Solution> getNeighbors(Solution sol) {
		final Solution s = sol;
		return new Iterable<Solution>() {
			public Iterator<Solution> iterator() {
				return new SwapIterator(s);
			}
		};
	}
}
