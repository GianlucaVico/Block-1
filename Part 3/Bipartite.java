import java.util.LinkedList;
/**
 * This algorithm check if the graph is a bipartite graph.
 */
public class Bipartite implements Solver{
    private int solution;
    private Graph g;
    
    /**
     * @return the graph used
     */
    public Graph getGraph() {return g;}
    
    /**
     * @param g Grapg to use
     */
    public Bipartite(Graph g) {
        this.g = g;
        solution = -1;
    }
    
    /**
     * Check if the graph is a bipartite graph.
     * Computation are done only when this method is called for the first time.
     * @return 1 if it is bipartite, 0 if not
     */
    public int solve() {
        if(solution == -1) {
            if(g.isTrivial()){
                if(g.trivialSolution() == 2)
                    solution = 1;
                else
                    solution = 0;
            }else{
                resetColors(-1);    
                int i = 0;
                boolean bipartite = true;
                while(bipartite && i < g.getSize()){
                    if(g.getNode(i).getColor() == -1) {
                        bipartite = _solve(i);
                    }
                    i++;
                }                
                resetColors(-1);
                if(bipartite)
                    solution = 1;
                else
                    solution = 0;
            }
        }
        return solution;
    }
    
    private void resetColors(int value) {
        for(Node n: g.getNodes()) {
            n.setColor(value);
        }
    }
    
    private boolean _solve(int start) {
        LinkedList<Node> queue = new LinkedList<Node>();
        g.getNode(start).setColor(0);
        queue.add(g.getNode(start));
        
        while(!queue.isEmpty()){
            Node n = queue.poll();
            for(Node child: n.getChildren()) {
                if(child.getColor() == -1) {
                    child.setColor(1 - n.getColor());
                    queue.add(child);
                }else if(child.getColor() == n.getColor()) {
                    return false;
                }
            }
        }
        return true;
    }
}
