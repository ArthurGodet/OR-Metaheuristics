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
		Solver solv = new Neh(instance);
		solv.solve();
		//Solver solver = new Memetic(instance);
		Solver solver = new TabuSearch(instance,Neighborhood.SHIFT,solv.getSolution());
		
		solver.solve();
		
		System.out.println(solver);
	}
}
