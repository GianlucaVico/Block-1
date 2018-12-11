/** Solver that return the id of the easiest node to solve */
public class BestNode implements Solver {
    private Graph g;
    
    /**     
     * @param g Graph object to solve. BestNode doesn't change it
     */
    public BestNode(Graph g) {
            this.g = g;
    }
    
    /** Solve the graph
     * @return the id of a node or -1 if all nodes have a color
     */
    public int solve() {
        int solution = -1;
        Node tmp = null;	//current best next node to solve
        int count = 0;		//number of children of the current node not done
        int tmpCount = Integer.MAX_VALUE;	//number of children of tmp not done
        for(Node n: g.getNodes()) {
            if(n.getColor() == -1){
                count = 0;
                for(Node c: n.getChildren()) {
                    if(c.getColor() == -1) {
                        count++;
                    }			
                }
                if(count < tmpCount){	//at least 1 children node not done and a better node than tmp
                    tmpCount = count;
                    tmp = n; 
                }
            }
        }
        if(tmp != null)
            solution = tmp.getId();
        return solution;
    }
    
    /** @return the Graph used by this object*/
    public Graph getGraph() {
            return g;
    }
}