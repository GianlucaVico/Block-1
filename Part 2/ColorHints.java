/** Created by Gianluca Vico */
/**
 * Suggest the best color for the selected node of a GraphComponent
 */
public class ColorHints implements Solver {
    private GraphComponent graph;
    /**
     * Make a ColorHints object for this GraphComponent
     * @param graphComponent the GraphComponent to use
     */
    public ColorHints(GraphComponent graphComponent) {
        graph = graphComponent;
    }
	
    /**
     * Find a color
     * @return the color number to use for the selected node
     */
    public int solve() {
        Node n = graph.getSelectedNode();
        int color = -1;
        if(n != null){        
            boolean ok;	//is this color as small as possible?
            int i = 0; 	//counter (count children of n)
            do{
                ok = true;
                color++;
                while(i < n.getDegree() && ok) {
                    if(n.getChild(i).getColor() == color) {
                        ok = false;
                    }
                    i++;
                }
            }while(!ok);
            if(!ok)
                color++;
        }else{
            color--;
        }
        return color + 1;
    }
	
    /**
     * @return the Graph used for this object
     */
    public Graph getGraph() {
        return graph.getGraph();
    }
}