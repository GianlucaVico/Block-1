import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import java.util.LinkedList;
import javax.swing.JFrame;

public class GraphComponent extends JComponent{
    private GameMode mode;  //to check if can do a move
    private Node selected;
    private Graph g;
    private Solver[] solvers;    
    private OperationComponent op;
    
    public static final int LOWER_BOUND = 0;
    public static final int UPPER_BOUND = 1; 
    public static final int EXACT = 2;
    public static final int BEST_NODE = 3; 
    public static final int BEST_COLOR = 4;
    //test
    public GraphComponent() {
        g = new Graph(10, 10);
        selected = null;
        initSolvers();
    }
    
    public Node getSelectedNode() {
        return selected;
    }
    
    public void select(Node n) {
        selected = n;
        //TODO update operation info
    } 
    
    public Graph getGraph() {return g;}
    
    public LinkedList<Node> notColored() {
        LinkedList<Node> result = new LinkedList<Node>();
        for(Node n: g.getNodes()) {
            if(n.getColor() == -1) {
                result.add(n);
            }
        }
        return result;
    }
    
    public LinkedList<Node> errors() {
        LinkedList<Node> result = new LinkedList<Node>();
        for(Node n: g.getNodes()) {
            if(n.getColor() != -1 && !result.contains(n)) {
                for(Node c: n.getChildren()) {
                    if(c.getId() > n.getId() && c.getColor() == n.getColor())
                        result.add(c);
                }
            }
        }
        return result;
    }
    
    public void setGameMode(GameMode mode) {
        this.mode = mode;
    }
    
    public void changeGraph(Graph g) {
        this.g = g;
        initSolvers();
        this.selected = null;
    }
    
    public Solver[] getSolvers() {
        return solvers;
    }
    
    public int getSolution() {
        return solvers[GraphComponent.EXACT].solve();
    }
    
    public boolean chromaticNumberUsed() {
        return op.countColors() == getSolution();
    }
    
    public void setOperationComponent(OperationComponent op) {
        this.op = op;
        Node.setOperationComponent(op);
    }
    
    public void paintComponent(Graphics gr) {
        Graphics2D g2 = (Graphics2D)gr;
        //draw nodes
        for(Node n: g.getNodes()) {
            n.draw(g2);           
        }  
        
        //draw edges    -> repeat cycle to avoid conflicts on setting colors and strokes        
        g2.setStroke(new BasicStroke(5F));
        for(Node n: g.getNodes()){
            for(Node c: n.getChildren()) {
                if(c.getId() > n.getId()) { //avoid dupli
                    if(c.getColor() == n.getColor() && c.getColor() != -1) {
                        g2.setColor(Color.RED);
                    }else {
                        g2.setColor(Color.BLACK);
                    }
                    g2.draw(new Line2D.Double(c.getPoint(), n.getPoint()));
                }
            }
        }
        
    }
    
    //when a color change 
    public void update() {}
    
    public void setDrawSize(double height, double width) {
        Node.setSize(height, width);        
    }
    
    private void initSolvers() {
        solvers = new Solver[5];
        solvers[0] = new LowerBound(g);                                                 //lower
        solvers[1] = new UpperBound((Graph)g.clone());                                  //upper
        solvers[2] = new ChromaticNumber((Graph)g.clone(), solvers[0], solvers[1]);	//exact
        solvers[3] = new BestNode((Graph)g.clone());                                    //best node
        solvers[4] = new ColorHints(this);						//best color for this node
    }
        
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400,500);
	GraphComponent g = new GraphComponent();
        g.setDrawSize(500, 400);
        frame.add(g);     
        frame.setTitle("Chromatic Number Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
