import java.util.LinkedList;

public class Graph {
	int minDegree;
	int maxDegree;
	int chromaticNumber;

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

		}
		
	}
	
	private void findUpperBound(){	//update bound values (find bounds algorithm)
		if(complete) {
			maxDegree = verts - 1;
		}else if(nullGraph){
			maxDegree = 0;
		}else {
			
		}
	}

	private void reduce(int degree) {//node with degree = degree will be eliminated
		boolean reduced = true;
		Node tmp;
		boolean tmpNullGraph = nullGraph;
		//while(!complete || !nullGraph && reduced){
		while(reduced){	
			reduced = false;
			/*if(degree == minDegree){
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
			}else{*/
				for(int i = 0; i < Node.allNodes.size(); i++){
					tmp = Node.allNodes.get(i);
					if(tmp.getDegree() == degree) {
						verts--;
						edges -= tmp.getDegree();
						Node.remove(tmp);
						reduced = true;
					}
				}
			//}
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

	private int soleveGeneric(Node start, int color) {
		start.test = true;
		start.color = color;
		boolean done = false;
		LinkedList<Node> notColor = Node.allNodes;
		LinkedList<Node> sameColor;
		LinkedList<String> colors = new LinkedList<String>();	//debug
		
		while(!done){
			sameColor = notColor;
			notColor = new LinkedList<Node>();
			for(int i = 0; i < start.getDegree(); i++){
				if(!start.getChild(i).test){
					notColor.add(start.getChild(i));
					sameColor.remove(start.getChild(i));
					start.getChild(i).color = color + 1;
				}
			}	

			int maxConflict = 0, tmpConflict;
			Node conflict = null, tmp;
			do{
				maxConflict = 0;
				for(int i = 0; i < sameColor.size(); i++){
					tmp = sameColor.get(i);
					tmpConflict = tmp.conflicts();
					if(tmpConflict > maxConflict){
						maxConflict = tmpConflict;
						conflict = tmp;
					}
					if(tmpConflict == 0){
						tmp.test = true;
						tmp.color = color;
					}
				}
				if(maxConflict != 0){				
					conflict.color = color + 1;
					notColor.add(conflict);
					sameColor.remove(conflict);
				}
			}while(maxConflict != 0);

			if(notColor.size() <= 2){	//it works somethimes but idk why
				done = true;
				System.out.println("Stopping: " + notColor.size());
				if(notColor.size() == 2 && notColor.get(0).isNeighbour(notColor.get(1))){
					System.out.println("Neighbours " + notColor.get(0).index + " " + notColor.get(1).index + ": " + notColor.get(0).isNeighbour(notColor.get(1)));
					color += 2;
				}else{
					color++;
				}
			}else{
				int i = 0;
				while(i < notColor.size() && notColor.get(i).test)
					i++;
				start = notColor.get(i);
				color++;
			}
			//System.out.println(color);
			for(int i = 0; i < sameColor.size(); i++){
				colors.add(sameColor.get(i).index + " " + sameColor.get(i).color);
			}
		}
		System.out.println("Nodes checked:" + colors.size());
		/*for(int i = 0; i < colors.size(); i++){
			System.out.println(colors.get(i));
		}*/
		
		return color + 1;
	}

	private int solve() {	//<- algorithm goes here
		int c = 0;

		if(acyclic && !nullGraph){
            c = 2;
		}else if(complete){
            c = verts;
		}else if(nullGraph){
            c = 1;
		}else if((minDegree == maxDegree) && (minDegree == 2)){ //single cycle
			if(verts % 2 == 0){
                c = 2;
			}else {
                c = 3;
			}
		}else{
			reduce(1);
			c = soleveGeneric(maxDegreeNodes.get(0), 0);
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