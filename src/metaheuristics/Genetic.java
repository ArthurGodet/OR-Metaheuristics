package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import definition.Crossover;
import definition.Instance;
import definition.Neighborhood;
import definition.Solution;
import util.Random;

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
		this(inst, Neighborhood.SHIFT, Crossover.TWO_POINTS_CROSSOVER_SEPARES);
	}

	/**
	 * One point crossover.
	 *
	 * @param parent1 the parent 1
	 * @param parent2 the parent 2
	 * @return the solution
	 */
	public Solution onePointCrossover(Solution parent1, Solution parent2){
		Solution child = new Solution(this.getInstance());
		int coupure = Random.randomInteger(1, this.getInstance().getNbJobs());

		// ajoute les premiers jobs du parent1
		for(int i=0; i<coupure; i++)
			child.setOrder(parent1.getJob(i), i);

		// ajoute les jobs restants dans le même ordre que dans parent2
		int indexAInserer = coupure;
		for(int i=0; i<this.getInstance().getNbJobs(); i++)
			if(!child.contains(parent2.getJob(i)))
				child.setOrder(parent2.getJob(i), indexAInserer++);

		child.evaluate();
		return child;
	}

	/**
	 * Two point crossover separes.
	 *
	 * @param parent1 the parent 1
	 * @param parent2 the parent 2
	 * @return the solution
	 */
	public Solution twoPointCrossoverSepares(Solution parent1, Solution parent2){
		Solution child = new Solution(this.getInstance());
		int[] coupure = Random.randomTwoPoints(0, this.getInstance().getNbJobs());

		// ajoute les premiers jobs du parent1
		for(int i=0; i<coupure[0]; i++)
			child.setOrder(parent1.getJob(i), i);
		// ajoute les derniers jobs du parent1
		for(int i=coupure[1]; i<this.getInstance().getNbJobs(); i++)
			child.setOrder(parent1.getJob(i), i);

		// ajoute les jobs restants dans le même ordre que dans parent2
		int indexAInserer = coupure[0];
		for(int i=0; i<this.getInstance().getNbJobs(); i++)
			if(!child.contains(parent2.getJob(i)))
				child.setOrder(parent2.getJob(i), indexAInserer++);

		child.evaluate();
		return child;
	}

	/**
	 * Two point crossover ensemble.
	 *
	 * @param parent1 the parent 1
	 * @param parent2 the parent 2
	 * @return the solution
	 */
	public Solution twoPointCrossoverEnsemble(Solution parent1, Solution parent2){
		Solution child = new Solution(this.getInstance());
		int[] coupure = Random.randomTwoPoints(0, this.getInstance().getNbJobs());

		// ajoute les jobs centraux du parent1
		for(int i=coupure[0]; i<coupure[1]; i++)
			child.setOrder(parent1.getJob(i), i);

		// ajoute les jobs restants dans le même ordre que dans parent2
		int indexAInserer = 0;
		for(int i=0; i<this.getInstance().getNbJobs(); i++){
			if(!child.contains(parent2.getJob(i))){
				child.setOrder(parent2.getJob(i), indexAInserer++);
				if(indexAInserer == coupure[0])
					indexAInserer = coupure[1];
			}
		}

		child.evaluate();
		return child;
	}

	/**
	 * Position based crossover.
	 *
	 * @param parent1 the parent 1
	 * @param parent2 the parent 2
	 * @return the solution
	 */
	public Solution positionBasedCrossover(Solution parent1, Solution parent2){
		Solution child = new Solution(this.getInstance());
		int nbHerites = Random.randomInteger(1, this.getInstance().getNbJobs());

		// ajoute des jobs choisis aléatoirement depuis parent1
		for(Integer i : Random.randomSample(0, this.getInstance().getNbJobs(), nbHerites))
			child.setOrder(parent1.getJob(i),i);

		// ajoute les jobs restants dans le même ordre que dans parent2
		int indexAInserer = 0;
		for(int i=0; i<this.getInstance().getNbJobs(); i++){
			if(!child.contains(parent2.getJob(i))){
				while(child.getJob(indexAInserer)!=-1)
					indexAInserer++;
				child.setOrder(parent2.getJob(i), indexAInserer);
			}
		}

		child.evaluate();
		return child;
	}

	/**
	 * Mutation.
	 *
	 * @param child the child
	 * @return the solution
	 */
	public Solution mutation(Solution child){
		int pos1=0;
		int pos2=0;
		int pos3=0;
		double rand = Math.random();
		if(rand<this.probaMutation){
			switch(this.nh){
			case SWAP :
				do{
					pos1 = (int)(Math.random()*child.getInstance().getNbJobs());
					pos2 = (int)(Math.random()*child.getInstance().getNbJobs());
				}while(pos1 == pos2);
				child.swap(pos1, pos2); break;
			case CHANGE :
				do{
					pos1 = (int)(Math.random()*child.getInstance().getNbJobs());
					pos2 = (int)(Math.random()*child.getInstance().getNbJobs());
				}while (pos1 == pos2);

				do{
					pos3 = (int)(Math.random()*child.getInstance().getNbJobs());
				}while(pos3==pos1 || pos3==pos2);
				child.change(pos1, pos2, pos3); break;

			default :
				do{
					pos1 = (int)(Math.random()*child.getInstance().getNbJobs());
					pos2 = (int)(Math.random()*child.getInstance().getNbJobs());
				}while(pos1 == pos2);

				child.rightShift(Math.min(pos1,pos2),Math.max(pos1,pos2)); break;
			}
			child.evaluate();
		}
		return child;
	}

	/* (non-Javadoc)
	 * @see metaheuristics.Solver#solve()
	 */
	public void solve() {
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
			
			Collections.sort(newGeneration); // trie la nouvelle population par ordre croissant de Cmax
			population = newGeneration;
			if(this.getSolution().getCmax() > population.get(0).getCmax()){
				this.setSolution(population.get(0).clone());
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
