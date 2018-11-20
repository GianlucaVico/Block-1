import java.util.LinkedList;

public class Node {
	private int id;
	private int color;
	private LinkedList<Node> adj;
	
	//constructor
	public Node(int id) {
		this.id = id;
		this.adj = new LinkedList<Node>();
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
	
	//add a child to this Node
	public void addChild(Node child) {
		if(!adj.contains(child)) {
			adj.add(child);
			child.addChild(this);
		}
	}

}