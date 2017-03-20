package definition;

public class Solution implements Comparable<Solution>{
	private int[] order;
	private int cmax;
	private Instance instance;

	public Solution(Instance i){
		this.instance = i;
		this.order = new int[i.getNbJobs()];
		this.cmax = Integer.MAX_VALUE;
	}

	public int[] getOrder() {
		return order;
	}
	
	public void setOrder(int[] o){
		this.order = o;
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
		for(int i = 0; i<this.instance.getNbJobs(); i++){
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
}
