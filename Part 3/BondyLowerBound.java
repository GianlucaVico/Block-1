/**
 * Algorithm to find a lower bound of the chromatic number of a graph. 
 * This implements the Bondy algorithm.
 */
public class BondyLowerBound implements Solver{
    private Graph g;
    private int solution;
    private int j, sum, k;
    
    /**
     * @param g Graph to use
     */
    public BondyLowerBound(Graph g) {
        this.g = g;
        solution = -1;
        sum = 0;        
        j = 0;
        k = 1;
        solution = -1;      
        
    }
    
    /**
     * @return a lower bound of the graph. Computations are done only during the first call of this method
     */
    public int solve(){        
        //while(sumI < g.getSize()) {
        if(sum == 0){
            do{
                k++;
                j = g.getSize() - g.getNodes().get(sum).getDegree();
                sum += j;
                /*if(sum < g.getSize())
                    k++;*/
            }while(sum < g.getSize());
            solution = k;
        }
        //System.out.println("--bondy: " + solution);
        return solution;
    }
    
    /**
     * @return the graph used
     */
    public Graph getGraph() { return g;}            
}
