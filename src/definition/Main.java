package definition;

import metaheuristics.Genetic;
import metaheuristics.LocalSearch;
import metaheuristics.Memetic;
import metaheuristics.Neh;
import metaheuristics.Solver;

public class Main {
	public static void main(String[] args) {
		Instance instance = new Instance("instances/tai21.txt");
		//Solver solver = new Neh(instance);
		Solver solver = new Memetic(instance);
		
		solver.solve();
		
		System.out.println(solver);
	}
}
