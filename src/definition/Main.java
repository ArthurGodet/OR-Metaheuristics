package definition;

import metaheuristics.Genetic;
import metaheuristics.Grasp;
import metaheuristics.Ils;
import metaheuristics.LocalSearch;
import metaheuristics.Memetic;
import metaheuristics.Neh;
import metaheuristics.Solver;
import metaheuristics.TabuSearch;
import metaheuristics.Vns;

public class Main {
	public static void main(String[] args) {
		Instance instance = new Instance("instances/tai21.txt");
		/*
		Solver solver = new Memetic(instance);
		solver.solve();

		Solver solver2 = new TabuSearch(instance,Neighborhood.SHIFT,solver.getSolution());
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

		//*
		Solver solver = new Vns(instance,1000);
		solver.solve();
		System.out.println(solver);
		//*/
	}
}
