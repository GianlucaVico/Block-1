import java.util.LinkedList;

public class Graph {
	int minDegree;
	int maxDegree;
	int getChromaticNumber;

	int edges;
	int verts;
	
	boolean complete = false;
	boolean nullGraph = false;
	boolean acyclic = false;
	
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
		findLowerBound();
	}
	
	private void setNullGraph(){
		nullGraph = (edges == 0);
	}
	
	private void setComplete(){
		complete = (edges == verts*(verts -1)/2);
	}
	
	private void findLowerBound(){	//update bound values (find bounds algorithm)
		if(complete) {
			minDegree = verts - 1;
		}else if(nullGraph){
			minDegree = 0;
		}else {
			//
		}
	}
	
	private void findUpperBound(){	//update bound values (find bounds algorithm)
		if(complete) {
			maxDegree = verts - 1;
		}else if(nullGraph){
			maxDegree = 0;
		}else {
			//
		}
	}

	private boolean reduce(int degree) {//node with degree = degree will be eliminated
		boolean reduced = true;
		Node tmp;
		boolean tmpNullGraph = nullGraph;
		while(!complete || !nullGraph && reduced){
			reduced = false;
			if(degree == minDegree){
				for(int i = 0; i < minDegreeNodes.size(); i++) {
					tmp = minDegreeNodes.get(i);
					verts--;
					edges -= tmp.getDegree(); 
					Node.remove(tmp);	
				}
				reduced = true;
				minDegree = -1;
			}else if(degree == maxDegree){
				for(int i = 0; i < maxDegreeNodes.size(); i++) {
					tmp = maxDegreeNodes.get(i);
					verts--;
					edges -= tmp.getDegree();
					Node.remove(tmp);
				}
				reduced = true;
				maxDegree = -1;
			}else{
				for(int i = 0; i < Node.allNodes.size(); i++){
					tmp = Node.allNodes.get(i);
					if(tmp.getDegree() == degree) {
						verts--;
						edges -= tmp.getDegree();
						Node.remove(tmp);
						reduece = true;
					}
				}
			}
		}
		if(minDegree == -1)
			findLowerBound();
		if(maxDegree == -1)
			findUpperBound();
		setNullGraph();
		setComplete();
		if(nullGraph != tmpNullGraph)
			acyclic = true;
	}
	
	//gives a name and order the nodes
	private void label(Node first, int start){	//if need
	
	}
	
	private int solve() {	//<- algorithm goes here
		int c = 0;

		if(acyclic){
			//
		}else if(complete){
			//
		}else if(nullGraph){
			//
		}else if((minDegree == maxDegree) && (minDegree == 2)){ //single cycle
			if(verts % 2 == 0){
				//
			}else {
				//
			}
		}else{ //not acyclic, not a single cycle
			//
			LinkedList<Boolean>[] colors = new LinkedList<Boolean>[Node.allNodes.size()];
			int color = 0;
			LinkedList<Node> wainting = new LinkedList<Node>();
			
			for(int i = 0; i < Node.allNodes.size(); i++){
			  tmp = Node.allNodes.get(i);
			  
				if(i == 0) {
				colors[tmp.index].add(true);
				for(int j = 0; j < tmp.getDegree(); j++){
				  colors[tmp.getChild(j)].add(false);
				  colors[tmp.getChild(j)].add(true);
				}
				Node.allNodes.remove(tmp);
			  }
			}
			//
		}
		return c;
	}
	
	public int[] getBounds() {
		return new int[]{minDegree, maxDegree + 1};
	}
	
	public int getChromaticNumber() {
		int c = chromaticNumber;
		if(c == 0)
			c = solve();
		return c;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public boolean isNullGraph() {
		return nullGraph;
	}
}