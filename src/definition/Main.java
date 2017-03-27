package definition;

import metaheuristics.Neh;
import metaheuristics.Solver;

public class Main {
	public static void main(String[] args) {
		Instance instance = new Instance("instances/tai21.txt");
		Solver sol = new Neh(instance);
		
		sol.solve();
		
		System.out.println(sol.getName());
		System.out.println(sol.getSolution());
	}
}
