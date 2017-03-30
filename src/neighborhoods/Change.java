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
 * The Class Change.
 */
public class Change extends Neighborhood {

	/**
	 * The Class ChangeIterator.
	 */
	public static class ChangeIterator implements Iterator<Solution> {

		/** The sol. */
		private Solution sol;

		/** The k. */
		private int i, j, k;

		/**
		 * Instantiates a new change iterator.
		 *
		 * @param sol the sol
		 */
		public ChangeIterator(Solution sol) {
			this.sol = sol.clone();
			i = 0;
			j = 1;
			k = 1;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			int n = sol.getInstance().getNbJobs();
			return i != n-3 || j != n-2 || k != n-1;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Solution next() {
			// restaurer la solution de départ sauf la première fois
			if(k != 1)
				sol.change(k,j,i);

			// calcul des indices suivants
			k++;
			if(k == sol.getInstance().getNbJobs()) {
				j++;
				if(j == sol.getInstance().getNbJobs()-1) {
					i++;
					j = i+1;
				}
				k = j+1;
			}

			// calcul du voisin
			sol.change(i, j, k);
			sol.evaluate();

			return sol;
		}
	}

	/* (non-Javadoc)
	 * @see definition.Neighborhood#assignRandomNeighbor(definition.Solution)
	 */
	@Override
	public void assignRandomNeighbor(Solution sol) {
		int[] triplet = Random.randomTriplet(0, sol.getInstance().getNbJobs());
		sol.change(triplet[0], triplet[1], triplet[2]);
		sol.evaluate();
	}

	/* (non-Javadoc)
	 * @see definition.Neighborhood#getNeighbors(definition.Solution)
	 */
	@Override
	public Iterable<Solution> getNeighbors(Solution sol) {
		final Solution s = sol;
		return new Iterable<Solution>() {
			@Override
			public Iterator<Solution> iterator() {
				return new ChangeIterator(s);
			}
		};
	}
}
