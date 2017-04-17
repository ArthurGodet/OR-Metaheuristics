/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package definition;

import java.io.IOException;

import metaheuristics.AntColonyOptimization;
import metaheuristics.BeesAlgorithm;
import metaheuristics.Benchmark;
import metaheuristics.Genetic;
import metaheuristics.Grasp;
import metaheuristics.Ils;
import metaheuristics.LocalSearch;
import metaheuristics.Memetic;
import metaheuristics.Neh;
import metaheuristics.Parallel;
import metaheuristics.SimulatedAnnealing;
import metaheuristics.Solver;
import metaheuristics.TabuSearch;
import metaheuristics.Vns;
import metaheuristics.WolfPackAlgorithm;
import neighborhoods.Change;
import neighborhoods.Shift;
import neighborhoods.Swap;
import util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Instance instance = new Instance("instances/tai21.txt");
		Timer timer = new Timer(15000);
		/*
		Solver solver = new Memetic(instance);
		solver.solve(timer);

		Solver solver2 = new TabuSearch(instance,new Shift(),solver.getSolution());
		solver2.solve(new Timer(10000));

		System.out.println(solver);
		System.out.println("--------");
		System.out.println(solver2);
		//*/

		/*
		Solver solver = new Grasp(instance);
		solver.solve(timer);
		System.out.println(solver);
		//*/

		/*
		Solver solver = new Ils(instance);
		solver.solve(timer);
		System.out.println(solver);
		//*/

		/*
		Solver solver = new Vns(instance);
		solver.solve(timer);
		System.out.println(solver);
		//*/

		/*
		// startTemp = 25.0
		// tempfactor = 0.99 (doit être proche de 1)
		// sizefactor = 1 (n'apporte pas grand chose)
		// minpercent = 0.1 (doit être petit)
		Solver solver = new SimulatedAnnealing(instance, 25., 0.99, 1, 0.1);
		Solution s = Solution.generateSolution(instance);
		while(!timer.isFinished()){
			solver.solve(timer);
			if(solver.getSolution().compareTo(s)<0)
				s = solver.getSolution().clone();
		}
		solver.setSolution(s);
		System.out.println(solver);
		//*/

		/*
		AntColonyOptimization solver = new AntColonyOptimization(instance);
		solver.setNbAnts(500);
		solver.solve(timer);
		System.out.println(solver);
		//*/

		/*
		Solver solver = new BeesAlgorithm(instance);
		solver.solve(timer);
		System.out.println(solver);
		//*/

		/*
		Solver solver = new WolfPackAlgorithm(instance);
		solver.solve(timer);
		System.out.println(solver);
		//*/
		
		//*
		Instance[] instances = new Instance[]{new Instance("instances/tai01.txt"),new Instance("instances/tai11.txt"),new Instance("instances/tai21.txt"),new Instance("instances/tai31.txt"),new Instance("instances/tai41.txt"),new Instance("instances/tai51.txt")};
		Solver[] solvers = new Solver[]{new AntColonyOptimization(instance),new BeesAlgorithm(instance),new Genetic(instance),new Grasp(instance),new Ils(instance),new Memetic(instance),new SimulatedAnnealing(instance),new TabuSearch(instance,new Shift(),Solution.generateSolution(instance)),new Vns(instance),new WolfPackAlgorithm(instance)};
		
		Parallel parallel = new Parallel(instances[0]);
		for(int i = 0; i<instances.length; i++){
			parallel.setInstance(instances[i]);
			for(int k = 0; k<solvers.length; k++){
				parallel.setSolver(solvers[k]);
				timer.reset();
				parallel.solve(timer);
			}
		}
		//*/

		//System.out.println(timer.getElapsedTime()+" ms");
	}
}
