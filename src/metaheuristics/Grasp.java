/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import definition.Instance;
import definition.InstanceFlowshop;
import definition.Solution;
import metaheuristics.Greedy.Job;
import neighborhoods.Shift;
import util.Timer;

/**
 * Implementation of the GRASP (Greedy Randomized Adaptive Search Procedure) metaheuristic.
 */
public class Grasp extends Solver{
	
	/**
	 * Instantiates a new GRASP solver.
	 *
	 * @param inst the instance
	 */
	public Grasp(Instance inst) {
		super(inst,"GRASP");
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		this.setSolution(Greedy.solve(this.getInstance()));
		do{
			Solution s = new Solution(this.getInstance());
			List<Integer> lj = (this.getInstance() instanceof InstanceFlowshop ? createListOfJobs() : createListOfCities());
			for(int j = 0; j<this.getInstance().getSize(); j++)
				s.setScheduling(lj.remove((int)(Math.random()*(lj.size()/4))),j);
			s.evaluate();
			
			LocalSearch ls = new LocalSearch(this.getInstance(),new Shift(),s);
			ls.solve();
			if(ls.getSolution().compareTo(this.getSolution())<0)
				this.setSolution(ls.getSolution().clone()); // keeps the best solution found so far
		}while(!timer.isFinished());
		// until the timer is finished, generates a random solution and applies a local search
	}

	/**
	 * Creates the list of cities.
	 *
	 * @return the list
	 */
	private List<Integer> createListOfCities() {
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i<this.getInstance().getSize(); i++)
			l.add(i);
		
		List<Integer> res = new ArrayList<Integer>();
		while(l.size() != 0)
			res.add(l.remove((((int)(Math.random()*l.size())))));
		return res; // shuffled list of cities
	}

	/**
	 * Creates the list of jobs.
	 *
	 * @return the list
	 */
	private List<Integer> createListOfJobs(){
		List<Job> l = new ArrayList<Job>();
		Greedy g = new Greedy(this.getInstance());
		for(int id = 0; id<this.getInstance().getSize(); id++)
			l.add(g.new Job(((InstanceFlowshop)this.getInstance()),id));
		Collections.sort(l, Collections.reverseOrder());
		
		List<Integer> res = new ArrayList<Integer>();
		for(int i = 0; i<l.size(); i++)
			res.add(l.get(i).getId());
		return res;
	}
}
