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

/**
 * Solver running 4 solvers in parallel
 *
 */
public class Parallel extends Solver {
	private static final int NUM_THREADS = 4;
	private Solver solver;

	public Parallel(Instance instance){
		super(instance,"Calculs parallèles");
	}

	public void setSolver(Solver solver){
		this.solver = solver;
	}

	private class RunnableSolver implements Runnable {

		private Solver solver;
		private Timer timer;

		public RunnableSolver(Solver solver, Timer timer) {
			this.solver = solver;
			this.timer = timer;
		}

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
	 * 
	 * @param time Time allowed (in milliseconds) to solve the problem.
	 * @param out Output stream
	 * @throws Exception
	 *             May return some error, in particular if some vertices index
	 *             are wrong.
	 */
	public void solve(Timer timer){
		Solver[] solvers = this.prepareSolvers();

		Timer[] timers = new Timer[NUM_THREADS];
		for(int i = 0; i<NUM_THREADS; i++)
			timers[i] = new Timer(timer.getTimeGiven());


		ExecutorService exe = Executors.newFixedThreadPool(NUM_THREADS);
		for(int i = 0; i < NUM_THREADS; i++){
			exe.execute(new RunnableSolver(solvers[i],timers[i]));
		}

		exe.shutdown();
		try {
			exe.awaitTermination(timer.getRemainingTime()+500, TimeUnit.MILLISECONDS);
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

	private Solver[] prepareSolvers(){
		Solver[] solvers = new Solver[NUM_THREADS];
		for(int i = 0; i<NUM_THREADS; i++){
			if(this.solver instanceof AntColonyOptimization)
				solvers[i] = new AntColonyOptimization(this.getInstance());
			else if(this.solver instanceof BeesAlgorithm)
				solvers[i] = new BeesAlgorithm(this.getInstance());
			else if(this.solver instanceof Vns)
				solvers[i] = new Vns(this.getInstance());
			else if(this.solver instanceof TabuSearch)
				solvers[i] = new TabuSearch(this.getInstance(),new Shift(),Neh.solve(this.getInstance()));
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
