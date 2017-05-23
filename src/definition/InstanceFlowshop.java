/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package definition;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * Représentation d'un problème de flowshop de permutation.
 */
public class InstanceFlowshop extends Instance{
	
	/** The nb jobs. */
	private int nbJobs;
	
	/** The nb machines. */
	private int nbMachines;
	
	/** The durees operations. */
	private int[][] dureesOperations;
	
	/** The name of the instance. */
	private String name;

	/**
	 * Construit un problème à partir d'un fichier.
	 *
	 * @param s nom du fichier
	 */
	public InstanceFlowshop(String s) {
		this.name = s;
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
	
	/**
	 * Gets the name of the Instance.
	 *
	 * @return the name of the Instance
	 */
	public String getName(){
		return this.name;
	}

	@Override
	public int getSize() {
		return this.nbJobs;
	}
}
