/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package definition;

import java.util.Queue;
import java.util.LinkedList;

import java.util.Set;
import java.util.TreeSet;

/**
 * Container used in the TabuSearch Solver.
 */
public class TabuList {
	
	/** The capacity. */
	private int capacity;
	
	/** The set. */
	private Set<Solution> set;
	
	/** The queue. */
	private Queue<Solution> queue;
	
	/**
	 * Instantiates a new tabu list.
	 *
	 * @param capacity the capacity
	 */
	public TabuList(int capacity)
	{
		this.capacity = capacity;
		queue = new LinkedList<Solution>();
		set = new TreeSet<Solution>();
	}
	
	/**
	 * Gets the size of the list.
	 *
	 * @return the size of the list
	 */
	public int size() {
		return set.size();
	}
	
	/**
	 * Adds the solution to the tabu list.
	 *
	 * @param solution the solution to add
	 */
	public void add(Solution solution)
	{
		// remove oldest solution if the container is full
		if(size() == capacity)
			set.remove(queue.poll());
		
		set.add(solution);
		queue.add(solution);
	}
	
	/**
	 * Checks if the list contains the solution.
	 *
	 * @param solution the solution
	 * @return true, if successful
	 */
	public boolean contains(Solution solution)
	{
		return set.contains(solution);
	}
}