public class ChromaticNumber implements Solver {
	private Graph g;
	private int solution;
	
	public ChromaticNumber(Graph g) {
		this.g = g;
		solution = -1;
	}
	
	public Graph getGraph() {
		return g;
	}
	//return the solution
	public int solve() {
		if(solution == -1){
			if(g.isTrivial()){
				solution = g.trivialSolution();
			}else {
				//TODO
			}
		}
		return solution;
	}
}