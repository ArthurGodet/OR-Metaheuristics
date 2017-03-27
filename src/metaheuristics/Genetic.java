package metaheuristics;

import definition.Instance;
import definition.Solution;

public class Genetic extends Solver{

	public Genetic(Instance inst) {
		super(inst);
	}
	
	public Solution onePointCrossover (Solution parent1, Solution parent2){
		int coupure = (int) Math.random()*super.getInstance().getNbJobs();
		Solution child = new Solution(super.getInstance());
		
		for(int i=0;i<coupure ; i++){
			child.setOrder(parent1.getJob(i), i);
		}
		int indexAInserer = coupure;
		for(int i=0 ; i<parent2.getInstance().getNbJobs() ;i++){
			if( !child.contains(parent2.getJob(i))){
				child.setOrder(parent2.getJob(i), indexAInserer);
				indexAInserer++;
			}
		}
		child.evaluate();
		return child;
	}

	@Override
	public void solve() {
		// TODO Auto-generated method stub
		
	}

}
