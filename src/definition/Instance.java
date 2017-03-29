package definition;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * Représentation d'un problème de flowshop de permutation.
 */
public class Instance {
	
	/** The nb jobs. */
	private int nbJobs;
	
	/** The nb machines. */
	private int nbMachines;
	
	/** The durees operations. */
	private int[][] dureesOperations;

	/**
	 * Construit un problème à partir d'un fichier.
	 *
	 * @param s nom du fichier
	 */
	public Instance(String s) {
		try {
			Scanner scanner = new Scanner(new FileReader(s));

			// lecture du nombre de jobs
			if (scanner.hasNextInt())
				this.nbJobs = scanner.nextInt();

			// lecture du nombre de machines
			if (scanner.hasNextInt())
				this.nbMachines = scanner.nextInt();

			this.dureesOperations = new int[this.nbJobs][this.nbMachines];
			int job = 0;
			int machine = 0;
			while (scanner.hasNextInt()) {
				this.dureesOperations[job][machine] = scanner.nextInt();
				if (machine < nbMachines - 1)
					machine++;
				else {
					job++;
					machine = 0;
				}
			}
			scanner.close();
		}
		catch (IOException e) {
			System.err.println("Erreur : " + e.getMessage()) ;
			System.exit(2) ;
		}
	}

	/**
	 * Gets the nb jobs.
	 *
	 * @return le nombre de jobs du problème
	 */
	public int getNbJobs() {
		return nbJobs;
	}

	/**
	 * Gets the nb machines.
	 *
	 * @return le nombre de machines du problème
	 */
	public int getNbMachines() {
		return nbMachines;
	}

	/**
	 * Gets the duree operation.
	 *
	 * @param job the job
	 * @param machine the machine
	 * @return la durée de traitement d'un job sur une machine
	 */
	public int getDureeOperation(int job, int machine){
		return this.dureesOperations[job][machine];
	}
}
