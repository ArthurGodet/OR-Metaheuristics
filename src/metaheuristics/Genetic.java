package metaheuristics;

import java.util.ArrayList;

import definition.Instance;
import definition.Neighborhood;
import definition.Solution;

/**
 * Solver utilisant un algorithme génétique
 */
public class Genetic extends Solver{

	public Genetic(Instance inst) {
		super(inst, "Genetic");
	}
	
	public Solution onePointCrossover (Solution parent1, Solution parent2){
		int coupure = 0;
		do{
			coupure = (int) Math.random()*super.getInstance().getNbJobs();
		}while(coupure == parent1.getInstance().getNbJobs()-2);
		
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
	
	public Solution twoPointCrossoverSepares(Solution parent1, Solution parent2){
		int coupureDebut=0;
		int coupureFin=0;
		do{
			coupureDebut = (int) Math.random()*(super.getInstance().getNbJobs());
			coupureFin = (int) Math.random()*super.getInstance().getNbJobs();
		}while(coupureDebut==0 && coupureFin==parent2.getInstance().getNbJobs()-1
				|| coupureDebut == coupureFin);
		
		Solution child = new Solution(super.getInstance());
		
		for(int i=0;i<coupureDebut ; i++){
			child.setOrder(parent1.getJob(i), i);
		}
		for(int i= coupureFin ;i<parent2.getInstance().getNbJobs();i++){
			child.setOrder(parent1.getJob(i), i);
		}
		int indexAInserer = coupureDebut;
		for(int i=0 ; i<parent2.getInstance().getNbJobs() ;i++){
			if( !child.contains(parent2.getJob(i))){
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
			coupureDebut = (int) Math.random()*super.getInstance().getNbJobs();
			coupureFin = (int) Math.random()*(super.getInstance().getNbJobs());
			
		}while(coupureDebut==0 && coupureFin==parent2.getInstance().getNbJobs()-1
				|| coupureDebut == coupureFin);
		Solution child = new Solution(super.getInstance());
		
		for(int i=coupureDebut;i<coupureFin ; i++){
			child.setOrder(parent1.getJob(i), i);
		}
		
		int indexAInserer = 0;
		for(int i=0 ; i<parent2.getInstance().getNbJobs() ;i++){
			if( !child.contains(parent2.getJob(i))){
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
			nbCoupure=(int) Math.random()*super.getInstance().getNbJobs();
		}while(nbCoupure==0 || nbCoupure == parent1.getInstance().getNbJobs()-1);
		
		ArrayList<Integer> coupures = new ArrayList<Integer>();
		int indexCoupure=0;
		for(int i=0;i<nbCoupure ; i++){
			do{
				indexCoupure = (int) Math.random()*parent1.getInstance().getNbJobs();
			}while(coupures.contains(indexCoupure));
			coupures.add(indexCoupure);
		}
		Solution child = new Solution(super.getInstance());
		
		int pos =0;
		for(int i=0 ; i<parent2.getInstance().getNbJobs() ;i++){
			if( !child.contains(parent2.getJob(i))){
				while(child.getJob(pos)!=-1){
					pos++;
				}
				child.setOrder(parent2.getJob(i), pos);	
			}
		}
		child.evaluate();
		return child;
		
	}

	public void mutation (Solution child, Neighborhood nh){
		int pos1=0;
 		int pos2=0;
 		int pos3 =0;
		switch (nh){
		 	
		 	case SWAP :
		 		do{
		 			pos1 = (int) Math.random()*child.getInstance().getNbJobs();
		 			pos2 = (int) Math.random()*child.getInstance().getNbJobs();
		 		}while( pos1 == pos2);
		 		child.swap(pos1, pos2); break;
		 		
		 	case CHANGE :
		 		do{
		 			pos1 = (int) Math.random()*child.getInstance().getNbJobs();
		 			pos2 = (int) Math.random()*child.getInstance().getNbJobs();
			 	}while (pos1 == pos2 );
		 		
		 		do{
		 			pos3 = (int) Math.random()*child.getInstance().getNbJobs();
		 		} while(pos3 == pos1 && pos3==pos2);
	 			child.change(pos1, pos2, pos3);break;
	 			
		 	case SHIFT :
		 		do{
		 			pos1 = (int) Math.random()*child.getInstance().getNbJobs();
		 			pos2 = (int) Math.random()*child.getInstance().getNbJobs();
			 	}while (pos1 >= pos2 );
		 		
		 		child.rightShift(pos1,pos2); break;
		 }
	}
	
	public void solve() {
		// TODO Auto-generated method stub
		
	}

}
