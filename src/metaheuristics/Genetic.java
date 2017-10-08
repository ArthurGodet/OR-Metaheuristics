/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import crossovers.TwoPointOut;
import definition.Crossover;
import definition.Instance;
import definition.Neighborhood;
import definition.Solution;
import neighborhoods.Shift;
import util.Timer;

/**
 * Implementation of a Genetic algorithm.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class Genetic extends Solver{
	
	/** The Constant PROBA_MUTATION. */
	public static double PROBA_MUTATION = 0.001;
	
	/** The Constant CROSSOVER_RATIO. */
	public static double CROSSOVER_RATIO = 0.85;
	
	/** The Constant POPULATION_SIZE. */
	public static int POPULATION_SIZE = 100;

	/** The probability of mutation. */
	protected double probaMutation;
	
	/** The crossover ratio. */
	protected double crossoverRatio;
	
	/** The type of neighborhood. */
	protected Neighborhood nh;
	
	/** The crossover technique. */
	protected Crossover cross;

	/**
	 * Instantiates a new solver applying the genetic algorithm.
	 *
	 * @param inst the instance
	 * @param nh the type of neighborhood
	 * @param c the crossover technique
	 * @param probaMutation the probability of mutation
	 * @param crossoverRatio the crossover ratio
	 */
	public Genetic(Instance inst, Neighborhood nh, Crossover c, double probaMutation, double crossoverRatio) {
		super(inst, "Genetic");
		this.probaMutation = probaMutation;
		this.crossoverRatio = crossoverRatio;
		this.nh = nh;
		this.cross = c;
	}

	/**
	 * Instantiates a new solver applying the genetic algorithm.
	 *
	 * @param inst the instance
	 * @param nh the type of neighborhood
	 * @param c the crossover ratio
	 */
	public Genetic(Instance inst, Neighborhood nh, Crossover c) {
		this(inst, nh, c, PROBA_MUTATION, CROSSOVER_RATIO);
	}
	
	/**
	 * Instantiates a new solver applying the genetic algorithm.
	 *
	 * @param inst the instance
	 */
	public Genetic(Instance inst){
		this(inst, new Shift(), new TwoPointOut());
	}

	/**
	 * Does a mutation if it happens.
	 *
	 * @param child the child
	 * @return the child after a potential mutation
	 */
	public Solution mutation(Solution child){
		if(Math.random()<this.probaMutation) // checks if the mutation happens
			nh.assignRandomNeighbor(child);
		return child;
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		this.setSolution(Greedy.solve(this.getInstance()));

		while(!timer.isFinished()){
			// Generates the initial population
			ArrayList<Solution> population = this.generateInitialPopulation();
			
			// Applies the genetic algorithm until the timer is finished or 95% of the population have the same score
			while(population.get(0).getScore() != population.get((int)(population.size()*0.95)).getScore() && !timer.isFinished()){
				ArrayList<Solution> newGeneration = this.generateNewGeneration(population);
				
				Collections.sort(newGeneration); // Sorts the new population by increasing score
				population = newGeneration;
				if(this.getSolution().getScore() > population.get(0).getScore()){
					this.setSolution(population.get(0).clone()); // keeps the best solution found so far
				}
			}
		}
	}
	
	/**
	 * Generates an initial generation of the population.
	 *
	 * @return the new generation
	 */
	protected ArrayList<Solution> generateInitialPopulation(){
		ArrayList<Solution> population = new ArrayList<Solution>();
		population.add(this.getSolution());
		for(int i = 1; i<Genetic.POPULATION_SIZE; i++)
			population.add(Solution.generateSolution(this.getInstance()));

		Collections.sort(population); // sorts the population by increasing score
		return population;
	}
	
	/**
	 * Generates a new generation of the population.
	 *
	 * @param population the population
	 * @return the new generation
	 */
	protected ArrayList<Solution> generateNewGeneration(List<Solution> population){
		ArrayList<Solution> newGeneration = new ArrayList<Solution>();
		newGeneration.addAll(population.subList(0,(int)(POPULATION_SIZE*(1.-this.crossoverRatio))-1)); // keep best 15% ---> elitism
		for(int i = (int)(POPULATION_SIZE*(1.-crossoverRatio))-1; i<population.size(); i++){
			Solution parent1 = this.groupSelection(population);
			Solution parent2 = this.groupSelection(population);
			newGeneration.add(this.mutation(this.cross.crossover(parent1, parent2)));
		}
		return newGeneration;
	}
	
	/**
	 * Picks a solution from the population with a non-uniform probability.
	 *
	 * @param popu the population
	 * @return the picked solution
	 */
	public Solution groupSelection(List<Solution> popu){
		double pick = Math.random();
		int popSize = popu.size();
		List<Solution> subList;
		if(pick > 0.5)
			subList = popu.subList(0,popSize/4);
		else if(pick > 0.2)
			subList = popu.subList(popSize/4,popSize/2);
		else if(pick > 0.05)
			subList = popu.subList(popSize/2,3*popSize/4);
		else
			subList = popu.subList(3*popSize/4,popSize);
		
		return subList.get((int)(Math.random()*subList.size()));
	}
}
