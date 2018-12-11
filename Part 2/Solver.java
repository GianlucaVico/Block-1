public interface Solver {
	//return the solution
    /**     
     * @return The solution for the problem
     */
    int solve();
    
    /**
     * @return the graph used
     */
    Graph getGraph();
}