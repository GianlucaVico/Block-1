import java.util.LinkedList;

public class UpperBound implements Solver {
    private Graph g;
    private int solution;
    public UpperBound(Graph g) {
        this.g = g;
        solution = -1;
    }

    public Graph getGraph() {
        return g;
    }
    //return the solution
    public int solve() {
        if(solution == -1) {
            int tmp = -1; 
            for(int i = 0; i < g.countSubgraphs(); i++) {
                tmp = getBound(g.getSubgraph(i));
                if(tmp > solution)
                    solution = tmp;
            }			
        }
        return solution;
    }

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
        return bound + 1;
    }

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
