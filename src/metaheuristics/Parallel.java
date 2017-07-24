/**
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package metaheuristics;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import definition.Benchmark;
import definition.Instance;
import neighborhoods.Shift;
import util.Timer;

/**
 * Solver running 4 solvers of the same type in parallel.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class Parallel extends Solver {
	
	/** The Constant NUM_THREADS. */
	public static final int NUM_THREADS = 4;
	
	/** The number of threads. */
	private int numThreads;
	
	/** The type of solver. */
	private Solver solver;

	/**
	 * Instantiates a new Parallel solver.
	 *
	 * @param instance the instance
	 */
	public Parallel(Instance instance){
		this(instance,NUM_THREADS);
	}
	
	/**
	 * Instantiates a new Parallel solver.
	 *
	 * @param instance the instance
	 * @param numThreads the number of threads
	 */
	public Parallel(Instance instance,int numThreads){
		super(instance,"Parallel");
		this.numThreads = numThreads;
	}

	/**
	 * Sets the solver.
	 *
	 * @param solver the new solver
	 */
	public void setSolver(Solver solver){
		this.solver = solver;
	}

	/**
	 * The Class RunnableSolver.
	 */
	private class RunnableSolver implements Runnable {

		/** The solver. */
		private Solver solver;
		
		/** The timer. */
		private Timer timer;

		/**
		 * Instantiates a new runnable solver.
		 *
		 * @param solver the solver
		 * @param timer the timer
		 */
		public RunnableSolver(Solver solver, Timer timer) {
			this.solver = solver;
			this.timer = timer;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			try {
				this.solver.solve(this.timer);
				Benchmark.writeResults(this.solver);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer){
		Solver[] solvers = this.prepareSolvers();

		Timer[] timers = new Timer[this.numThreads];
		for(int i = 0; i<this.numThreads; i++)
			timers[i] = new Timer(timer.getTimeGiven());


		ExecutorService exe = Executors.newFixedThreadPool(this.numThreads);
		for(int i = 0; i < this.numThreads; i++){
			exe.execute(new RunnableSolver(solvers[i],timers[i]));
		}

		exe.shutdown();
		try {
			exe.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			for(int i = 0; i<NUM_THREADS; i++){
				Benchmark.writeResults(solvers[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prepares the numThreads solvers.
	 *
	 * @return the solver[]
	 */
	private Solver[] prepareSolvers(){
		Solver[] solvers = new Solver[this.numThreads];
		for(int i = 0; i<this.numThreads; i++){
			if(this.solver instanceof AntColonyOptimization)
				solvers[i] = new AntColonyOptimization(this.getInstance());
			else if(this.solver instanceof BeesAlgorithm)
				solvers[i] = new BeesAlgorithm(this.getInstance());
			else if(this.solver instanceof Vns)
				solvers[i] = new Vns(this.getInstance());
			else if(this.solver instanceof TabuSearch)
				solvers[i] = new TabuSearch(this.getInstance(),new Shift(),Greedy.solve(this.getInstance()));
			else if(this.solver instanceof SimulatedAnnealing)
				solvers[i] = new SimulatedAnnealing(this.getInstance());
			else if(this.solver instanceof Memetic)
				solvers[i] = new Memetic(this.getInstance());
			else if(this.solver instanceof Ils)
				solvers[i] = new Ils(this.getInstance());
			else if(this.solver instanceof Grasp)
				solvers[i] = new Grasp(this.getInstance());
			else if(this.solver instanceof Genetic)
				solvers[i] = new Genetic(this.getInstance());
			else
				solvers[i] = new WolfPackAlgorithm(this.getInstance());
		}
		return solvers;
	}
}
