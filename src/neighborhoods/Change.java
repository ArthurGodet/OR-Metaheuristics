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

/**
 * The Change type of neighborhood. The neighbors are computing by doing circular permutation from
 * the given solution.
 */
public class Change extends Neighborhood {

	/**
	 * The Class ChangeIterator.
	 */
	public static class ChangeIterator implements Iterator<Solution> {

		/** The solution. */
		private Solution solution;

		/** The indexes.. */
		private int i, j, k;

		/**
		 * Instantiates a new change iterator.
		 *
		 * @param solution the solution
		 */
		public ChangeIterator(Solution solution) {
			this.solution = solution.clone();
			i = 0;
			j = 1;
			k = 1;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			int n = solution.getInstance().getSize();
			return i != n-3 || j != n-2 || k != n-1;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Solution next() {
			if(k != 1)
				solution.change(k,j,i);

			// computing the next indexes
			k++;
			if(k == solution.getInstance().getSize()) {
				j++;
				if(j == solution.getInstance().getSize()-1) {
					i++;
					j = i+1;
				}
				k = j+1;
			}

			// computing the neighbor
			solution.change(i, j, k);
			solution.evaluate();

			return solution;
		}
	}

	/* (non-Javadoc)
	 * @see definition.Neighborhood#assignRandomNeighbor(definition.Solution)
	 */
	@Override
	public void assignRandomNeighbor(Solution solution) {
		int[] triplet = Random.randomTriplet(0, solution.getInstance().getSize());
		solution.change(triplet[0], triplet[1], triplet[2]);
		solution.evaluate();
	}

	/* (non-Javadoc)
	 * @see definition.Neighborhood#getNeighbors(definition.Solution)
	 */
	@Override
	public Iterable<Solution> getNeighbors(Solution solution) {
		final Solution s = solution;
		return new Iterable<Solution>() {
			public Iterator<Solution> iterator() {
				return new ChangeIterator(s);
			}
		};
	}
}
