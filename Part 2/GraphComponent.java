import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import java.util.LinkedList;
import javax.swing.JFrame;

public class GraphComponent extends JComponent{
    public static float strokeSize = .1F;
    
    class Click implements MouseListener {  
        //check if can change selection -> auto selection for some game mode
        public void mouseClicked(MouseEvent arg0) {   
            if(mode.autoSelection()) {                
                selectNext();
            }else {
                Point click = arg0.getPoint();
                float squareSize = Node.size * Node.size;
                Node tmp = null;
                for(Node n: g.getNodes()) { //check every node -> nodes without color have priority
                    if(click.distanceSq(n.getPoint()) <= squareSize){
                        if(tmp == null || (tmp.getColor() != -1 && n.getColor() == -1)) {
                            tmp = n;
                        }
                    }
                }            
                selected = tmp; 
            }
            update();
            op.update();
        }        
        public void mousePressed(MouseEvent arg0) {
        }        
        public void mouseReleased(MouseEvent arg0) {
        }
        public void mouseEntered(MouseEvent arg0) {
        }
        public void mouseExited(MouseEvent arg0) {
        }
    }
    
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
        this.addMouseListener(new Click());
    }
    
    public Node getSelectedNode() {
        return selected;
    }
    
    public void select(Node n) {
        selected = n;
        //TODO update operation info
        update();
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
        op.updateSolvers();
        update();
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
        update();
    }
    
    public OperationComponent getOperationComponent() {
        return this.op;
    }
    
    public void paintComponent(Graphics gr) {
        Graphics2D g2 = (Graphics2D)gr;
        if(!mode.gameEnded()){
            //draw edges and nodes        
            for(Node n: g.getNodes()){
                g2.setStroke(new BasicStroke(strokeSize));
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
                n.draw(g2);
            }                     
            //draw selection
            if(selected != null) {
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(Node.size / 2));
                Point2D.Double p = selected.getPoint();            
                g2.draw(new Ellipse2D.Double(p.getX() - Node.size/4, p.getY() - Node.size/4, Node.size/2, Node.size/2));           
            }
        }else {
        //TODO write somenthing
        }
    }
    
    //when a color change 
    public void update() {
        this.repaint();
    }
    
    public void setDrawSize(double height, double width, double border) {
        Node.setSize(height, width);                 
    }  
    
    public void selectNext() {
        if(g.getSize() != 0) {
            if(selected == null) {
                selected = g.getNode(0);
            }else if(selected.getId() != g.getSize() - 1) {
                if(selected.getColor() != -1)
                    selected = g.getNode(selected.getId() + 1);
            }else {
                selected = null;                
            }
        }
    }        
    
    private void initSolvers() {
        solvers = new Solver[5];
        solvers[0] = new LowerBound(g);                                                 //lower
        solvers[1] = new UpperBound((Graph)g.clone());                                  //upper
        solvers[2] = new ChromaticNumber((Graph)g.clone(), solvers[0], solvers[1]);	//exact
        solvers[3] = new BestNode(g);                                                    //best node
        solvers[4] = new ColorHints(this);						//best color for this node
    }
        
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400,500);
	GraphComponent g = new GraphComponent();
        g.setDrawSize(500, 400, 15);
        frame.add(g);     
        frame.setTitle("Chromatic Number Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
