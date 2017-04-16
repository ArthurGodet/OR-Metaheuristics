package metaheuristics;

import java.io.FileWriter;
import java.io.IOException;

public class Benchmark {
	public static void writeResults(Solver solver) throws IOException{
		FileWriter fw;
		if(solver instanceof AntColonyOptimization)
			fw = new FileWriter("results/AntColonyOptimization.txt",true);
		else if(solver instanceof BeesAlgorithm)
			fw = new FileWriter("results/BeesAlgorithm.txt",true);
		else if(solver instanceof Memetic)
			fw = new FileWriter("results/Memetic.txt",true);
		else if(solver instanceof Genetic)
			fw = new FileWriter("results/Genetic.txt",true);
		else if(solver instanceof Grasp)
			fw = new FileWriter("results/Grasp.txt",true);
		else if(solver instanceof Ils)
			fw = new FileWriter("results/Ils.txt",true);
		else if(solver instanceof SimulatedAnnealing)
			fw = new FileWriter("results/SimulatedAnnealing.txt",true);
		else if(solver instanceof TabuSearch)
			fw = new FileWriter("results/TabuSearch.txt",true);
		else if(solver instanceof Vns)
			fw = new FileWriter("results/Vns.txt",true);
		else
			fw = new FileWriter("results/WolfPackAlgorithm.txt",true);
		
		//fw.write(solver.getSolution().getCmax() + "\n");
		fw.write(solver + "\n");
		fw.close();
	}
	
	public static void presentation(Solver solver, String instance) throws IOException{
		FileWriter fw;
		if(solver instanceof AntColonyOptimization)
			fw = new FileWriter("results/AntColonyOptimization.txt",true);
		else if(solver instanceof BeesAlgorithm)
			fw = new FileWriter("results/BeesAlgorithm.txt",true);
		else if(solver instanceof Memetic)
			fw = new FileWriter("results/Memetic.txt",true);
		else if(solver instanceof Genetic)
			fw = new FileWriter("results/Genetic.txt",true);
		else if(solver instanceof Grasp)
			fw = new FileWriter("results/Grasp.txt",true);
		else if(solver instanceof Ils)
			fw = new FileWriter("results/Ils.txt",true);
		else if(solver instanceof SimulatedAnnealing)
			fw = new FileWriter("results/SimulatedAnnealing.txt",true);
		else if(solver instanceof TabuSearch)
			fw = new FileWriter("results/TabuSearch.txt",true);
		else if(solver instanceof Vns)
			fw = new FileWriter("results/Vns.txt",true);
		else
			fw = new FileWriter("results/WolfPackAlgorithm.txt",true);
		
		fw.write(instance+"\n");
		fw.close();
	}
	
	public static void EndPresentation(Solver solver) throws IOException{
		FileWriter fw;
		if(solver instanceof AntColonyOptimization)
			fw = new FileWriter("results/AntColonyOptimization.txt",true);
		else if(solver instanceof BeesAlgorithm)
			fw = new FileWriter("results/BeesAlgorithm.txt",true);
		else if(solver instanceof Memetic)
			fw = new FileWriter("results/Memetic.txt",true);
		else if(solver instanceof Genetic)
			fw = new FileWriter("results/Genetic.txt",true);
		else if(solver instanceof Grasp)
			fw = new FileWriter("results/Grasp.txt",true);
		else if(solver instanceof Ils)
			fw = new FileWriter("results/Ils.txt",true);
		else if(solver instanceof SimulatedAnnealing)
			fw = new FileWriter("results/SimulatedAnnealing.txt",true);
		else if(solver instanceof TabuSearch)
			fw = new FileWriter("results/TabuSearch.txt",true);
		else if(solver instanceof Vns)
			fw = new FileWriter("results/Vns.txt",true);
		else
			fw = new FileWriter("results/WolfPackAlgorithm.txt",true);
		
		fw.write("-----------"+"\n");
		fw.close();
	}
}
