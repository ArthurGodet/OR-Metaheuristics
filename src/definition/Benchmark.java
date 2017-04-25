/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package definition;

import java.io.FileWriter;
import java.io.IOException;

import metaheuristics.Solver;

// TODO: Auto-generated Javadoc
/**
 * The Class Benchmark.
 */
public class Benchmark {
	
	/**
	 * Write results.
	 *
	 * @param solver the solver
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeResults(Solver solver) throws IOException{
		FileWriter fw = new FileWriter("results/"+System.getProperty("user.name")+"/"+solver.getName()+".txt",true);
		fw.write(solver.getSolution().getCmax() + "\n");
		fw.close();
	}
	
	/**
	 * Presentation.
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
