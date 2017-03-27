package metaheuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import definition.Crossover;
import definition.Instance;
import definition.Neighborhood;
import definition.Solution;

/**
 * Solver utilisant un algorithme génétique
 */
public class Genetic extends Solver{
	public static double PROBA_MUTATION = 0.001;
	public static double CROSSOVER_RATIO = 0.85;
	public static int POPULATION_SIZE = 100;

	protected double probaMutation;
	protected double crossoverRatio;
	protected Neighborhood nh;
	protected Crossover cross;

	public Genetic(Instance inst, Neighborhood nh, Crossover c, double probaMutation, double crossoverRatio) {
		super(inst, "Genetic");
		this.probaMutation = probaMutation;
		this.crossoverRatio = crossoverRatio;
		this.nh = nh;
		this.cross = c;
	}

	public Genetic(Instance inst, Neighborhood nh, Crossover c) {
		this(inst, nh, c, PROBA_MUTATION, CROSSOVER_RATIO);
	}
	
	public Genetic(Instance inst){
		this(inst, Neighborhood.SHIFT, Crossover.TWO_POINTS_CROSSOVER_SEPARES);
	}

	public Solution onePointCrossover(Solution parent1, Solution parent2){
		int coupure = 0;
		do{
			coupure = (int)(Math.random()*this.getInstance().getNbJobs());
		}while(coupure == parent1.getInstance().getNbJobs()-1);

		Solution child = new Solution(this.getInstance());

		for(int i=0;i<coupure ; i++){
			child.setOrder(parent1.getJob(i), i);
		}
		int indexAInserer = coupure;
		for(int i=0 ; i<parent2.getInstance().getNbJobs(); i++){
			if(!child.contains(parent2.getJob(i))){
				child.setOrder(parent2.getJob(i), indexAInserer);
				indexAInserer++;
			}
		}
		child.evaluate();
		return child;
	}

	public Solution twoPointCrossoverSepares(Solution parent1, Solution parent2){
		int coupureDebut=0;
		int coupureFin=0;
		do{
			coupureDebut = (int)(Math.random()*this.getInstance().getNbJobs());
			coupureFin = (int)(Math.random()*this.getInstance().getNbJobs());
		}while(coupureDebut==0 && coupureFin==parent2.getInstance().getNbJobs()-1 || coupureDebut == coupureFin);

		Solution child = new Solution(this.getInstance());

		for(int i=0; i<coupureDebut; i++){
			child.setOrder(parent1.getJob(i), i);
		}
		for(int i=coupureFin; i<parent2.getInstance().getNbJobs(); i++){
			child.setOrder(parent1.getJob(i), i);
		}
		int indexAInserer = coupureDebut;
		for(int i=0; i<parent2.getInstance().getNbJobs(); i++){
			if(!child.contains(parent2.getJob(i))){
				child.setOrder(parent2.getJob(i), indexAInserer);
				indexAInserer++;
			}
		}
		child.evaluate();
		return child;
	}

	public Solution twoPointCrossoverEnsemble(Solution parent1, Solution parent2){
		int coupureDebut = 0;
		int coupureFin = 0;
		do{
			coupureDebut = (int)(Math.random()*this.getInstance().getNbJobs());
			coupureFin = (int)(Math.random()*this.getInstance().getNbJobs());

		}while(coupureDebut==0 && coupureFin==parent2.getInstance().getNbJobs()-1 || coupureDebut == coupureFin);

		Solution child = new Solution(this.getInstance());

		for(int i=coupureDebut; i<coupureFin; i++){
			child.setOrder(parent1.getJob(i), i);
		}

		int indexAInserer = 0;
		for(int i=0; i<parent2.getInstance().getNbJobs(); i++){
			if(!child.contains(parent2.getJob(i))){
				if(indexAInserer < coupureDebut){
					child.setOrder(parent2.getJob(i), indexAInserer);
					indexAInserer++;
				}
				else{
					indexAInserer = Math.max(coupureFin, indexAInserer);
					child.setOrder(parent2.getJob(i), indexAInserer);
					indexAInserer++;
				}

			}
		}
		child.evaluate();
		return child;
	}

	public Solution positionBasedCrossover(Solution parent1, Solution parent2){
		int nbCoupure = 0;
		do{
			nbCoupure = (int)(Math.random()*this.getInstance().getNbJobs());
		}while(nbCoupure==0 || nbCoupure == parent1.getInstance().getNbJobs()-1);

		ArrayList<Integer> coupures = new ArrayList<Integer>();
		int indexCoupure=0;
		for(int i=0;i<nbCoupure ; i++){
			do{
				indexCoupure = (int)(Math.random()*parent1.getInstance().getNbJobs());
			}while(coupures.contains(indexCoupure));
			coupures.add(indexCoupure);
		}

		Solution child = new Solution(this.getInstance());

		for(Integer i : coupures)
			child.setOrder(parent1.getJob(i),i);

		int pos = 0;
		for(int i=0; i<parent2.getInstance().getNbJobs(); i++){
			if(!child.contains(parent2.getJob(i))){
				while(child.getJob(pos)!=-1){
					pos++;
				}
				child.setOrder(parent2.getJob(i), pos);	
			}
		}
		child.evaluate();
		return child;

	}

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
	 * Pick a solution from the population with a non-uniform probability
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
