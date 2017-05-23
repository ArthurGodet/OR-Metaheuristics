/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
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

// TODO: Auto-generated Javadoc
/**
 * Solver utilisant un algorithme génétique.
 */
public class Genetic extends Solver{
	
	/** The proba mutation. */
	public static double PROBA_MUTATION = 0.001;
	
	/** The crossover ratio. */
	public static double CROSSOVER_RATIO = 0.85;
	
	/** The population size. */
	public static int POPULATION_SIZE = 100;

	/** The proba mutation. */
	protected double probaMutation;
	
	/** The crossover ratio. */
	protected double crossoverRatio;
	
	/** The nh. */
	protected Neighborhood nh;
	
	/** The cross. */
	protected Crossover cross;

	/**
	 * Instantiates a new genetic.
	 *
	 * @param inst the inst
	 * @param nh the nh
	 * @param c the c
	 * @param probaMutation the proba mutation
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
	 * Instantiates a new genetic.
	 *
	 * @param inst the inst
	 * @param nh the nh
	 * @param c the c
	 */
	public Genetic(Instance inst, Neighborhood nh, Crossover c) {
		this(inst, nh, c, PROBA_MUTATION, CROSSOVER_RATIO);
	}
	
	/**
	 * Instantiates a new genetic.
	 *
	 * @param inst the inst
	 */
	public Genetic(Instance inst){
		this(inst, new Shift(), new TwoPointOut());
	}

	/**
	 * Mutation.
	 *
	 * @param child the child
	 * @return the solution
	 */
	public Solution mutation(Solution child){
		if(Math.random()<this.probaMutation)
			nh.assignRandomNeighbor(child);
		return child;
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve(util.Timer)
	 */
	public void solve(Timer timer) {
		this.setSolution(Neh.solve(this.getInstance()));

		while(!timer.isFinished()){
			// Génération de la population initiale
			ArrayList<Solution> population = new ArrayList<Solution>();
			population.add(this.getSolution());
			for(int i = 1; i<Genetic.POPULATION_SIZE; i++)
				population.add(Solution.generateSolution(this.getInstance()));

			Collections.sort(population); // trie la population par ordre croissant de Cmax
			
			// Application de l'algorithme génétique
			while(population.get(0).getScore() != population.get((int)(population.size()*0.95)).getScore() && !timer.isFinished()){
				ArrayList<Solution> newGeneration = new ArrayList<Solution>();
				newGeneration.addAll(population.subList(0,(int)(POPULATION_SIZE*(1.-this.crossoverRatio))-1)); // keep best 15% ---> elitism
				for(int i = (int)(POPULATION_SIZE*(1.-crossoverRatio))-1; i<population.size(); i++){
					Solution parent1 = this.groupSelection(population);
					Solution parent2 = this.groupSelection(population);
					newGeneration.add(this.mutation(this.cross.crossover(parent1, parent2)));
				}
				
				Collections.sort(newGeneration); // trie la nouvelle population par ordre croissant de Cmax
				population = newGeneration;
				if(this.getSolution().getScore() > population.get(0).getScore()){
					this.setSolution(population.get(0).clone());
				}
			}
		}
	}
	
	/**
	 * Pick a solution from the population with a non-uniform probability.
	 *
	 * @param popu the popu
	 * @return the solution
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
