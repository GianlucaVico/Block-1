import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.LinkedList; 

/**
 * Represent a node of a graph
 */
public class Node {
    private static float size = 10F;
    private static double height = 0;
    private static double width = 0;
    private static double border = 10;
    private static OperationComponent op;
    
    private int id;
    private int color;
    private LinkedList<Node> adj;    
    
    private double x,y; //to draw - use x y instead of point -> scale independent
    
    //constructor
    /*GRAPH methods */
    /**
     * Initialize a node
     * @param id identifier, unique in a graph
     */
    public Node(int id) {
        this.color = -1;
        this.id = id;
        this.adj = new LinkedList<Node>();
        this.x = Math.random();
        this.y = Math.random();        
    }

    /**
     * @return the identifier of this node
     */
    public int getId(){
        return id;
    }

    //return number of links on this node
    /**
     * @return the degree of this node
     */
    public int getDegree() {
        return adj.size();
    }

    //return the color of this node
    /**
     * @return the color of this node, -1 if not set
     */
    public int getColor() { 
        return this.color;
    }

    //change the color of this node
    /**
     * @param color set this color
     */
    public void setColor(int color) {
        this.color = color;
    }

    //return a child (a Node object) of this node
    /**
     * @param number number of the child, between 0 (included) and getDegree() (excluded)
     * @return the child node
     */
    public Node getChild(int number) {
        return adj.get(number);
    }

    /**
     * @return the list of nodes linked to this
     */
    public LinkedList<Node> getChildren() {
        return adj;
    }

    //add a child to this Node
    /**
     * @param child node to link to this one
     */
    public void addChild(Node child) {
        if(!adj.contains(child)) {
            adj.add(child);
            child.addChild(this);
        }
    }
    
    /*DRAW methods */
    public static void setOperationComponent(OperationComponent op) {
        Node.op = op;
    }
    
    /**
     * Set bounds to draw this node
     * @param h max height 
     * @param w max width
     * @param b borders
     * @param size radius
     */
    public static void setSize(double h, double w, double b, float size) {
        Node.height = h;
        Node.width = w;        
        Node.border = b;
        Node.size = size;
    }
    
    /**
     * Draw this node
     * @param g2 
     */
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(size));        
        g2.setColor(op.getColor(color));      
        double xDraw = getX() - size/ 2;
        double yDraw = getY() - size / 2;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2.draw(new Ellipse2D.Double(xDraw, yDraw, size, size));
        g2.drawString(Integer.toString(id), (float)(xDraw), (float)(yDraw - size));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
    }
    
    /**
     * @return x position
     */
    private double getX() {
        return Math.min(Math.max(x * Node.width, border), width - border);
    }
    
    /**
     * @return y position
     */
    private double getY() {
        return Math.min(Math.max(y * Node.height, border), height - border);
    }
    
    /**
     * @return positon
     */
    public Point2D.Double getPoint() {
        return new Point2D.Double(getX(), getY());
    }    
    
    public static double getBorder() {
        return border;
    }    
    public static double getHeight() {
        return height;
    }
    public static double getWidth() {
        return width;
    }
    /**
     * @return radius of a node
     */
    public static float getSize() {
        return size;
    }
}