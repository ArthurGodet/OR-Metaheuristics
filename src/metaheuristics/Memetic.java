/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;

import crossovers.TwoPointOut;
import definition.Crossover;
import definition.Instance;
import definition.Neighborhood;
import definition.Solution;
import neighborhoods.Shift;
import util.Timer;

/**
 * Implementation of the Memetic metaheuristic. It consists of a genetic algorithm with local
 * search applied on every created child.
 */
public class Memetic extends Genetic{

	/**
	 * Instantiates a new Memetic solver.
	 *
	 * @param inst the instance
	 * @param nh the type of neighborhood
	 * @param c the crossover technique
	 * @param probaMutation the probability of mutation
	 * @param crossoverRatio the crossover ratio
	 */
	public Memetic(Instance inst, Neighborhood nh, Crossover c, double probaMutation, double crossoverRatio) {
		super(inst, nh, c, probaMutation, crossoverRatio);
		this.setName("Memetic");
	}

	/**
	 * Instantiates a new Memetic solver.
	 *
	 * @param inst the instance
	 * @param nh the type of neighborhood
	 * @param c the crossover technique
	 */
	public Memetic(Instance inst, Neighborhood nh, Crossover c) {
		this(inst, nh, c, PROBA_MUTATION, CROSSOVER_RATIO);
	}
	
	/**
	 * Instantiates a new Memetic solver.
	 *
	 * @param inst the instance
	 */
	public Memetic(Instance inst){
		this(inst, new Shift(), new TwoPointOut());
	}
	
	/* (non-Javadoc)
	 * @see metaheuristics.Genetic#solve(util.Timer)
	 */
	public void solve(Timer timer){
		this.setSolution(Greedy.solve(this.getInstance()));

		while(!timer.isFinished()){
			// Generates the initial population
			ArrayList<Solution> population = this.generateInitialPopulation();
			
			// Applies the genetic algorithm
			while(population.get(0).getScore() != population.get((int)(population.size()*0.95)).getScore() && !timer.isFinished()){
				ArrayList<Solution> newGeneration = this.generateNewGeneration(population);
				
				// Applies a local search on each member of the population
				LocalSearch ls = new LocalSearch(this.getInstance(),this.nh,this.getSolution());
				for(int i = 0; i<newGeneration.size(); i++){
					ls.setSolution(newGeneration.remove(i));
					ls.solve();
					newGeneration.add(i,ls.getSolution());
				}
				
				Collections.sort(newGeneration); // Sorts the new population by increasing score
				population = newGeneration;
				if(this.getSolution().getScore() > population.get(0).getScore()){
					this.setSolution(population.get(0).clone());
				}
			}
		}
	}
}
