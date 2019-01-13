/**Created by Louis Gauthy */
import java.util.LinkedList;

/**
 * Find the chromatic number of a graph 
 */
public class ChromaticNumber implements Solver {
    private Graph g;
    private int solution;
    private Solver lower;
    private Solver upper;

    /**
     * Make a new solver for this graph, using a LowerBound and a UpperBound objects to solve this problem faster
     * @param g Graph to use
     * @param lower LowerBound solver
     * @param upper UpperBound solver
     */
    public ChromaticNumber(Graph g, Solver lower, Solver upper) {
        this.g = g;
        this.lower = lower;
        this.upper = upper;
        solution = -1;
    }

    /**
     * @return the Graph used
     */
    public Graph getGraph() {
        return g;
    }

    //return the solution
    /**
     * @return the chromatic number of the graph
     */
    public int solve() {
        if(solution == -1){         //fist time that this method is called
            if(g.isTrivial()){      //check if it trivial
                solution = g.trivialSolution();
            }else if(lower.solve() == upper.solve()) {  //check if bound algorithm agree
                solution = upper.solve();
            }else{
                solution = -1;
                int tmp;
                for(int i = 0; i < g.countSubgraphs(); i++){
                    tmp = getBound(g.getSubgraph(i));                    
                    if(tmp > solution) {
                        solution = tmp;                        
                    }
                    
                }
                
                if(solution > upper.solve()){
                    solution = upper.solve();
                }
                if(solution < lower.solve()) {
                    solution = lower.solve();
                }                
            }
        }
        if(solution == 0) 
            solution = 1;
        return solution;
    }

    /**
     * @param sub subgraph
     * @return chromatic number of the subgraph
     */
    private int getBound(Graph sub) {
        int bound = -1;
        if(sub.isTrivial()){
            bound = sub.trivialSolution();
        }else {
            LinkedList<Node> done = new LinkedList<Node>(); 
            LinkedList<Node> children = new LinkedList<Node>();
            LinkedList<Node> childrenDone = new LinkedList<Node>();
            int chromNode = -1, chromSubgraph = -1;             
            Node start;

            for(int i = 0; i < sub.getInitialSize(); i++) {                   
                //count = 0;
                start = sub.getNode(i);
                if(start != null) {
                    children.clear();
                    childrenDone.clear();

                    if(!done.contains(start)) { 
                        done.add(start);

                        //color matrix - initialized to false -> change all conditions next
                        boolean[][] color = new boolean[g.getInitialSize()][g.getInitialSize()];
                        int startDegree = start.getDegree();
                        //while(childrenDone.size() <= start.getDegree()){
                        while(childrenDone.size() <= startDegree){
                            if (children.isEmpty()) {
                                children = (LinkedList<Node>)start.getChildren().clone();     
                                for(Node n: children) {
                                    color[n.getId()][start.getId()] = true;
                                }
                            }else {
                                int colorStart;
                                int colorOther;
                                while(!children.isEmpty()) {
                                    start = children.remove();
                                    childrenDone.add(start);
                                    colorStart = 0;
                                    colorOther = 0;

                                    while(color[start.getId()][colorStart]){    //get color of start
                                        colorStart++;
                                    }

                                    for(int j = 0; j < start.getDegree(); j++) {    //get color of child, change it if invalid
                                        while(color[start.getChild(j).getId()][colorOther]){    //get color of start
                                            colorOther++;
                                        }
                                        if(colorOther == colorStart) {
                                            color[start.getChild(j).getId()][colorOther] = true;
                                        }
                                        if(!children.contains(start.getChild(j)) && !childrenDone.contains(start.getChild(j))){
                                            children.add(start.getChild(j));
                                        }
                                    }
                                }
                            }
                        } 
                        //get color from this starting node

                        for(int j = 0; j < color.length; j++){
                            chromNode = 1;
                            for(int k = 0; k < color[j].length; k++){
                                if(color[j][k])
                                    chromNode++;                             
                            }
                            if(chromNode > bound)
                                bound = chromNode;
                        }
                        /*if(chromSubgraph == -1)
                            chromSubgraph = bound;
                        else if(chromSubgraph > bound)
                            chromSubgraph = bound;
                        }*/
                        if(chromNode == -1)
                            chromNode = bound;
                        else if(chromNode > bound)
                            chromNode = bound;
                        //}
                    }
                }
            }
            //bound = chromSubgraph;
            bound = chromNode;
        }
        return bound + 1;
    }
}