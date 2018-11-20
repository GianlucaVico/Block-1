public class LowerBound implements Solver {
	private Graph g;
	private int solution;
	
	public LowerBound(Graph g) {
		this.g = g;
		solution = -1;
	}
	
	public Graph getGraph() {
		return g;
	}
	//return the solution
	public int solve() {
		if (solution == -1){
			if(g.isTrivial())
				solution = g.trivialLowerBound();
			else {
				//TODO
			}
		}
		return solution;
	}
}

// 
// public LowerBound(Graph g) {
		// 
		// // All edges are connected to each other
		// if(complete) {
			// minDegree = verts;
			// }
			// 
			// // There are no edges and so minDegree = chromaticNumber 
			// else if(nullGraph) {
			// minDegree = 1; 
			// }
			// 
			// // There are no vertices = non existent graph 
			// else if (verts == 0) {
			// minDegree = 0;
			// }
			// 
			// /* 	If there are no cliques, there can be no edges and so the chromatic number is = 1
			// Even if a graph has only one edge between two nodes, there is already a clique of degree 1
			// If there are any edges, there must be at least one clique with degree 1
			// Therefore, if noClique = true, minDegree = 0; */
		// 
			// else if (noClique){
			// minDegree = 0; 
			// }
			// 
		// /* Now that all the possible exceptions have been covered in the if statements, a very rough lower bound
		// can be found by finding the highest degree and then adding 1 
		// */
		// else {
		// /* Find node with minimum degree, which uses binary search 
		// Binary search = an algorithm to find an item from a sorted list
		// This algorithm works by dividing the list of items in two repeatedly and so finding the vertex with min degree
		// */ 
		// 
		// // Nodes must be sorted in terms of valency 
		// 
		// public int binarySearch(/*List of nodes*/){
			// int first = 0;
			// int last = /*listofnodes*/.length - 1;
			// int middle = (first + last)/2;
			// 
			// // Recursive method to find the right node
			// if (first < last) {
				// int middle = last + (last - first)/2;
				// return binarySearch(/*list of nodes*/, first, middle, last);
			// }
		// 
			// else if { }
			// 
			// // This needs to be finished 
			// 
			// }
		// }
	// }