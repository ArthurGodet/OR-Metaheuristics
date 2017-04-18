package definition;

import java.io.FileWriter;
import java.io.IOException;

import metaheuristics.Solver;

public class Benchmark {
	public static void writeResults(Solver solver) throws IOException{
		FileWriter fw = new FileWriter("results/"+System.getProperty("user.name")+"/"+solver.getName()+".txt",true);
		fw.write(solver.getSolution().getCmax() + "\n");
		fw.close();
	}
	
	public static void presentation(Solver solver, String instance) throws IOException{
		FileWriter fw = new FileWriter("results/"+System.getProperty("user.name")+"/"+solver.getName()+".txt",true);
		fw.write(instance+"\n");
		fw.close();
	}
}
