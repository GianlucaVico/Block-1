import java.util.LinkedList;
import java.util.Iterator;

public class Node {
	int color;
	LinkedList<Node> nodes;
	int index;
	boolean test; //if need
	
	public static LinkedList<Node> allNodes = new LinkedList<Node>();
	
	public static Node exists(int index) {
		Node tmp = null;
		boolean found = false;
		Iterator<Node> i = allNodes.listIterator(0);
		while(i.hasNext() && !found) {
			tmp = i.next();
			if(tmp.index == index)
				found = true;
		}
		if(!found)
			tmp = null;
		return tmp;
	}
	
	public static Node makeNode(int index) {
		Node tmp = Node.exists(index);
		if(tmp == null){
			tmp = new Node();
			tmp.index = index;
			allNodes.add(tmp);
		}
		return tmp;
	}
	
	public static void clear() {
		allNodes.clear();
	}
	
	public Node() {
		color = 0;
		nodes = new LinkedList<Node>();
	}
	
	public void link(Node other){
		nodes.add(other);
		other.nodes.add(this);
	} 
	
	public void sort() {
		
	}
	
	
	public void remove() {
		for(int i = 0; i < nodes.size(); i++) {
			nodes.get(i).nodes.remove(this);
		}
		nodes.clear();
	}
	
	public int getDegree() {
		return nodes.size();
	}
}