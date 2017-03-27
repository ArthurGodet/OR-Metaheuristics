package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;

import definition.Crossover;
import definition.Instance;
import definition.Neighborhood;
import definition.Solution;

public class Memetic extends Genetic{

	public Memetic(Instance inst, Neighborhood nh, Crossover c, double probaMutation, double crossoverRatio) {
		super(inst, nh, c, probaMutation, crossoverRatio);
		this.setName("Memetic");
	}

	public Memetic(Instance inst, Neighborhood nh, Crossover c) {
		this(inst, nh, c, PROBA_MUTATION, CROSSOVER_RATIO);
	}
	
	public Memetic(Instance inst){
		this(inst, Neighborhood.SHIFT, Crossover.TWO_POINTS_CROSSOVER_SEPARES);
	}
	
	public void solve(){
		Neh neh = new Neh(this.getInstance());
		neh.solve();
		this.setSolution(neh.getSolution());
		System.out.println(neh);
		System.out.println("--------");
		// Génération de la population initiale
		ArrayList<Solution> population = new ArrayList<Solution>();
		population.add(neh.getSolution());
		for(int i = 1; i<Genetic.POPULATION_SIZE; i++)
			population.add(Solution.generateSolution(this.getInstance()));

		Collections.sort(population); // trie la population par ordre croissant de Cmax
		
		// Application de l'algorithme génétique
		while(population.get(0).getCmax() != population.get((int)(population.size()*0.95)).getCmax()){
			ArrayList<Solution> newGeneration = new ArrayList<Solution>();
			newGeneration.addAll(population.subList(0,(int)(POPULATION_SIZE*(1.-this.crossoverRatio))-1)); // keep best 15% ---> elitism
			for(int i = (int)(POPULATION_SIZE*(1.-crossoverRatio))-1; i<population.size(); i++){
				Solution parent1 = this.groupSelection(population);
				Solution parent2 = this.groupSelection(population);
				
				switch(this.cross){
				case ONE_POINT_CROSSOVER : newGeneration.add(this.mutation(this.onePointCrossover(parent1, parent2))); break;
				case TWO_POINTS_CROSSOVER_ENSEMBLE: newGeneration.add(this.mutation(this.twoPointCrossoverEnsemble(parent1, parent2))); break;
				case TWO_POINTS_CROSSOVER_SEPARES: newGeneration.add(this.mutation(this.twoPointCrossoverSepares(parent1, parent2))); break;
				default : newGeneration.add(this.mutation(this.positionBasedCrossover(parent1, parent2))); break;
				}
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
