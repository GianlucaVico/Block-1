import java.io.*;
import java.util.*;

public class Graph {
	private Node[] nodes;
	private int[][] rawData;
	private int edges;
	private int size;
	//private boolean hasTrivialSolution;
	//private int trivialSolution;
	//private int trivialUpperBound;
	//private int trivialLowerBound;
	private int minDegree;
	private int maxDegree;
	private boolean reduced;	//TODO useless
	private boolean complete;
	private boolean nullGraph;
	private boolean cyclic;
	private boolean toUpdate;	//TODO useless
	private boolean acyclic;
	
	private void init(int size) {	//TODO optimize initialization order
		this.size = size;
		toUpdate = true;	//TODO useless
		edges = rawData.length;
		
		nodes = new Node[size];
		for(int i = 0; i < size; i++) {
			nodes[i] = new Node(i);
		}
		
		for(int i = 0; i < edges; i++) {
			nodes[rawData[i][0]].addChild(nodes[rawData[i][1]]);
			nodes[rawData[i][1]].addChild(nodes[rawData[i][0]]);
		}
		nullGraph = edges == 0;
		
		reduce();
		updateDegrees();
		//minDegree = getMaxDegree();
		//maxDegree = getMinDegree()
		
		//reduced = false;	//TODO
		complete = (edges ==(size)*(size - 1) / 2);
		cyclic = ((minDegree == maxDegree) && (maxDegree == 2));
		acyclic = (nodes.length == 0); 
	
		//hasTrivialSolution = false;	//TODO
		//isTrivial()
		//trivialSolution = -1;	//TODO
		//trivialUpperBound = -1;	//TODO
		//trivialLowerBound = -1;	//TODO
	}
	
	private Graph(int size) {init(size);}
	
	//graph from file
	public Graph(String fileName) {
		ReadGraph rg = new ReadGraph();
		ColEdge[] ce = rg.getEdges(fileName);

		rawData = new int[ce.length][2];
		
		for(int i = 0; i < rawData.length; i++){
			rawData[i] = new int[]{ce[i].u, ce[i].v};
		}
		
		init(rg.verts); 
	}
	
	//random graph from size and edge number
	public Graph(int size, int edges) {
		rawData = new int[edges][2];
		for(int i = 0; i < edges; i++) {
			rawData[i] = new int[]{(int)(Math.random()*size + 1), (int)(Math.random()*size + 1)};
		}
		init(size);
	}
	
	//graph from edges
	public Graph(int[][] edges, int size) {
		rawData = edges;
		init(size);
	}
	
	//return a copy of this graph
	public Graph clone() {
		Graph newGraph = new Graph(this.size);
		//TODO
		return newGraph;
	}
	
	//is there a trivial solution?
	public boolean isTrivial() {
		boolean hasTrivialSolution = false;
		if(complete || nullGraph || cyclic || acyclic)	//complete - null - cyclic
			hasTrivialSolution = true;
		/*else
			hasTrivialSolution = false;*/
		return hasTrivialSolution;
	}
	
	
	public int trivialSolution() {
		int solution = 0;
		if(complete)
			solution = size;
		else if(nullGraph)
			solution = 1;
		else if(cyclic){
			if (size % 2 == 0)
				solution = 2;
			else
				solution = 3;
		}else if(acyclic)
			solution = 2;
		return solution;
	}
	
	public int trivialUpperBound() {	//DONE
		int bound = 0;
		if(isTrivial())
			bound = trivialSolution();
		else
			bound = getMaxDegree() + 1;
		return bound;
	}
	
	public int trivialLowerBound() {	//DONE
		int bound = 0;
		if(isTrivial())
			bound = trivialSolution();
		else
			bound = 3;
		return bound;
	}
	
	//number of nodes
	public int getSize() {
		return size;
	}
	
	//get a node
	public Node getNode(int num) {	//DONE
		Node node;
		if(num < 0 || num >= nodes.length)
			node = null;
		else
			node = nodes[num];	
		return node;
	}
	
	public int getEdges() {
		return edges;
	}
	
	private void updateDegrees() {
		if(nullGraph) {
			maxDegree = 0;
			minDegree = 0;
		}else{
			toUpdate = false;
			maxDegree = -1;
			minDegree = Integer.MAX_VALUE;
			for(Node i : nodes) {
				maxDegree = Math.max(maxDegree, i.getDegree());
				minDegree = Math.min(minDegree, i.getDegree());
			}
		}
	}
	
	public int getMaxDegree() {	
	/*	if(toUpdate)
			updateDegrees();*/
		return maxDegree;
	}
	
	public int getMinDegree() {	
	/*	if(toUpdate)
			updateDegrees();*/
		return maxDegree;
	}
	
	private void reduce() {	//TODO
		if(!reduced){}
	}

}

//internal stuffs
class ColEdge {
	int u;
	int v;
}

class ReadGraph
		{
		
		public final static boolean DEBUG = false;
		
		public final static String COMMENT = "//";
		
		public int edges = 0;
		public int verts = 0;
		//it doesn't return unconnected edges
		public ColEdge[] getEdges( String file )
			{
				
			String inputfile = file;
			
			boolean seen[] = null;
			
			//! n is the number of vertices in the graph
			int n = -1;
			
			//! m is the number of edges in the graph
			int m = -1;
			
			//! e will contain the edges of the graph
			ColEdge e[] = null;
			
			try 	{ 
			    	FileReader fr = new FileReader(inputfile);
			        BufferedReader br = new BufferedReader(fr);

			        String record = new String();
					
					//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
					//! These comments are only allowed at the top of the file.
					
					//! -----------------------------------------
			        while ((record = br.readLine()) != null)
						{
						if( record.startsWith("//") ) continue;
						break; // Saw a line that did not start with a comment -- time to start reading the data in!
						}
	
					if( record.startsWith("VERTICES = ") )
						{
						n = Integer.parseInt( record.substring(11) );		
						verts = n;
						if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
						}

					seen = new boolean[n+1];	
						
					record = br.readLine();
					
					if( record.startsWith("EDGES = ") )
						{
						m = Integer.parseInt( record.substring(8) );					
						edges = m;
						if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
						}

					e = new ColEdge[m];	
												
					for( int d=0; d<m; d++)
						{
						if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
						record = br.readLine();
						String data[] = record.split(" ");
						if( data.length != 2 )
								{
								System.out.println("Error! Malformed edge line: "+record);
								System.exit(0);
								}
						e[d] = new ColEdge();
						
						e[d].u = Integer.parseInt(data[0]);
						e[d].v = Integer.parseInt(data[1]);

						seen[ e[d].u ] = true;
						seen[ e[d].v ] = true;
						
						if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);
				
						}
									
					String surplus = br.readLine();
					if( surplus != null )
						{
						if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
						}
					
					}
			catch (IOException ex)
				{ 
		        // catch possible io errors from readLine()
			    System.out.println("Error! Problem reading file "+inputfile);
				System.exit(0);
				}

			for( int x=1; x<=n; x++ )
				{
				if( seen[x] == false )
					{
					if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
					}
				}		
			return e;
		}

}


