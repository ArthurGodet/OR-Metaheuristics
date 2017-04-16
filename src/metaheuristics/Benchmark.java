package metaheuristics;

import java.io.FileWriter;
import java.io.IOException;

public class Benchmark {
	public static void writeResults(Solver solver) throws IOException{
		FileWriter fw = new FileWriter("results/"+solver.getName()+".txt",true);
		fw.write(solver.getSolution().getCmax() + "\n");
		fw.close();
	}
	
	public static void presentation(Solver solver, String instance) throws IOException{
		FileWriter fw = new FileWriter("results/"+solver.getName()+".txt",true);
		fw.write(instance+"\n");
		fw.close();
	}
	
	public static void EndPresentation(Solver solver) throws IOException{
		FileWriter fw = new FileWriter("results/"+solver.getName()+".txt",true);
		fw.write("-----------"+"\n");
		fw.close();
	}
}
