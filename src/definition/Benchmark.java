/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package definition;

import java.io.FileWriter;
import java.io.IOException;

import metaheuristics.Solver;

/**
 * The Class Benchmark contains method for writing results of tests. The methods' behavior is 
 * adapted depending on the meta-heuristic used (Solver objects) and the instance of the problem.
 */
public class Benchmark {
	
	/**
	 * Write results got by the Solver object in the appropriate text file.
	 *
	 * @param solver the solver
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeResults(Solver solver) throws IOException{
		FileWriter fw = new FileWriter("results/"+System.getProperty("user.name")+"/"+solver.getName()+".txt",true);
		fw.write(solver.getSolution().getScore() + "\n");
		fw.close();
	}
	
	/**
	 * Write the name of the instance in the appropriate text file.
	 *
	 * @param solver the solver
	 * @param instance the instance
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void presentation(Solver solver, String instance) throws IOException{
		FileWriter fw = new FileWriter("results/"+System.getProperty("user.name")+"/"+solver.getName()+".txt",true);
		fw.write(instance+"\n");
		fw.close();
	}
}
