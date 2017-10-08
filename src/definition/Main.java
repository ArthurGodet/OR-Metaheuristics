/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
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
import metaheuristics.Memetic;
import metaheuristics.Parallel;
import metaheuristics.SimulatedAnnealing;
import metaheuristics.Solver;
import metaheuristics.TabuSearch;
import metaheuristics.Vns;
import metaheuristics.WolfPackAlgorithm;
import neighborhoods.Shift;
import util.Timer;

/**
 * The Class Main.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Instance instance = new InstanceFlowshop("instances/Flowshop/tai51.txt");
		Timer timer = new Timer(10000); // time given in milliseconds for solving the instance of the problem

		//*
		// Flowshop instances
		int[] instanceIndex = new int[] {
				1,   2,   3,   4,   5,   6,   7,   8,   9,  10,
				//11,  12,  13,  14,  15,  16,  17,  18,  19,  20,
				//21,  22,  23,  24,  25,  26,  27,  28,  29,  30,
				//31,  32,  33,  34,  35,  36,  37,  38,  39,  40,
				//41,  42,  43,  44,  45,  46,  47,  48,  49,  50,
				//51,  52,  53,  54,  55,  56,  57,  58,  59,  60,
				//61,  62,  63,  64,  65,  66,  67,  68,  69,  70,
				//71,  72,  73,  74,  75,  76,  77,  78,  79,  80,
				//81,  82,  83,  84,  85,  86,  87,  88,  89,  90,
				//91,  92,  93,  94,  95,  96,  97,  98,  99, 100,
				//101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
				//111, 112, 113, 114, 115, 116, 117, 118, 119, 120
		};
		Instance[] instances = new Instance[instanceIndex.length];
		for(int i = 0; i < instanceIndex.length; i++)
			instances[i] = new InstanceFlowshop(String.format("instances/Flowshop/tai%02d.txt", instanceIndex[i]));
		//*/

		/*
		// TSP instances

		class Filter implements FileFilter{
			public Filter(){

			}

			public boolean accept(File pathname) {
				String path = pathname.getName();
				String type = path.substring(path.length()-3);
				return type.equals("tsp");
			}
		}

		File folder = new File("instances/TSP");
		File[] listOfFiles = folder.listFiles(new Filter());
		Instance[] instances = new Instance[listOfFiles.length];
		for(int i = 0; i<instances.length; i++){
			instances[i] = new InstanceTSP(listOfFiles[i].getPath());
		}
		//*/

		Solver[] solvers = new Solver[]{
				new AntColonyOptimization(instance),
				new BeesAlgorithm(instance),
				new Genetic(instance),
				new Grasp(instance),
				new Ils(instance),
				new Memetic(instance),
				new SimulatedAnnealing(instance),
				new TabuSearch(instance, new Shift(), new Solution(instance)),
				new Vns(instance),
				new WolfPackAlgorithm(instance)
		};
		Parallel parallel = new Parallel(instances[0],2);
		try{
			int nbTimes = 1;
			for(int m = 0; m<nbTimes; m++){
				for(int i = 0; i<instances.length; i++){
					parallel.setInstance(instances[i]);
					System.out.println(instances[i].getName());
					for(int k = 0; k<solvers.length; k++){
						if(m>0 && !(solvers[k] instanceof Ils || solvers[k] instanceof Vns)){ // the results are always the same for a given instance for these two algorithms
							parallel.setSolver(solvers[k]);
							System.out.println(solvers[k].getName());
							Benchmark.presentation(solvers[k],instances[i].getName());
							timer.reset();
							parallel.solve(timer);
						}
					}
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
