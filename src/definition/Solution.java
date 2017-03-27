package definition;

public class Solution implements Comparable<Solution>{
	private int[] order;
	private int cmax;
	private Instance instance;

	public Solution(Instance i){
		this.instance = i;
		this.order = new int[i.getNbJobs()];
		for(int j = 0; j<this.order.length; j++)
			this.order[j] = -1;
		this.cmax = Integer.MAX_VALUE;
	}
	
	public int getJob(int pos){
		return this.order[pos];
	}

	public void setOrder(int[] o){
		this.order = o.clone();
	}
	
	public void setOrder(int job, int pos){
		this.order[pos] = job;
	}
	
	public int getIndex(int job){
		for(int i = 0; i<this.order.length; i++)
			if(this.order[i] == job)
				return i;
		return -1;
	}

	public int getCmax() {
		return cmax;
	}
	
	public Instance getInstance(){
		return this.instance;
	}
	
	public void evaluate(){
		int[] dateDisponibilite = new int[this.instance.getNbMachines()];
		for(int i = 0; i<this.order.length && this.order[i] != -1; i++){
			dateDisponibilite[0] += this.instance.getDureeOperation(this.order[i],0);
			for(int j = 1; j<this.instance.getNbMachines(); j++){
				dateDisponibilite[j] = Math.max(dateDisponibilite[j],dateDisponibilite[j-1]) + this.instance.getDureeOperation(this.order[i],j);
			}
		}
		this.cmax = dateDisponibilite[this.instance.getNbMachines()-1];
	}

	public int compareTo(Solution s) {
		return Integer.compare(this.getCmax(),s.getCmax());
	}
	
	public String toString(){
		String s = "Cmax = " + this.cmax + "  <---  (";
		for(int i = 0; i<this.order.length; i++){
			s += this.order[i] + (i == this.order.length-1 ? "": " ");
		}
		s += ")";
		return s;
	}
	
	public void insererJob(int job, int pos){
		for(int k = this.order.length-1; k>pos; k--)
			this.order[k] = this.order[k-1];
		this.order[pos] = job;
	}
	
	public void retirerJob(int pos){
		for(int k = pos; k<this.order.length-1; k++)
			this.order[k] = this.order[k+1];
		this.order[this.order.length-1] = -1;
	}
	
	public boolean contains(int job){
		for(int i = 0; i<this.order.length; i++)
			if(this.order[i]==job)
				return true;
		return false;
	}
	
	// echange les jobs aux positions pos1 et pos2
	public void swap(int pos1, int pos2){
		int job = this.order[pos1];
		this.order[pos1] = this.order[pos2];
		this.order[pos2] = job;
	}
	
	// cycle de permutation des jobs : job1 en pos2, job2 en pos3 et job3 en pos1
	public void change(int pos1, int pos2, int pos3){
		int job2 = this.order[pos2];
		
		this.order[pos2] = this.order[pos1];
		this.order[pos1] = this.order[pos3];
		this.order[pos3] = job2;
	}
	
	// decalage d'une chaine de jobs : jobEnd en posBegin, jobBegin en posBegin+1, ... jobEnd-1 en posEnd
	public void shift(int posBegin, int posEnd){
		int job = this.order[posEnd];
		for(int i = posEnd; i>posBegin; i--)
			this.order[i] = this.order[i-1];
		this.order[posBegin] = job;
	}
}
