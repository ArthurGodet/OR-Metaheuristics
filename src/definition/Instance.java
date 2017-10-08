/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package definition;

/**
 * Abstract class for the different type of instances. Each type of instance represents a certain
 * problem.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public abstract class Instance {
	/** The name of the specific instance of the problem. */
	protected String name;
	/** The name of the problem. */
	protected String problem;
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
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
