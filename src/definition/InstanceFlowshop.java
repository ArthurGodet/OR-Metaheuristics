/**
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package definition;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Representation of an instance for the permutation flow shop problem.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class InstanceFlowshop extends Instance{
	
	/** The number of jobs. */
	private int nbJobs;
	
	/** The number of machines. */
	private int nbMachines;
	
	/** The duration of the different operations. */
	private int[][] durations;

	/**
	 * Builds the instance from a text file with a certain format.
	 *
	 * @param s name of the file.
	 */
	public InstanceFlowshop(String s) {
		this.problem = "Permutation flow shop";
		this.name = s;
		try {
			Scanner scanner = new Scanner(new FileReader(s));

			// reads the number of jobs
			if (scanner.hasNextInt())
				this.nbJobs = scanner.nextInt();

			// reads the number of machines
			if (scanner.hasNextInt())
				this.nbMachines = scanner.nextInt();

			this.durations = new int[this.nbJobs][this.nbMachines];
			int job = 0;
			int machine = 0;
			while (scanner.hasNextInt()) {
				this.durations[job][machine] = scanner.nextInt();
				if (job < nbJobs - 1)
					job++;
				else {
					machine++;
					job = 0;
				}
			}
			scanner.close();
		}
		catch (IOException e) {
			System.err.println("Error : " + e.getMessage()) ;
			System.exit(2) ;
		}
	}

	/**
	 * Gets the number of machines.
	 *
	 * @return the number of machines for this instance
	 */
	public int getNbMachines() {
		return nbMachines;
	}

	/**
	 * Gets the duration of the operation done by the machine for the job.
	 *
	 * @param job the job
	 * @param machine the machine
	 * @return the duration of the operation done by the machine for the job
	 */
	public int getDuration(int job, int machine){
		return this.durations[job][machine];
	}

	/* (non-Javadoc)
	 * @see definition.Instance#getSize()
	 */
	@Override
	public int getSize() {
		return this.nbJobs;
	}
}
