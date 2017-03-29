/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package definition;

import java.util.Queue;
import java.util.LinkedList;

import java.util.Set;
import java.util.TreeSet;

// TODO: Auto-generated Javadoc
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
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return set.size();
	}
	
	/**
	 * Adds the.
	 *
	 * @param s the s
	 */
	public void add(Solution s)
	{
		// remove oldest solution if the container is full
		if(size() == capacity)
			set.remove(queue.poll());
		
		set.add(s);
		queue.add(s);
	}
	
	/**
	 * Contains.
	 *
	 * @param s the s
	 * @return true, if successful
	 */
	public boolean contains(Solution s)
	{
		return set.contains(s);
	}
}