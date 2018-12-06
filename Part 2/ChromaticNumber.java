import java.util.LinkedList;

public class ChromaticNumber implements Solver {
    private Graph g;
    private int solution;
    private Solver lower;
    private Solver upper;

    public ChromaticNumber(Graph g, Solver lower, Solver upper) {
        this.g = g;
        this.lower = lower;
        this.upper = upper;
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
            }else if(lower.solve() == upper.solve()) {
                solution = upper.solve();
            }else{
                solution = Integer.MAX_VALUE;
                int tmp;
                for(int i = 0; i < g.countSubgraphs(); i++){
                    tmp = getBound(g.getSubgraph(i));
                    if(tmp < solution)
                        solution = tmp;
                }
                if(solution < lower.solve()) {
                    solution = lower.solve();
                }
                if(solution > upper.solve()){
                    solution = upper.solve();
                }
            }
        }
        return solution;
    }

    private int getBound(Graph sub) {
        int bound = -1;
        if(sub.isTrivial()){
            bound = sub.trivialSolution();
        }else {
            LinkedList<Node> done = new LinkedList<Node>(); //USELESS
            LinkedList<Node> children = new LinkedList<Node>();
            LinkedList<Node> childrenDone = new LinkedList<Node>();
            int chromNode, chromSubgraph = -1;             
            Node start;

            for(int i = 0; i < sub.getSize(); i++) {                   
                //count = 0;
                start = sub.getNode(i);

                children.clear();
                childrenDone.clear();

                if(!done.contains(start)) { //USELESS don't check done
                    done.add(start);

                    //color matrix - initialized to false -> change all conditions next
                    boolean[][] color = new boolean[g.getSize()][g.getSize()];

                    while(childrenDone.size() <= start.getDegree()){
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
                    if(chromSubgraph == -1)
                            chromSubgraph = bound;
                    else if(chromSubgraph > bound)
                            chromSubgraph = bound;
                }
            }
            bound = chromSubgraph;
        }

        return bound + 1;
    }
}