package definition;

import java.util.Queue;
import java.util.LinkedList;

import java.util.Set;
import java.util.TreeSet;

/**
 * Container used in the TabuSearch Solver
 * 
 */
public class TabuList {
	
	private int capacity;
	private Set<Solution> set;
	private Queue<Solution> queue;
	
	public TabuList(int capacity)
	{
		this.capacity = capacity;
		queue = new LinkedList<Solution>();
		set = new TreeSet<Solution>();
	}
	
	public int size() {
		return set.size();
	}
	
	public void add(Solution s)
	{
		// remove oldest solution if the container is full
		if(size() == capacity)
			set.remove(queue.poll());
		
		set.add(s);
		queue.add(s);
	}
	
	public boolean contains(Solution s)
	{
		return set.contains(s);
	}
}