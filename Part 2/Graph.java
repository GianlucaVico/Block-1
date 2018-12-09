import java.io.*;
//import java.util.*;
import java.util.LinkedList;

public class Graph {
    private Node[] nodes;
    private int[][] rawData;
    private int edges;
    private int size;
    private int minDegree;
    private int maxDegree;
    private boolean complete;
    private boolean nullGraph;
    private boolean cyclic;
    private boolean acyclic;
    private LinkedList<Graph> subgraphs;

    //by reduction effects
    private LinkedList<Node> removed;	//1 reduction: -1 edge, -1 nodes 

    private void init(int size) {	//TODO optimize initialization order
        this.size = size;
        edges = rawData.length;
        removed = new LinkedList<Node>();
        subgraphs = new LinkedList<Graph>();

        //make nodes and their conncetions
        nodes = new Node[size];
        for(int i = 0; i < size; i++) {
            nodes[i] = new Node(i);
        }

        for(int i = 0; i < edges; i++) {
            nodes[rawData[i][0] - 1].addChild(nodes[rawData[i][1] - 1]);
            nodes[rawData[i][1] - 1].addChild(nodes[rawData[i][0] - 1]);
        }

        setProperties();
        findSubgraphs();        
    }

    private void setProperties() {
        nullGraph = edges == 0;

        reduce();
        updateDegrees();

        //TODO recount edges and size after reductions
        complete = (edges ==(size)*(size - 1) / 2);	
        cyclic = ((minDegree == maxDegree) && (maxDegree == 2));
        acyclic = (removed.size() == nodes.length);
    }

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
    //TODO count edges correctly
    public Graph(int size, int edges) {
        edges = Math.min(edges, size*(size - 1)/2);
        rawData = new int[edges][2];
        int u,v,c;  //node, node, counter
        boolean d;
        for(int i = 0; i < edges; i++) {
            do{
                d = false;
                u = (int)(Math.random()*size + 1);
                do {
                    v = (int)(Math.random()*size + 1);
                }while(v == u);
                c = 0;
                while(c < i && !d){
                    if((rawData[c][0] == u && rawData[c][1] == v) || (rawData[c][0] == v && rawData[c][1] == u))
                        d = true;
                    c++;
                }
            }while(d);

            rawData[i] = new int[]{u, v};
        }
        System.out.println(this);
        for(int[] e: rawData) {
            System.out.println(e[0] + " " + e[1]);
        }
        init(size);
    }

    //graph from edges
    public Graph(int[][] edges, int size) {
        rawData = edges.clone();
        init(size);
    }

    //graph from nose list
    public Graph(Node[] n) {
        size = n.length;
        LinkedList<int[]> tmpRawData = new LinkedList<int[]>();
        for(int i = 0; i < n.length; i++) {
            for(Node j: n[i].getChildren()){
                if (n[i].getId() < j.getId()) {
                    tmpRawData.add(new int[]{n[i].getId(), j.getId()});
                }
            }
        }
        rawData = new int[tmpRawData.size()][2];
        for(int i = 0; i < tmpRawData.size(); i++) {
            rawData[i] = tmpRawData.get(i);
        }
        nodes = n;
        edges = rawData.length;
        removed = new LinkedList<Node>();
        subgraphs = new LinkedList<Graph>();
        setProperties();
    }

    //return a copy of this graph
    public Graph clone() {
        Graph newGraph = new Graph(this.rawData, this.size);
        return newGraph;
    }

    //is there a trivial solution?
    public boolean isTrivial() {	//TODO check subgraphs
        boolean hasTrivialSolution = false;
        if(complete || nullGraph || cyclic || acyclic)	//complete - null - cyclic
            hasTrivialSolution = true;
        return hasTrivialSolution;
    }

    public int trivialSolution() {	//TODO check subgraphs
        int solution = 0;
        if(nullGraph)
            solution = 1;
        else if(complete)
            solution = size - removed.size();
        else if(cyclic){
            if (size - removed.size() % 2 == 0)
                solution = 2;
            else
                solution = 3;
        }else if(acyclic)
            solution = 2;
        else
            solution = trivialUpperBound();
        return solution;
    }

    public int trivialUpperBound() {	//TODO check subgraphs
        int bound = 0;
        if(isTrivial())
            bound = trivialSolution();
        else
            bound = getMaxDegree() + 1;
        if(bound <= 0){
            if(edges != 0)
                bound = 2;
            else
                bound = 1;
        }
        return bound;
    }

    public int trivialLowerBound() {	//TODO check subgraphs
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
    public Node getNode(int num) {	
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
            maxDegree = -1;
            minDegree = Integer.MAX_VALUE;
            for(Node i : nodes) {
                if(!removed.contains(i)) {
                    maxDegree = Math.max(maxDegree, i.getDegree() - countRemovedChildren(i));
                    minDegree = Math.min(minDegree, i.getDegree() - countRemovedChildren(i));
                }
            }
            if(maxDegree == -1)
                maxDegree = 0;
            if(minDegree == Integer.MAX_VALUE)
                minDegree = 0;
        }
    }

    public int getMaxDegree() {	
        return maxDegree;
    }

    public int getMinDegree() {	
        return maxDegree;
    }

    private void reduce() {	
        boolean changes = true;		//stop when cannot remove any node

        LinkedList<Node> l = new LinkedList<Node>();	//list of node in the graph
        for(int i = 0; i < nodes.length; i++) {
            l.add(nodes[i]);
        }

        while(changes) {	
            changes = false;
            for(int i = 0; i < l.size(); i++) {
                if(l.get(i).getDegree() - countRemovedChildren(l.get(i)) <= 1){	//exclude removed children node
                    removed.add(l.get(i));
                    l.remove(i);
                    changes = true;
                }
            }
        }
    }

    private int countRemovedChildren(Node n) {
        int r = 0;
        for(int i = 0; i < n.getDegree(); i++) {
            if(removed.contains(n.getChild(i)))
                r++;
        }
        return r;
    }

    private void findSubgraphs() {
        if(size != 0) {
            boolean[] mask = new boolean[size];	//false: not done, true: done
            int done = 0;	
            LinkedList<Node[]> groups = new LinkedList<Node[]>();	//list of nodes in the same subgraph

            do{
                int i = 0;
                while(i < mask.length && mask[i]){	//find the first node not done
                    i++;
                }
                mask[i] = true;
                //current node: find all its relatives, remaining nodes are in another subgraph
                LinkedList<Node> group = findRelatives(nodes[i], mask);
                group.add(nodes[i]);
                done += group.size();
                groups.add(group.toArray(new Node[group.size()]));
            }while(done < size);

            if(groups.size() == 1) {
                subgraphs.add(this);
            }else{
                for(int i = 0; i < groups.size(); i++) {
                    subgraphs.add(new Graph(groups.get(i)));
                }	
            }
        }
    }
    private LinkedList<Node> findRelatives(Node n, boolean[] mask) {
        LinkedList<Node> relatives = new LinkedList<Node>();
        for(Node child : n.getChildren()) {
            if(!mask[child.getId()]){			//if not done yet
                mask[child.getId()] = true;
                relatives.add(child);
                relatives.addAll(findRelatives(child, mask));
            }
        }
        return relatives;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public int countSubgraphs() {
        return subgraphs.size();
    }

    public Graph getSubgraph(int i) {
        return subgraphs.get(i);
    }
    public LinkedList<Node> getRemoveed() {
        return removed;
    }
}

//internal stuffs
class ColEdge {
	int u;
	int v;
}

class ReadGraph{
    public final static boolean DEBUG = false;

    public final static String COMMENT = "//";

    public int edges = 0;
    public int verts = 0;
    //it doesn't return unconnected edges
    public ColEdge[] getEdges( String file ) {
        String inputfile = file;
        boolean seen[] = null;

        //! n is the number of vertices in the graph
        int n = -1;

        //! m is the number of edges in the graph
        int m = -1;

        //! e will contain the edges of the graph
        ColEdge e[] = null;
        try { 
            FileReader fr = new FileReader(inputfile);
            BufferedReader br = new BufferedReader(fr);

            String record = new String();

                    //! THe first few lines of the file are allowed to be comments, staring with a // symbol.
                    //! These comments are only allowed at the top of the file.

                    //! -----------------------------------------
            while ((record = br.readLine()) != null) {
                if( record.startsWith("//") ) continue;
                    break; // Saw a line that did not start with a comment -- time to start reading the data in!
                }
                if( record.startsWith("VERTICES = ") ) {
                    n = Integer.parseInt( record.substring(11) );		
                    verts = n;
                    if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
                }
                
                seen = new boolean[n+1];	

                record = br.readLine();

                if( record.startsWith("EDGES = ") ) {
                    m = Integer.parseInt( record.substring(8) );					
                    edges = m;
                    if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
                }

                e = new ColEdge[m];

                for( int d=0; d<m; d++) {
                    if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
                    record = br.readLine();
                    String data[] = record.split(" ");
                    if( data.length != 2 ) {
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
                if( surplus != null ) {
                    if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
                }

        } catch (IOException ex) { 
        // catch possible io errors from readLine()
            System.out.println("Error! Problem reading file "+inputfile);
            System.exit(0);
        }

        for( int x=1; x<=n; x++ ) {
            if( seen[x] == false ) {
                if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
            }
        }		
        return e;
    }
}