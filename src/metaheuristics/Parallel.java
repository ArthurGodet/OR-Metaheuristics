/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package metaheuristics;

import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import definition.Benchmark;
import definition.Instance;
import neighborhoods.Shift;
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * Solver running 4 solvers in parallel.
 */
public class Parallel extends Solver {
	
	/** The Constant NUM_THREADS. */
	public static final int NUM_THREADS = 4;
	
	/** The num threads. */
	private int numThreads;
	
	/** The solver. */
	private Solver solver;

	/**
	 * Instantiates a new parallel.
	 *
	 * @param instance the instance
	 */
	public Parallel(Instance instance){
		this(instance,NUM_THREADS);
	}
	
	/**
	 * Instantiates a new parallel.
	 *
	 * @param instance the instance
	 * @param numThreads the num threads
	 */
	public Parallel(Instance instance,int numThreads){
		super(instance,"Calculs parallèles");
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

	/**
	 * Solve.
	 *
	 * @param timer the timer
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		try {
			for(int i = 0; i<NUM_THREADS; i++){
				Benchmark.writeResults(solvers[i]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//*/
	}

	/**
	 * Prepare solvers.
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
