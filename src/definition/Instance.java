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
public abstract class Instance {
	/** The name of the instance. */
	private String name;
	/** The name of the problem. */
	private String problem;
	
	public abstract int getSize();

	
	/**
	 * Gets the name of the Instance.
	 *
	 * @return the name of the Instance
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gets the name of the problem.
	 *
	 * @return the name of the problem
	 */
	public String getProblemName(){
		return this.problem;
	}
}
