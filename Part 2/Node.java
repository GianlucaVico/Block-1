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
        //g2.setColor(op.getColor(color));
        g2.draw(new Ellipse2D.Double(x * Node.width - size/ 2, y * Node.height - size / 2, size, size));
        g2.drawString(Integer.toString(id), (float)(x * Node.width - size / 2), (float)(y * Node.height - size));
    }
    
    public Point2D.Double getPoint() {
        return new Point2D.Double(x * width,y * height);
    }    
}