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

// TODO: Auto-generated Javadoc
/**
 * The Class Memetic.
 */
public class Memetic extends Genetic{

	/**
	 * Instantiates a new memetic.
	 *
	 * @param inst the inst
	 * @param nh the nh
	 * @param c the c
	 * @param probaMutation the proba mutation
	 * @param crossoverRatio the crossover ratio
	 */
	public Memetic(Instance inst, Neighborhood nh, Crossover c, double probaMutation, double crossoverRatio) {
		super(inst, nh, c, probaMutation, crossoverRatio);
		this.setName("Memetic");
	}

	/**
	 * Instantiates a new memetic.
	 *
	 * @param inst the inst
	 * @param nh the nh
	 * @param c the c
	 */
	public Memetic(Instance inst, Neighborhood nh, Crossover c) {
		this(inst, nh, c, PROBA_MUTATION, CROSSOVER_RATIO);
	}
	
	/**
	 * Instantiates a new memetic.
	 *
	 * @param inst the inst
	 */
	public Memetic(Instance inst){
		this(inst, new Shift(), new TwoPointOut());
	}
	
	/* (non-Javadoc)
	 * @see metaheuristics.Genetic#solve(util.Timer)
	 */
	public void solve(Timer timer){
		if(this.getSolution().getJob(0)==-1)
			this.setSolution(Neh.solve(this.getInstance()));

		// Génération de la population initiale
		ArrayList<Solution> population = new ArrayList<Solution>();
		population.add(this.getSolution());
		for(int i = 1; i<Genetic.POPULATION_SIZE; i++)
			population.add(Solution.generateSolution(this.getInstance()));

		Collections.sort(population); // trie la population par ordre croissant de Cmax
		
		// Application de l'algorithme génétique
		while(population.get(0).getCmax() != population.get((int)(population.size()*0.95)).getCmax() && !timer.isFinished()){
			ArrayList<Solution> newGeneration = new ArrayList<Solution>();
			newGeneration.addAll(population.subList(0,(int)(POPULATION_SIZE*(1.-this.crossoverRatio))-1)); // keep best 15% ---> elitism
			for(int i = (int)(POPULATION_SIZE*(1.-crossoverRatio))-1; i<population.size(); i++){
				Solution parent1 = this.groupSelection(population);
				Solution parent2 = this.groupSelection(population);
				newGeneration.add(this.mutation(this.cross.crossover(parent1, parent2)));
			}
			
			LocalSearch ls = new LocalSearch(this.getInstance(),this.nh,this.getSolution());
			for(int i = 0; i<newGeneration.size(); i++){
				ls.setSolution(newGeneration.remove(i));
				ls.solve();
				newGeneration.add(i,ls.getSolution());
			}
			
			Collections.sort(newGeneration); // trie la nouvelle population par ordre croissant de Cmax
			population = newGeneration;
			if(this.getSolution().getCmax() > population.get(0).getCmax()){
				this.setSolution(population.get(0).clone());
			}
		}
	}
}
