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
 * The Shift type of neighborhood. The neighbors are computing by shifting part of the given 
 * solution's scheduling.
 */
public class Shift extends Neighborhood {

	/**
	 * The Class ShiftIterator.
	 */
	public static class ShiftIterator implements Iterator<Solution> {

		/** The solution. */
		private Solution solution;

		/** The indexes. */
		private int i, j;

		/**
		 * Instantiates a new shift iterator.
		 *
		 * @param solution the solution
		 */
		public ShiftIterator(Solution solution) {
			this.solution = solution.clone();
			i = 0;
			j = 0;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return i != solution.getInstance().getSize()-2 || j != solution.getInstance().getSize()-1;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Solution next() {
			j++;
			if(j == solution.getInstance().getSize()) {
				solution.leftShift(i, j-1);
				i++;
				j = i+1;
			}
			solution.swap(i, j);
			solution.evaluate();

			return solution;
		}

	}

	/* (non-Javadoc)
	 * @see definition.Neighborhood#assignRandomNeighbor(definition.Solution)
	 */
	@Override
	public void assignRandomNeighbor(Solution solution) {
		int[] couple = Random.randomCouple(0, solution.getInstance().getSize());
		solution.rightShift(couple[0], couple[1]);
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
				return new ShiftIterator(s);
			}
		};
	}
}
