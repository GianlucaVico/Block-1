import javax.swing.JComponent;

public class ColorHints implements Solver {
    private GraphComponent graph;
	
    public ColorHints(GraphComponent graphComponent) {
        graph = graphComponent;
    }
	
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
	
    public Graph getGraph() {
        return graph.getGraph();
    }
}