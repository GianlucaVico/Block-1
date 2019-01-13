/**Created by Harry Forest, Gianluca Vico */
import java.util.LinkedList;
/**
 * Find the upper bound of the chromatic number of a graph
 */
public class UpperBound implements Solver {
    private Graph g;
    private int solution;
    
    /**
     * Make a new solver for this graph
     * @param g Graph to use
     */
    public UpperBound(Graph g) {
        this.g = g;
        solution = -1;
    }

    /**
     * @return Graph used
     */
    public Graph getGraph() {
        return g;
    }
    //return the solution
    /**
     * This method changes the graph
     * @return upper bound of the chromatic number
     */
    public int solve() {        
        if(solution == -1) {
            int tmp = -1; 
            for(int i = 0; i < g.countSubgraphs(); i++) {
                tmp = getBound(g.getSubgraph(i));
                if(tmp > solution)
                    solution = tmp;
                //System.out.println(solution);
            }
        }
        if(solution < 3 && !g.isTrivial())
            solution = 3;
        return solution;
    }

    /**
     * Get the bound of a subgraph
     * @param sub subgraph
     * @return boujnd of the chromatic number of the subgraph
     */
    private int getBound(Graph sub) {
        int bound = -1;
        if(sub.isTrivial()) {
            bound = sub.trivialUpperBound();
        }else{
            int color = 0;			//current color and color counter
            boolean done = false;	//it is done when every node has a color
            LinkedList<Node> differentColor = new  LinkedList<Node>();	//nodes with a with a different color from the current node
            for(Node n: sub.getNodes()) {
                differentColor.add(n);
            }
            LinkedList<Node> sameColor = new LinkedList<Node>();	//nodes with the same color of the current one
            LinkedList<Node> checked = new LinkedList<Node>();		//node already checked (avoid infinite loops)
            Node start = differentColor.removeFirst();			
            start.setColor(color);						
            sameColor.add(start);

            while(!done) {
                checked.add(start);
                sameColor = differentColor;
                differentColor = new LinkedList<Node>();

                //exclude children of start
                for(int i = 0; i < start.getDegree(); i++) {
                    if(!checked.contains(start.getChild(i))) {
                        differentColor.add(start.getChild(i));
                        start.getChild(i).setColor(color+1);
                        sameColor.remove(start.getChild(i));
                    }
                }

                //resolve conflicts
                int maxConflict = 0, tmpConflict;
                Node conflict = null, tmp;
                do{
                    maxConflict = 0;
                    for(int i = 0; i < sameColor.size(); i++) {
                        tmp = sameColor.get(i);
                        tmpConflict = countConflicts(tmp);
                        if(tmpConflict > maxConflict) {
                            maxConflict = tmpConflict;
                            conflict = tmp;
                        } 
                        if(tmpConflict == 0) {
                            tmp.setColor(color);
                            checked.add(tmp);	//TODO verify
                        }
                    }
                    if(maxConflict != 0) {
                        conflict.setColor(color + 1);
                        differentColor.add(conflict);
                        sameColor.remove(conflict);
                    }
                }while(maxConflict != 0);

                //check end condition
                if(differentColor.size() < 2) {
                    done = true;
                }else {
                    int i = 0;
                    while(i < differentColor.size() && checked.contains(differentColor.get(i)))
                        i++;

                    start = differentColor.get(i);
                    differentColor.remove(start);
                    color++;
                }
            }
            bound = color + 1;
        }
        //return bound + 1;
        return bound;
    }
    
    /**
     * @param n Node to check
     * @return number of linked noes with the same color
     */
    private int countConflicts(Node n) {
        int color = n.getColor();
        int c = 0;
        for(int i = 0; i < n.getDegree(); i++) {
            if(n.getChild(i).getColor() == color)
                c++;
        }
        return c;
    }
} 
