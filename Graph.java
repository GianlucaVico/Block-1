import java.util.LinkedList;

public class Graph {
	int minDegree;
	int maxDegree;
	
	int edges;
	int verts;
	
	boolean complete = false;
	boolean nullGraph = false;
	
	LinkedList<Graph> subgraph;	//if need
	
	LinkedList<Node> minDegreeNodes; //if need
	LinkedList<Node> maxDegreeNodes; //if need
	
	public Graph(ColEdge[] edges, int numberOfEdges, int verts) {
		this.edges = numberOfEdges;
		this.verts = verts;
		setNullGraph();
		setComplete();
		
		if(complete) {
			minDegree = verts - 1;
			maxDegree = verts - 1;
		}else if(nullGraph) {
			minDegree = 0;
			maxDegree = 0;
		}else{
			minDegreeNodes = new LinkedList<Node>();
			maxDegreeNodes = new LinkedList<Node>();
			
			maxDegree = 0;
			
			Node[] tmp = {null, null};
			for(int i = 0; i < edges.length; i++){
				tmp[0] = Node.makeNode(edges[i].u);
				tmp[1] = Node.makeNode(edges[i].v);
				tmp[0].link(tmp[1]);
				
				for(int j = 0; j < 2; j++){
					if(tmp[j].getDegree() > maxDegree){
						maxDegree = tmp[j].getDegree();
						maxDegreeNodes.clear();
					}
					if(tmp[j].getDegree() == maxDegree)
						maxDegreeNodes.add(tmp[j]);
				}
			}
		}
	}
	
	private void setNullGraph(){
		nullGraph = (edges == 0);
	}
	
	private void setComplete(){
		complete = (edges == verts*(verts -1)/2);
	}
	
	private void findBounds(){	//update bound values (find bounds algorithm)
		if(!(complete || nullGraph)) {
		
		}
	}
	
	private boolean reduce(int degree) {//node with degree = degree will be eliminated
		return false;
	}
	
	//gives a name and order the nodes
	private void label(Node first, int start){	//if need
	
	}
	
	private int solve() {	//<- algorithm goes here
		return 0;	
	}
	
	public int[] getBounds() {
		return new int[]{minDegree + 1, maxDegree + 1};
	}
	
	public int getChromaticNumber() {
		return solve();
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public boolean isNullGraph() {
		return nullGraph;
	}
}