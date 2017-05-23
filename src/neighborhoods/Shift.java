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
 * The Class Shift.
 */
public class Shift extends Neighborhood {

	/**
	 * The Class ShiftIterator.
	 */
	public static class ShiftIterator implements Iterator<Solution> {

		/** The sol. */
		private Solution sol;

		/** The j. */
		private int i, j;

		/**
		 * Instantiates a new shift iterator.
		 *
		 * @param sol the sol
		 */
		public ShiftIterator(Solution sol) {
			this.sol = sol.clone();
			i = 0;
			j = 0;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return i != sol.getInstance().getSize()-2 || j != sol.getInstance().getSize()-1;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Solution next() {
			j++;
			if(j == sol.getInstance().getSize()) {
				sol.leftShift(i, j-1);
				i++;
				j = i+1;
			}
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
		int[] couple = Random.randomCouple(0, sol.getInstance().getSize());
		sol.rightShift(couple[0], couple[1]);
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
				return new ShiftIterator(s);
			}
		};
	}
}
