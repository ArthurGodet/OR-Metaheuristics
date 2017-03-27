package definition;

import metaheuristics.Genetic;
import metaheuristics.LocalSearch;
import metaheuristics.Memetic;
import metaheuristics.Neh;
import metaheuristics.Solver;
import metaheuristics.TabuSearch;

public class Main {
	public static void main(String[] args) {
		Instance instance = new Instance("instances/tai21.txt");
		
		Solver solver = new Memetic(instance);
		solver.solve();
		
		Solver solver2 = new TabuSearch(instance,Neighborhood.SHIFT,solver.getSolution());
		solver2.solve();
		
		System.out.println(solver);
		System.out.println("--------");
		System.out.println(solver2);
	}
}
