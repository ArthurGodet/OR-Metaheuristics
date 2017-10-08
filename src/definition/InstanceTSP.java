/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package definition;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Representation of an instance for the Traveling Salesman Problem.
 */
public class InstanceTSP extends Instance{
	
	/** The number of cities. */
	private int nbCities;
	
	/** The distances between cities. */
	private double[][] distance;
	
	
	/**
	 * Builds the instance from a text file with a certain format.
	 *
	 * @param s name of the file
	 */
	public InstanceTSP(String s) {
		this.problem = "Traveling Salesman Problem";
		this.name = s;
		try {
			Scanner scanner = new Scanner(new FileReader(s));

			// reads the number of cities
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

			// computes the distance between cities from the coordinates
			this.distance = new double[this.nbCities][this.nbCities];
			for(int i = 0; i<this.nbCities; i++){
				for(int j = 0; j<this.nbCities; j++){
					this.distance[i][j] = Math.sqrt(Math.pow(coordinates[i][0]-coordinates[j][0], 2.0) + Math.pow(coordinates[i][1]-coordinates[j][1], 2.0));
				}
			}
			scanner.close();
		}
		catch (IOException e) {
			System.err.println("Error : " + e.getMessage()) ;
			System.exit(2) ;
		}
	}

	/* (non-Javadoc)
	 * @see definition.Instance#getSize()
	 */
	@Override
	public int getSize() {
		return nbCities;
	}
	
	/**
	 * Gets the distance.
	 *
	 * @param city1 the city 1
	 * @param city2 the city 2
	 * @return the distance
	 */
	public double getDistance(int city1, int city2){
		return this.distance[city1][city2];
	}
}
