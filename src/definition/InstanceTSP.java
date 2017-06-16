package definition;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InstanceTSP extends Instance{
	private int nbCities;
	
	private double[][] distance;
	
	
	/**
	 * Construit un problème à partir d'un fichier.
	 *
	 * @param s nom du fichier
	 */
	public InstanceTSP(String s) {
		this.problem = "Traveler Salesman Problem";
		this.name = s;
		try {
			Scanner scanner = new Scanner(new FileReader(s));

			// lecture du nombre de villes
			for(int i = 0; i<3; i++)
				scanner.nextLine();
			this.nbCities = new Integer(scanner.nextLine().substring(11));
			scanner.nextLine();
			scanner.nextLine();
			
			int[][] coordinates = new int[this.nbCities][2];
			int index = 0;
			while(scanner.hasNextInt()){
				scanner.nextInt();
				coordinates[index][0] = scanner.nextInt();
				coordinates[index][1] = scanner.nextInt();
				index++;
			}

			this.distance = new double[this.nbCities][this.nbCities];
			for(int i = 0; i<this.nbCities; i++){
				for(int j = 0; j<this.nbCities; j++){
					this.distance[i][j] = Math.sqrt(Math.pow(coordinates[i][0]-coordinates[j][0], 2.0) + Math.pow(coordinates[i][1]-coordinates[j][1], 2.0));
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
	
	public double getDistance(int city1, int city2){
		return this.distance[city1][city2];
	}
}
