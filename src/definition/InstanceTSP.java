package definition;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InstanceTSP extends Instance{
	private int nbCities;
	
	private int[][] distance;
	
	
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
				this.nbCities = scanner.nextInt();

			this.distance = new int[this.nbCities][this.nbCities];
			int city1 = 0;
			int city2 = 0;
			while (scanner.hasNextInt()) {
				this.distance[city1][city2] = scanner.nextInt();
				if (city2 < nbCities - 1)
					city2++;
				else {
					city1++;
					city2 = 0;
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
		return nbCities;
	}
	
	public int getDistance(int city1, int city2){
		return this.distance[city1][city2];
	}
}
