package definition;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Instance {
	private int nbJobs;
	private int nbMachines;
	private int[][] dureesOperations;
	
	// cree un probleme a partir d'un fichier
	public Instance(String s) {
		try {
			Scanner scanner = new Scanner(new FileReader(s));

			// lecture du nombre de jobs
			if (scanner.hasNextInt()) {
				this.nbJobs = scanner.nextInt();
			}

			// lecture du nombre de machines
			if (scanner.hasNextInt()) {
				this.nbMachines = scanner.nextInt();
			}

			this.dureesOperations = new int[this.nbJobs][this.nbMachines];
			int i = 0; // indice du job
			int j = 0; // indice de l'operation
			while (scanner.hasNextInt()) {
				this.dureesOperations[i][j] = scanner.nextInt();
				//System.out.println(j + " " + d[j]);
				if (j < nbMachines - 1) {
					j++; // operation suivante
				}
				else { // sinon on cree le job et on passe au suivant
					i++;
					j = 0;
				}
			}
			scanner.close();
		}
		catch (IOException e) {
			System.err.println("Erreur : " + e.getMessage()) ;
			System.exit(2) ;
		}
	}

	public int getNbJobs() {
		return nbJobs;
	}

	public int getNbMachines() {
		return nbMachines;
	}
	
	public int getDureeOperation(int job, int machine){
		return this.dureesOperations[job][machine];
	}
}
