import javax.swing.JComponent;
import java.util.LinkedList;

public class GraphComponent extends JComponent{
    private GameMode mode;  //to check if can do a move
    private Graph g;
    //test
    public GraphComponent() {
        g = new Graph(10, 10);
    }
    
    public Node getSelectedNode() {return g.getNode(0);}
    
    public Graph getGraph() {return g;}
    
    public LinkedList<Node> notColored() {return null;}
    
    public LinkedList<Node> errors() {return null;}
    
    public void setGameMode(GameMode mode) {
        this.mode = mode;
    }
}
