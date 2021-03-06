import java.util.LinkedList;

public class Graph {
	boolean noClique;

	int minDegree;
	int maxDegree;
	int chromaticNumber;

	int edges;
	int verts;
	
	boolean complete = false;
	boolean nullGraph = false;
	boolean acyclic = false;
	
	LinkedList<Node> minDegreeNodes; //if need
	LinkedList<Node> maxDegreeNodes; //if need
	
	public Graph(ColEdge[] edges, int numberOfEdges, int verts, boolean noClique) {
		System.out.println("Verts: " + verts);
		System.out.println("Edges: " + numberOfEdges);
		Node.allNodes.clear();
		long startTime = System.nanoTime();
		this.noClique = noClique;
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

		System.out.println("Graph and bounds computed in " + (System.nanoTime() - startTime)/1000000 + " ms");
	}
	
	private void setNullGraph(){
		nullGraph = (edges == 0);
	}
	
	private void setComplete(){
		if(edges != 0)
			complete = (edges == verts*(verts -1)/2);
		else
			complete = false;
	}
	
	private LinkedList<Node> intersection(LinkedList<Node> l1, LinkedList<Node> l2){
		LinkedList<Node> inter = new LinkedList<Node>();
		for(int i = 0; i < l1.size(); i++){
			if(l2.indexOf(l1.get(i)) != -1){
				inter.add(l1.get(i));
			}
		}
		return inter;
	}
	private LinkedList<Node> union(LinkedList<Node> l1, Node n ){
		LinkedList<Node> un = new LinkedList<Node>();
		for(int i = 0; i < l1.size(); i++){
			un.add(l1.get(i));
		}
		un.add(n);
		return un;
	}
	private void bronKerbosch(LinkedList<Node> clique, LinkedList<Node> possible, LinkedList<Node> not, LinkedList<Integer> cliques){
		if(possible.size() == 0 && not.size() == 0) {
			cliques.add(clique.size());
		}else{
			LinkedList<Node> neighbours;
			
			for(int i = 0; i < possible.size(); i++){
				neighbours = new LinkedList<Node>();
				for(int j = 0; j < possible.get(i).getDegree(); j++){
					neighbours.add(possible.get(i).getChild(j));
				}
				bronKerbosch(union(clique, possible.get(i)), intersection(possible, neighbours), intersection(not, neighbours), cliques);
				not.add(possible.get(i));
				possible.remove(possible.get(i));
			}
		}
	}
	private void findLowerBound(){	//update bound values (find bounds algorithm)
		if(complete) {
			minDegree = verts;
		}else if(nullGraph){
			minDegree = 0;
		}else if(noClique){
				minDegree = Integer.MAX_VALUE;
				minDegreeNodes.clear();
				for(int i = 0; i < Node.allNodes.size(); i++){
					if(Node.allNodes.get(i).getDegree() < minDegree){
						minDegree = Node.allNodes.get(i).getDegree();
						minDegreeNodes.clear();
					}
					if(Node.allNodes.get(i).getDegree() == minDegree){
						minDegreeNodes.add(Node.allNodes.get(i));
					}
				}
				if(minDegree == Integer.MAX_VALUE){
					minDegree = 0;
				}
		}else{
			LinkedList<Node> clone = new LinkedList<Node>();
			for(int i = 0; i < Node.allNodes.size(); i++){
				clone.add(Node.allNodes.get(i));
			}
			LinkedList<Integer> cliques = new LinkedList<Integer>();
			bronKerbosch(new LinkedList<Node>(), clone, new LinkedList<Node>(), cliques);
			minDegree = 0;
			for(int i = 0; i < cliques.size(); i++){
				if(cliques.get(i) > minDegree)
					minDegree = cliques.get(i);
			}
			System.out.println("Max clique: " + minDegree);
		}
	}
	
	private void findUpperBound(){	//update bound values (find bounds algorithm)
		if(complete) {
			maxDegree = verts - 1;
			maxDegreeNodes = Node.allNodes;
		}else if(nullGraph){
			maxDegree = 0;
		}else {
			maxDegree = 0;
			maxDegreeNodes.clear();
			for(int i = 0; i < Node.allNodes.size(); i++){
				if(Node.allNodes.get(i).getDegree() > maxDegree){
					maxDegree = Node.allNodes.get(i).getDegree();
					maxDegreeNodes.clear();
				}
				if(Node.allNodes.get(i).getDegree() == maxDegree){
					maxDegreeNodes.add(Node.allNodes.get(i));
				}
			}
			if(!noClique)
				maxDegree = (int)Math.round(((double)(minDegree + maxDegree + 1))/2);
		}
	}

	private void reduce() {//node with degree = 1 will be eliminated
		System.out.println("Before reduction: verts " + verts + " null " + nullGraph + " complete " + complete + " acyclic " + acyclic);
		boolean reduced = true;
		Node tmp;
		while(!(complete || nullGraph) && reduced){
		//while(reduced){	
			reduced = false;
			for(int i = 0; i < Node.allNodes.size(); i++){
				tmp = Node.allNodes.get(i);
				if(tmp.getDegree() <= 1) {
					verts--;
					edges -= tmp.getDegree();
					Node.remove(tmp);
					reduced = true;
				}
			}
		}
		setComplete();
		if(!nullGraph && verts == 0)
			acyclic = true;
		setNullGraph();
		System.out.println("After reduction: verts " + verts + " null " + nullGraph + " complete " + complete + " acyclic " + acyclic);
	}	
	
	private int soleveGeneric(Node start) {
		int color = 0;
		
		start.color = color;
		boolean done = false;
		LinkedList<Node> notColor = Node.allNodes;
		LinkedList<Node> sameColor;
		//LinkedList<String> colors = new LinkedList<String>();	//debug
		
		while(!done){
			//System.out.println("Start: " + start);
			//next color
			start.test = true;
			sameColor = notColor;
			notColor = new LinkedList<Node>();

			//exclude children
			for(int i = 0; i < start.getDegree(); i++){
				if(!start.getChild(i).test){
					notColor.add(start.getChild(i));
					start.getChild(i).color = color + 1;
					sameColor.remove(start.getChild(i));
				}
				
			}	

			//resolve conflicts
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

			//check ending
			if(notColor.size() < 2){	
				done = true;
			}else{
				int i = 0;
				while(i < notColor.size() && notColor.get(i).test)
					i++;
				Node.allNodes.remove(start);	
				start = notColor.get(i);
				notColor.remove(start);
				//color++;
			}
			color++;
		}
		return color;
	}

	private int solve() {	//<- algorithm goes here
		int c = 0;
		//System.out.println("Solving");
		if(complete){
			c = verts;
		}else if (nullGraph){
			c = 1;
		}else{
			reduce();
			//findLowerBound();
			findUpperBound();
			if(acyclic){		
				System.out.println("Acyclic");	
				c = 2;
			}else if(complete){
				System.out.println("Complete");
				c = verts;
			}else if(nullGraph){
				System.out.println("nullGraph");
				c = 1;
			}else if((minDegree == maxDegree) && (minDegree == 2)){ //single cycle
				if(verts % 2 == 0){
					System.out.println("Even");
					c = 2;
				}else {
					System.out.println("Odd");
					c = 3;
				}
			}else{
				if(!noClique && maxDegree == minDegree)
					c = minDegree;
				else
					c = soleveGeneric(maxDegreeNodes.get(0));
			}
		}
		return c;
	}
	
	public int[] getBounds() {
		return new int[]{minDegree, maxDegree + 1};
	}
	
	public int getChromaticNumber() {
		int c = chromaticNumber;
		long startTime = System.nanoTime();
		if(c == 0)
			c = solve();
		System.out.println("Chromatic number computed in " + (System.nanoTime() - startTime)/1000000 + " ms");	
		return c;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public boolean isNullGraph() {
		return nullGraph;
	}
}