/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package definition;

import metaheuristics.AntColonyOptimization;
import metaheuristics.BeesAlgorithm;
import metaheuristics.Genetic;
import metaheuristics.Grasp;
import metaheuristics.Ils;
import metaheuristics.LocalSearch;
import metaheuristics.Memetic;
import metaheuristics.Neh;
import metaheuristics.SimulatedAnnealing;
import metaheuristics.Solver;
import metaheuristics.TabuSearch;
import metaheuristics.Vns;
import neighborhoods.Shift;

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
		/*
		Solver solver = new Memetic(instance);
		solver.solve();

		Solver solver2 = new TabuSearch(instance,new Shift(),solver.getSolution());
		solver2.solve();

		System.out.println(solver);
		System.out.println("--------");
		System.out.println(solver2);
		//*/

		/*
		Solver solver = new Grasp(instance,1000);
		solver.solve();
		System.out.println(solver);
		//*/

		/*
		Solver solver = new Ils(instance,10000);
		solver.solve();
		System.out.println(solver);
		//*/

		/*
		Solver solver = new Vns(instance,1000);
		solver.solve();
		System.out.println(solver);
		//*/

		/*
		// startTemp = 25.0
		// tempfactor = 0.99 (doit être proche de 1)
		// sizefactor = 1 (n'apporte pas grand chose)
		// minpercent = 0.1 (doit être petit)
		// nbLoops peut être grand (10 000 : OK)
		Solver solver = new SimulatedAnnealing(instance, 25., 0.99, 1, 0.1, 10000);
		Solution s = Solution.generateSolution(instance);
		for(int i = 0; i<20; i++){ // intérêt d'avoir un paramètre de temps dans notre solver !!!
			solver.solve();
			if(solver.getSolution().compareTo(s)<0)
				s = solver.getSolution().clone();
		}
		solver.setSolution(s);
		System.out.println(solver);
		//*/

		/*
		Solver solver = new AntColonyOptimization(instance,500); // environ 1 minute par millier de boucles
		solver.solve();
		System.out.println(solver);
		//*/

		//*
		Solver solver = new BeesAlgorithm(instance,100);
		solver.solve();
		System.out.println(solver);
		//*/
	}
}
