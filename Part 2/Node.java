import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.LinkedList; 
import javax.swing.JComboBox;

public class Node {
    public static float size = 10F;
    public static double height = 0;
    public static double width = 0;
    public static double border = 10;
    private static OperationComponent op;
    
    private int id;
    private int color;
    private LinkedList<Node> adj;    
    
    private double x,y; //to draw - use x y instead of point -> scale independent
    
    //constructor
    /**GRAPH methods **/
    public Node(int id) {
        this.color = -1;
        this.id = id;
        this.adj = new LinkedList<Node>();
        this.x = Math.random();
        this.y = Math.random();        
    }

    public int getId(){
        return id;
    }

    //return number of links on this node
    public int getDegree() {
        return adj.size();
    }

    //return the color of this node
    public int getColor() { 
        return this.color;
    }

    //change the color of this node
    public void setColor(int color) {
        this.color = color;
    }

    //return a child (a Node object) of this node
    public Node getChild(int number) {
        return adj.get(number);
    }

    public LinkedList<Node> getChildren() {
        return adj;
    }

    //add a child to this Node
    public void addChild(Node child) {
        if(!adj.contains(child)) {
            adj.add(child);
            child.addChild(this);
        }
    }
    /**      **/
    
    /**DRAW methods **/
    public static void setOperationComponent(OperationComponent op) {
        Node.op = op;
    }
    public static void setSize(double h, double w) {
        Node.height = h;
        Node.width = w;        
    }
    
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(size));        
        g2.setColor(op.getColor(color));      
        double xDraw = getX() - size/ 2;
        double yDraw = getY() - size / 2;
        g2.draw(new Ellipse2D.Double(xDraw, yDraw, size, size));
        g2.drawString(Integer.toString(id), (float)(xDraw), (float)(yDraw - size));
    }
    
    private double getX() {
        return Math.min(Math.max(x * Node.width, border), width - border);
    }
    
    private double getY() {
        return Math.min(Math.max(y * Node.height, border), height - border);
    }
    
    public Point2D.Double getPoint() {
        return new Point2D.Double(getX(), getY());
    }    
}