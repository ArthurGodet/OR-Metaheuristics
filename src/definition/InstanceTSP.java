package definition;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InstanceTSP extends Instance{
	private int nbVilles;
	
	private double[][] distance;
	
	
	/**
	 * Construit un problème à partir d'un fichier.
	 *
	 * @param s nom du fichier
	 */
	public InstanceTSP(String s) {
		this.problem = "Flowshop de permutation";
		this.name = s;
		try {
			Scanner scanner = new Scanner(new FileReader(s));

			// lecture du nombre de jobs
			if (scanner.hasNextInt())
				this.nbVilles = scanner.nextInt();

			this.distance = new double[this.nbVilles][this.nbVilles];
			int ville1 = 0;
			int ville2 = 0;
			while (scanner.hasNextInt()) {
				this.distance[ville1][ville2] = scanner.nextDouble();
				if (ville2 < nbVilles - 1)
					ville2++;
				else {
					ville1++;
					ville2 = 0;
				}
			}
			scanner.close();
		}
		catch (IOException e) {
			System.err.println("Erreur : " + e.getMessage()) ;
			System.exit(2) ;
		}
	}

	@Override
	public int getSize() {
		return nbVilles;
	}
	
	public double getDistance(int ville1, int ville2){
		return this.distance[ville1][ville2];
	}
}
