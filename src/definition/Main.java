/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package definition;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import metaheuristics.AntColonyOptimization;
import metaheuristics.BeesAlgorithm;
import metaheuristics.Genetic;
import metaheuristics.Grasp;
import metaheuristics.Ils;
import metaheuristics.LocalSearch;
import metaheuristics.Memetic;
import metaheuristics.Greedy;
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
		Instance instance = new InstanceFlowshop("instances/Flowshop/tai51.txt");
		Timer timer = new Timer(60000);
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

		/*
		Instance[] instances = new Instance[]{new Instance("instances/tai01.txt"),new Instance("instances/tai11.txt"),new Instance("instances/tai21.txt"),new Instance("instances/tai31.txt"),new Instance("instances/tai41.txt"),new Instance("instances/tai51.txt")};
		Solver[][] solvers = new Solver[instances.length][];
		for(int i = 0; i<instances.length; i++){
			instance = instances[i];
			solvers[i] = new Solver[]{new AntColonyOptimization(instance),new BeesAlgorithm(instance),new Genetic(instance),new Grasp(instance),new Ils(instance),new Memetic(instance),new SimulatedAnnealing(instance),new TabuSearch(instance,new Shift(),Solution.generateSolution(instance)),new Vns(instance),new WolfPackAlgorithm(instance)};
		}

		try{
			//for(int i = 0; i<instances.length; i++){
				//for(int j = 0; j<solvers[0].length; j++){
					int i = 5;
					int j = solvers[0].length-1;
					Benchmark.presentation(solvers[i][j],instances[i].getName());
					for(int k = 0; k<4; k++){
						timer.reset();
						solvers[i][j].solve(timer);
						Benchmark.writeResults(solvers[i][j]);
					}
				//}
			//}
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//*/

		/*
		// Flowshop instances
		int[] instanceIndex = new int[] {
				 1,   2,   3,   4,   5,   6,   7,   8,   9,  10,
				 11,  12,  13,  14,  15,  16,  17,  18,  19,  20,
				 21,  22,  23,  24,  25,  26,  27,  28,  29,  30,
				 31,  32,  33,  34,  35,  36,  37,  38,  39,  40,
				 41,  42,  43,  44,  45,  46,  47,  48,  49,  50,
				 51,  52,  53,  54,  55,  56,  57,  58,  59,  60,
				 61,  62,  63,  64,  65,  66,  67,  68,  69,  70,
				 71,  72,  73,  74,  75,  76,  77,  78,  79,  80,
				 81,  82,  83,  84,  85,  86,  87,  88,  89,  90,
				 91,  92,  93,  94,  95,  96,  97,  98,  99, 100,
				101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
				111, 112, 113, 114, 115, 116, 117, 118, 119, 120
		};
		Instance[] instances = new Instance[instanceIndex.length];
		for(int i = 0; i < instanceIndex.length; i++)
			instances[i] = new InstanceFlowshop(String.format("instances/Flowshop/tai%02d.txt", instanceIndex[i]));
		*/
		
		//*
		// TSP instances
		
		class Filter implements FileFilter{
			public Filter(){
				
			}
			
			public boolean accept(File pathname) {
				String path = pathname.getName();
				String type = path.substring(path.length()-3);
				System.out.println(type);
				return type.equals("tsp");
			}
		}
		
		File folder = new File("instances/TSP");
		File[] listOfFiles = folder.listFiles(new Filter());
		Instance[] instances = new Instance[listOfFiles.length];
		for(int i = 0; i<instances.length; i++){
			System.out.println(listOfFiles[i].getPath());
			instances[i] = new InstanceTSP(listOfFiles[i].getPath());
		}
		//*/
		//instances[0] = new InstanceTSP("instances/TSP/att48.tsp");
		
		
		Solver[] solvers = new Solver[]{
				//new AntColonyOptimization(instance),
				//new BeesAlgorithm(instance),
				//new Genetic(instance),
				//new Grasp(instance),
				//new Ils(instance),
				//new Memetic(instance),
				//new SimulatedAnnealing(instance),
				new TabuSearch(instance, new Shift(), new Solution(instance)),
				new Vns(instance),
				//new WolfPackAlgorithm(instance)
		};
		Parallel parallel = new Parallel(instances[0],2);
		//*
		try{
			int nbFois = 2;
			for(int m = 0; m<nbFois; m++){
				for(int i = 0; i<instances.length; i++){
					parallel.setInstance(instances[i]);
					System.out.println(instances[i].getName());
					for(int k = 0; k<solvers.length; k++){
						parallel.setSolver(solvers[k]);
						System.out.println(solvers[k].getName());
						Benchmark.presentation(solvers[k],instances[i].getName());
						timer.reset();
						parallel.solve(timer);
					}
				}
			}
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//*/
		//*/

		//System.out.println(timer.getElapsedTime()+" ms");
	}
}
